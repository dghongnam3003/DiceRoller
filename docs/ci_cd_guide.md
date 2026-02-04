# CI/CD Guide for this Android (Kotlin + Gradle) Project

This document explains what a CI/CD pipeline for this project should include, why each step matters, and an example GitHub Actions workflow you can adapt.

Goals
- Provide fast, reliable feedback on pull requests
- Prevent regressions by running builds and tests automatically
- Enforce code quality and style before merge
- Produce reproducible artifacts (APKs/AABs) for distribution
- Automate releases to the Play Store or distribution channels

Core Pipeline Stages (recommended)
1. Checkout
   - Fetch the commit and relevant submodules
   - Use a shallow clone for speed

2. Setup JDK / Gradle
   - Install a supported JDK (e.g., Amazon Corretto 11 or OpenJDK 11/17 depending on project)
   - Cache Gradle wrapper and Gradle dependencies to speed up builds

3. Formatting & Linters
   - Run ktlint/spotless to enforce Kotlin formatting
   - Run detekt for static analysis and code smells
   - Fail the build for violations or surface them as annotations (configurable)

4. Unit Tests
   - Run local JVM unit tests (./gradlew test)
   - Collect test reports and code coverage (JaCoCo)
   - Fail on test regressions

5. Instrumented / UI Tests (optional on PRs)
   - Run on an emulator or device farm (Firebase Test Lab) for critical UI tests
   - Consider running only smoke tests on each PR and full suites on nightly pipelines

6. Build / Assemble
   - Build debug and release variants as needed (./gradlew assembleDebug assembleRelease)
   - Run lint tasks: ./gradlew lint
   - Run resource shrinking, code shrinking (R8) during release builds

7. Security & Dependency Scanning
   - Run dependency vulnerability scanning (Snyk, OWASP Dependency-Check, or GitHub Dependabot alerts)
   - Scan for secrets in commits (git-secrets, truffleHog) before publishing artifacts

8. Artifact Storage
   - Upload APK/AAB artifacts and build logs to CI artifacts storage
   - Optionally push to an internal artifact repository (Nexus, Artifactory)

9. Code Coverage and Reports
   - Generate and publish code coverage reports (JaCoCo -> Coveralls / Codecov)
   - Fail the build if coverage drops below a defined threshold (optional)

10. Release Automation
   - On merge to main (or release branch), trigger a release pipeline
   - Build signed release AAB/APK and run final tests
   - Upload to Google Play using Fastlane or the Play Developer API
   - Create release tags and GitHub Releases with changelogs

11. Notifications & Monitoring
   - Notify team via Slack/Teams/email on failures and releases
   - Integrate crash/monitoring tools (Crashlytics, Sentry) into the release process

Branching & Workflow Guidelines
- Protect main branch: require PR reviews, status checks (CI pass), and successful linters/tests
- Feature branches: run full CI on PRs; keep CI fast by splitting heavy workloads to nightly or on-demand workflows
- Release branches: allow additional manual steps (release notes, QA sign-off)

Secrets & Credentials
- Do not store signing keys or Play Console credentials in the repo
- Use your CI provider's secrets management (GitHub Secrets, GitLab CI/CD Variables, CircleCI contexts)
- Encrypt or use a key-management system for sensitive artifacts
- For Android signing, consider using encrypted keystore files and the Gradle signing configs fed by environment variables

Caching & Performance
- Cache .gradle, ~/.gradle/caches and build outputs to reduce build time
- Use build scans or Gradle's build cache for diagnosing slow builds
- Use parallel Gradle execution where safe (org.gradle.parallel)

Testing Strategy Recommendations
- Unit tests: fast, run on every PR
- Instrumentation/UI tests: flaky and slow; run smoke tests on PRs and full suites on schedule or per-release
- Use test stability techniques: idling resources, retry on flakes, and isolate tests

Quality Gates
- Enforce passing linters and tests before merge
- Optional: require minimum code coverage or no new high-severity detekt findings
- Run security scans and block publishing on critical vulnerabilities

Example: GitHub Actions workflow (starter)

```yaml
name: CI

on:
  pull_request:
    branches: [ main ]
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    env:
      JAVA_HOME: /usr/lib/jvm/adoptopenjdk-11-hotspot

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: gradle

      - name: Cache Gradle
        uses: actions/cache@v4
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/gradle-wrapper.properties') }}

      - name: Run ktlint (format check)
        run: ./gradlew ktlintCheck --no-daemon --console=plain

      - name: Run static analysis (detekt)
        run: ./gradlew detekt --no-daemon --console=plain

      - name: Run unit tests
        run: ./gradlew testDebugUnitTest --no-daemon --console=plain

      - name: Assemble Debug
        run: ./gradlew assembleDebug --no-daemon --console=plain

      - name: Upload artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: build-artifacts
          path: app/build/outputs/

  release:
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'
    needs: build
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'

      - name: Decrypt signing key
        # Customize: decrypt keystore from secrets or secure storage
        run: echo "Decrypt signing key step - configure in your pipeline"

      - name: Build Release
        run: ./gradlew assembleRelease bundleRelease --no-daemon --console=plain

      - name: Publish to Play (example using Fastlane)
        run: |
          gem install fastlane -NV
          bundle exec fastlane android beta
        env:
          JSON_KEY: ${{ secrets.PLAY_STORE_JSON_KEY }}
```

Notes about the example
- Replace ktlint/detekt commands with the exact tasks/plugins used in your project (Spotless, detekt config, etc.)
- Adjust Java version and Gradle cache keys to suit your environment
- Use the CI's secrets features to store the Google Play JSON key and signing keystore password variables
- Split long-running steps (instrumentation tests, full linting) into separate workflows to keep PR feedback fast

Useful CI Providers & Integrations
- GitHub Actions (built-in, good ecosystem)
- GitLab CI (great for larger teams with built-in container registry)
- CircleCI / Bitrise / Jenkins / Azure DevOps
- Firebase Test Lab for cloud device testing
- Fastlane for automating Play Store releases

Checklist for a production-ready CI/CD
- [ ] Fast feedback on PRs (unit tests + linters)
- [ ] Build caching configured
- [ ] Secrets stored securely
- [ ] Signed release builds automated and audited
- [ ] Code coverage reporting
- [ ] Dependency vulnerability scanning
- [ ] Release notes and changelog generation
- [ ] Rollback strategy and monitoring in place

Further reading and references
- Android CI best practices: https://developer.android.com/studio/test
- Fastlane setup for Play Store: https://docs.fastlane.tools/getting-started/android/setup/
- GitHub Actions for Android: https://github.com/marketplace/actions/setup-android


