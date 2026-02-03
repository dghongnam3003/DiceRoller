# Cloudflare Deployment — Step-by-step Guide

This document provides a focused, practical walkthrough to deploy this Android project’s release artifacts (APK) to Cloudflare using three common deployment patterns:

- Option 1 — Cloudflare Pages (static site with APK download) — simplest
- Option 2 — R2 + Workers (recommended for control, streaming, custom headers, signed links)
- Option 3 — Pages + R2 + Worker (user-facing site + secure downloads)

Each option contains the precise files, commands, and example snippets you can add to this repository to make deployment reproducible and automatable.

Prerequisites
- Cloudflare account
- Wrangler CLI (v2+) installed: npm i -g wrangler
- Cloudflare API token with permissions for Workers, R2, and Pages as required
- (For CI) GitHub repository and optionally GitHub Actions
- Local machine or CI that can build the Android APK (JDK, Android SDK, Gradle)

Quick terminology
- Pages: static hosting for sites (good for a simple download page)
- R2: Cloudflare object storage (S3-like)
- Workers: serverless functions at the edge that can read from R2 and serve files
- Wrangler: Cloudflare CLI for deploying Workers, managing R2 bindings, and publishing Pages

--------------------------------------------------------------------------------
Option 1 — Cloudflare Pages: publish a static download page with an embedded APK
--------------------------------------------------------------------------------
When to use: you want a simple download page with no programmatic control.

Files to add to your repo (example):

public/index.html
public/apk/app-release.apk  <-- copy of the built APK

Example simple index.html (add to public/index.html):

<!doctype html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>DiceRoller — Download</title>
  </head>
  <body>
    <h1>DiceRoller</h1>
    <p>Download the latest APK:</p>
    <a href="/apk/app-release.apk" download>Download APK</a>
    <p>SHA256: <em>REPLACE_WITH_SHA256_OF_APK</em></p>
  </body>
</html>

How to build and copy the APK locally:

# Build APK (release)
./gradlew assembleRelease

# Create the public directory and copy APK
mkdir -p public/apk
cp app/build/outputs/apk/release/app-release.apk public/apk/

Commit and push. In Cloudflare Pages dashboard:
- Create a new Pages project
- Connect to your repo
- Set build output directory: public
- If you already included a built site (no build step), use an empty build command

Notes:
- Provide a SHA256 checksum on the page for users to verify integrity
- Simple, quick, but not ideal for large files or signed URLs

--------------------------------------------------------------------------------
Option 2 — R2 + Worker (recommended)
--------------------------------------------------------------------------------
When to use: you want versioned releases, signed/time-limited URLs, custom headers or streaming large files.

High-level flow:
- Create an R2 bucket called e.g. diceroller-apks
- Upload APK artifacts to R2
- Deploy a Worker that proxies requests to R2 and returns APK with correct headers
- Optionally implement signed URLs in the Worker

Files to add to this repo (examples):
- wrangler.toml
- worker/worker.js
- (optional) scripts/upload-to-r2.sh

Example wrangler.toml (place at repo root):

name = "diceroller-downloads"
main = "worker/worker.js"
compatibility_date = "2025-01-01"

account_id = "YOUR_ACCOUNT_ID"
# Add an environment if you want (optional)

[[r2_buckets]]
binding = "DICE_BUCKET"
bucket_name = "diceroller-apks"
preview_bucket_name = "diceroller-apks"

Notes:
- Replace YOUR_ACCOUNT_ID with your Cloudflare account id
- The binding name (DICE_BUCKET) will be available as env.DICE_BUCKET inside the Worker

Example Worker (worker/worker.js)

export default {
  async fetch(request, env) {
    const url = new URL(request.url)
    // Expecting /releases/<filename>
    const key = url.pathname.replace(/^\/(releases\/)?/, '')
    if (!key) return new Response('Not Found', { status: 404 })

    // Optional: verify signed token query param here for private downloads

    const obj = await env.DICE_BUCKET.get(key)
    if (!obj) return new Response('Not Found', { status: 404 })

    const headers = new Headers()
    headers.set('Content-Type', 'application/vnd.android.package-archive')
    headers.set('Content-Disposition', `attachment; filename="${key}"`)
    headers.set('Cache-Control', 'public, max-age=3600')

    // Stream the body directly
    return new Response(obj.body, { status: 200, headers })
  }
}

Create the R2 bucket
- In the Cloudflare dashboard: Storage -> R2 -> Create bucket (name it diceroller-apks)
- Or use Wrangler/r2 API if available in your version

Upload APK to R2
Option A — wrangler r2 (if supported):

wrangler r2 object put diceroller-apks app/build/outputs/apk/release/app-release-v1.0.apk --key app-release-v1.0.apk --account-id <YOUR_ACCOUNT_ID>

Option B — Use s3-compatible tools (R2 provides an S3-compatible endpoint) or Cloudflare dashboard to upload

Deploy the Worker (publish):

wrangler publish

Bind custom domain or route to the Worker via Cloudflare dashboard (e.g., downloads.example.com/* -> Worker)

Signed/time-limited URLs (optional)
- Implement HMAC-based signed tokens in your Worker: generate token server-side (CI or API) and validate expiry inside Worker
- Store secrets in Wrangler secrets (wrangler secret put SIGNING_KEY)

Example token validation (pseudo):
- Token includes: filename, expiry, signature = HMAC(secret, filename + expiry)
- Worker checks expiry and verifies signature before returning the R2 object

--------------------------------------------------------------------------------
Option 3 — Pages (UI) + R2 (storage) + Worker (downloads)
--------------------------------------------------------------------------------
This is a combination of Option 1 and Option 2 and is recommended when you want a public release site and robust downloads.

Flow:
- Pages hosts index/release pages with metadata and download buttons
- Download link points to Worker URL like https://downloads.example.com/app-release-v1.0.apk
- Worker serves from R2

Files to add:
- public/ (site for Pages)
- worker/worker.js and wrangler.toml

--------------------------------------------------------------------------------
Automating with GitHub Actions
--------------------------------------------------------------------------------
You can automate build + upload to R2 on push to a release branch or on GitHub Releases. Example workflow (place in .github/workflows/ci-deploy.yml):

name: Build and Upload APK to R2
on:
  push:
    tags:
      - 'v*'

jobs:
  build-and-upload:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'

      - name: Install Android tools (basic)
        run: |
          sudo apt-get update
          sudo apt-get install -y unzip wget
          wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O cmdline-tools.zip
          unzip cmdline-tools.zip -d $HOME/cmdline-tools
          export PATH="$HOME/cmdline-tools/cmdline-tools/bin:$PATH"
          yes | sdkmanager --sdk_root=$HOME/android-sdk "platform-tools" "platforms;android-33" "build-tools;33.0.2"

      - name: Build release APK
        run: |
          ./gradlew assembleRelease -Pci=true

      - name: Install Wrangler
        run: npm i -g wrangler@2

      - name: Upload APK to R2 via Wrangler
        env:
          CF_ACCOUNT_ID: ${{ secrets.CF_ACCOUNT_ID }}
          CF_API_TOKEN: ${{ secrets.CF_API_TOKEN }}
        run: |
          # ensure APK exists
          ls -la app/build/outputs/apk/release/
          # Put the APK file into R2 bucket
          wrangler r2 object put diceroller-apks app/build/outputs/apk/release/app-release.apk --key app-release-${{ github.ref_name }}.apk --account-id $CF_ACCOUNT_ID

Notes on secrets:
- Store CF_API_TOKEN and CF_ACCOUNT_ID in repo Secrets
- CF_API_TOKEN must have permissions to write to R2 (and to publish Workers if you publish via CI)

Alternative: Use a GitHub Action that runs wrangler publish to deploy Worker or Pages
- Use cloudflare/wrangler-action for publishing: https://github.com/cloudflare/wrangler-action

--------------------------------------------------------------------------------
Security & best practices
--------------------------------------------------------------------------------
- Always publish checksums (SHA256) alongside APKs
- Use HTTPS and ensure TLS is enabled for custom domains
- If you need limited downloads, implement signed URLs or token checks in the Worker
- Keep secrets out of the repo. Store them in Cloudflare secrets (wrangler secret put) or CI secrets
- Use short cache TTLs if you frequently update APKs; purge CDN cache after new release

--------------------------------------------------------------------------------
Troubleshooting
--------------------------------------------------------------------------------
- 404 from Worker: verify object key exists in R2 and Worker binding name matches wrangler.toml
- wrangler r2 command not found: ensure wrangler v2+ is installed (npm i -g wrangler). Some older wrangler releases had different syntax
- Large files failing to upload in CI: use multipart S3-compatible upload to R2 or upload from the Cloudflare dashboard
- CORS: if serving downloads via fetch from pages, add Access-Control-Allow-Origin header in Worker

--------------------------------------------------------------------------------
Next steps I can help with (tell me which you want added):
- Add example wrangler.toml and worker.js to this repository
- Add public/ index.html and sample release page for Pages
- Add GitHub Actions workflow file that builds and automatically uploads APKs
- Implement example signed URL logic in Worker

