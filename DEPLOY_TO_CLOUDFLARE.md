# Deploying the DiceRoller Project to Cloudflare

This guide explains several practical ways to host and distribute this Android project using Cloudflare services. Because this repository is an Android app (APK), Cloudflare cannot run the Android binary directly — instead you can:

- Host a small static site that provides the APK download (Cloudflare Pages), or
- Store the APK in Cloudflare R2 (object storage) and serve it via a Cloudflare Worker (recommended for programmatic control), or
- Build a small web UI + API and host both on Cloudflare Pages + Workers to provide release metadata, analytics, and secure download links.

Choose the option that best fits your needs. This doc covers prerequisites and step-by-step instructions for each option, example configs, and security/caching tips.

Prerequisites
- Cloudflare account (free plan is sufficient for Pages, Workers, and basic R2 usage; R2 may require enabling and may have limits)
- Cloudflare API token with appropriate permissions for Pages / Workers / R2
- Wrangler CLI (Cloudflare developer CLI) installed: https://developers.cloudflare.com/workers/wrangler/ (wrangler v2 recommended)
- GitHub (or Git provider) if you want to connect Cloudflare Pages to your repository
- Local development environment able to build the project (Android SDK, JDK, Gradle) if building APKs locally

Quick glossary
- Pages: Static site hosting (good for an index.html plus downloadable APK)
- Workers: Edge JS-like serverless functions (good for custom headers, redirects, signed URLs)
- R2: Cloudflare object storage (S3-compatible; good to store APKs and large assets)
- Wrangler: CLI to manage Workers, R2, and Pages from your machine

Option A — Simple: Host a static download page with Cloudflare Pages

Use when: you want a simple website with an APK download link and release notes.

1) Create a small site in this repo (recommended location: /docs or /site). Example structure:

public/
  index.html  <-- links to /apk/app-release.apk or an external URL
  apk/
    app-release.apk

Example index.html (very small):

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

2) Add your built APK to public/apk/app-release.apk (copy from app/build/outputs/apk/release/)

Build APK locally (release or debug):
./gradlew assembleRelease
# Copy the APK to site public folder
mkdir -p public/apk
cp app/build/outputs/apk/release/app-release.apk public/apk/

3) Commit and push your changes to the repository. In Cloudflare dashboard create a Pages site and connect the repo. Set the build output directory to "public" and (if no build is required) clear the build command.

4) Cloudflare will publish the static site. You can set a custom domain, enforce HTTPS, and use Cloudflare caching. Users download the APK directly from the site.

Notes
- Serving APKs from Pages is simple but not optimized for large file distribution. For more control and lower egress, consider R2.
- Ensure you provide checksum (SHA256) so users can verify the APK integrity.

Option B — Recommended for releases: Store APK in R2 + serve via a Worker

Use when: you want programmatic control, versioned releases, large files, or signed / time-limited download URLs.

Overview
- Create an R2 bucket and upload APKs (object storage)
- Create a Worker that serves or proxies APKs from R2 with custom headers (Content-Type, Content-Disposition) or generates signed URLs
- Configure a route or subdomain to the Worker (e.g., downloads.example.com/*)

Step-by-step

1) Install Wrangler (v2) and login

npm install -g wrangler
wrangler login

2) Create a Worker project (in this repo or separate directory) or add wrangler config to repo root. Example minimal wrangler.toml:

name = "diceroller-downloads"
main = "./worker.js"
compatibility_date = "2024-01-01"

# R2 binding - update account_id and bucket name
[[r2_buckets]]
binding = "DICE_BUCKET"
bucket_name = "diceroller-apks"
preview_bucket_name = "diceroller-apks"

3) Create a Worker script that fetches objects from R2 and returns them with appropriate headers. Example worker.js (JavaScript):

export default {
  async fetch(request, env, ctx) {
    // URL path /releases/{filename}
    const url = new URL(request.url)
    const key = url.pathname.replace(/^\//, '') // the filename
    if (!key) return new Response('Not Found', { status: 404 })

    // Fetch from R2 binding
    const obj = await env.DICE_BUCKET.get(key)
    if (!obj) return new Response('Not Found', { status: 404 })

    const headers = new Headers()
    headers.set('Content-Type', 'application/vnd.android.package-archive')
    // Suggest a download filename
    headers.set('Content-Disposition', `attachment; filename="${key}"`)

    // Cache control: adjust as needed
    headers.set('Cache-Control', 'public, max-age=3600')

    return new Response(obj.body, { status: 200, headers })
  }
}

4) Create the R2 bucket on the Cloudflare dashboard or via Wrangler and upload your APK(s):

# Example: using wrangler r2 commands (you may need to authenticate and set the correct account id):
# Upload a file to a bucket
wrangler r2 object put diceroller-apks app-release.apk --binding DICE_BUCKET --account-id <YOUR_ACCOUNT_ID> --path app-release-v1.0.apk

(If wrangler r2 commands are not available in your installed version, use the Cloudflare dashboard or the R2 S3-compatible API to upload.)

5) Deploy the Worker (publish):
wrangler publish --env production

6) Configure a route or custom subdomain to the Worker in Cloudflare dashboard, e.g., downloads.example.com/* -> diceroller-downloads

7) Use the Worker URL to download: https://downloads.example.com/app-release-v1.0.apk

Generating signed/time-limited URLs (optional)
- For secure downloads, you can implement signed URLs in the Worker by generating a token (HMAC) that encodes expiry and filename. The Worker validates the token before returning the object.
- R2 currently does not have built-in presigned URLs in the same way S3 does, so Workers are the mechanism to gate access.

Option C — Pages + Worker + R2: user-facing site + secure downloads

Combine the static Pages site for release notes + web UI, with downloads served by Worker from R2.

Example flow:
- Pages hosts index.html and release pages (e.g., /releases/1.0.0)
- Downloads are requested from a Worker subdomain (e.g., downloads.example.com) that serves from R2
- Pages links to Worker download URLs

This gives a polished release site with fast global CDN plus robust downloadable storage.

Automation: CI uploads to R2 or commits built site

You can automate building APKs and uploading them on every release using GitHub Actions or other CI. High-level approach:
- Use a CI runner with Android SDK to build the APK
- Use Cloudflare API or wrangler to upload the APK to R2
- Optionally trigger a Pages build or create/update a release page with the new download link

Example (high-level) GitHub Action steps
- actions/checkout
- Set up Java and Android SDK (use actions/setup-java and configure sdkmanager to install build-tools)
- ./gradlew assembleRelease
- Upload artifact or use wrangler to push apk to R2
- Optionally call Cloudflare API to purge cache or update metadata

Security & Best Practices
- Serve APKs with Content-Type: application/vnd.android.package-archive and Content-Disposition: attachment; filename="..."
- Use HTTPS (Cloudflare will automatically provide TLS for Pages/Workers)
- Consider signing your release APK and publish its checksum (SHA256) on the release page
- If you expose downloads publicly, consider rate limiting or signed URLs if you care about access control
- Use proper cache headers. For frequently-updated files, set shorter max-age and use cache purging on releases

Costs and limits
- Cloudflare Pages: free tier available, designed for static sites
- Workers: free tier has generous limits; heavy usage may incur cost
- R2: billed for storage and egress; pricing is typically competitive with egress-free tiers in some plans but check Cloudflare pricing

DNS and custom domains
- To use your own domain, configure Pages or Worker route with your domain in the Cloudflare dashboard and verify DNS ownership
- Cloudflare provides automatic TLS for Pages and Workers

Troubleshooting
- 404 from Worker: verify that the correct object key exists in R2 and that the binding name in wrangler.toml matches env.<BINDING>
- CORS issues when linking from a web UI: set appropriate CORS headers in the Worker responses (Access-Control-Allow-Origin)
- Large files not uploading via wrangler: use the R2 web dashboard or S3-compatible tools to upload large objects
- Authentication: ensure Wrangler is authenticated (wrangler login) and that your API token has R2 write/read and Workers publish permissions

References
- Cloudflare Pages docs: https://developers.cloudflare.com/pages/
- Cloudflare Workers: https://developers.cloudflare.com/workers/
- Cloudflare R2: https://developers.cloudflare.com/r2/
- Wrangler CLI: https://developers.cloudflare.com/workers/cli-wrangler/

If you want, I can:
- Add an example Worker project and wrangler.toml to this repo,
- Add a small static site (public/index.html) that links to the Worker downloads,
- Provide a sample GitHub Actions workflow that builds the APK and uploads to R2.


