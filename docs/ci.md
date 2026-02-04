# CI Documentation

This document describes the Continuous Integration (CI) pipeline at a high level, how to set it up for this repository, and troubleshooting tips for common CI failures. Use this as a guide when creating or maintaining CI workflows for this project.

## 1. Pipeline overview

A robust CI pipeline should provide fast feedback on changes, enforce quality gates, and produce reproducible artifacts. The pipeline stages below are recommended; adapt them to the CI provider you use (GitHub Actions, GitLab CI, CircleCI, etc.).

Recommended stages

- Checkout
  - Checkout repository (shallow clone optional)
  - Fetch submodules if used

- Setup environment
  - Install JDK (version required by the project)
  - Install Gradle wrapper or desired Gradle distribution
  - Install any language-specific runtimes or tools

- Dependency caching
  - Cache Gradle wrapper and dependency caches to reduce build times
  - Cache other tool caches as appropriate

- Static analysis & formatting
  - ktlint / spotless check for Kotlin formatting
  - detekt for static analysis
  - Fail early on critical issues

- Unit tests
  - Run JVM unit tests (fast, run on every PR)
  - Collect test reports and publish artifacts

- Integration / Instrumentation tests (optional)
  - Run a smaller smoke suite on PRs; run full suites on nightly pipelines or release branches
  - Use emulator farms (Firebase Test Lab) for wider device coverage

- Build / Assemble
  - Run assembleDebug/assembleRelease or bundleRelease as needed
  - Run lint tasks and resource checks

- Artifact handling
  - Upload build artifacts (APKs, AABs, test reports) to CI artifacts storage
  - Optionally publish to artifact registry

- Release steps (on main/release branches)
  - Sign release builds using encrypted keystore or CI secrets
  - Publish to Google Play (Fastlane, Play Developer API) or internal distribution
  - Create release tags and changelogs

- Notifications
  - Notify teams on failures or successful releases via Slack/Teams/email

Quality gates

- Require formatting and static analysis to pass on PRs
- Require passing unit tests for merge
- Optionally enforce minimum code coverage or zero high-severity findings

## 2. Setup instructions

This section shows example setup steps for GitHub Actions; adapt the same concepts for other CI providers.

Prerequisites

- A supported JDK version (check your project's Gradle configuration)
- A Gradle wrapper committed to repo (recommended)
- CI provider account with access to repository and secrets
- (For releases) Google Play JSON credential, keystore file, and related passwords stored securely as CI secrets

Example: GitHub Actions starter workflow (template)

- Create a workflow file at `.github/workflows/ci.yml` with contents similar to the sample below.

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

      - name: Run format check (ktlint/spotless)
        run: ./gradlew ktlintCheck --no-daemon --console=plain

      - name: Run static analysis (detekt)
        run: ./gradlew detekt --no-daemon --console=plain

      - name: Run unit tests
        run: ./gradlew test --no-daemon --console=plain

      - name: Assemble debug
        run: ./gradlew assembleDebug --no-daemon --console=plain

      - name: Upload artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: build-artifacts
          path: app/build/outputs/
```

Secrets and signing

- Store sensitive values (keystore, keystore passwords, Play JSON key) in CI secrets
- For Android signing, either:
  - Decrypt an encrypted keystore file in CI (store encrypted file in repo or CI storage) and use secrets for passwords; or
  - Use the Gradle signing plugin with environment variables supplied by the CI

Caching

- Cache Gradle dependencies and wrapper to speed up subsequent runs
- Use deterministic cache keys that include gradle-wrapper.properties or lockfiles so caches are invalidated properly when needed

Customizing to other CI providers

- GitLab CI: similar job stages and caching (use cache: key and paths)
- CircleCI: use orbs for Android and caching workspaces
- Bitrise: visual workflows with pre-built Android steps

Local verification

- Run the same Gradle commands locally as in CI to reproduce failures:
  - ./gradlew clean test assembleDebug
  - ./gradlew ktlintCheck detekt
- Use `--no-daemon --console=plain` to match CI environment behavior

Checklist to enable CI for this repo

- [ ] Add workflow file(s) to `.github/workflows/` or configure your CI provider
- [ ] Add required secrets to CI provider
- [ ] Ensure Gradle wrapper is up-to-date and committed
- [ ] Configure caches for Gradle and tools
- [ ] Add artifact upload steps for build outputs and reports
- [ ] Protect branches and require CI status checks for merges

## 3. Troubleshooting guide

Below are common CI issues and actionable steps to diagnose and fix them.

1) JDK / Java version mismatch
- Symptom: Compilation errors mentioning unsupported classfile major version, or Gradle fails with an incompatible JVM message.
- Action:
  - Check `build.gradle` and `gradle.properties` for the required Java version
  - Ensure CI workflow sets up the same JDK (e.g., actions/setup-java@v4 with java-version: '11')
  - Print java version in CI for debugging: `java -version` and `./gradlew -version`

2) Dependency resolution failures
- Symptom: Gradle cannot find artifacts or times out fetching dependencies
- Action:
  - Ensure network egress in CI is allowed to Maven Central/Google
  - Check for private repositories or credentials needed (add CI secrets if required)
  - Try `./gradlew build --refresh-dependencies` locally to reproduce

3) Slow builds / cache misses
- Symptom: Every CI run is slow; cache miss frequently
- Action:
  - Verify cache keys and paths are correct (include gradle-wrapper.properties hash)
  - Cache both `~/.gradle/caches` and `~/.gradle/wrapper`
  - Use Gradle's configuration cache or build cache where appropriate

4) Formatting or linter failures
- Symptom: ktlint/detekt fails in CI but passes locally
- Action:
  - Ensure same plugin/tool versions are used locally and in CI
  - Run `./gradlew ktlintFormat` or apply auto-fixes if allowed
  - Surface line/annotation-based reports to help developers fix issues

5) Flaky tests / instrumentation test failures
- Symptom: Tests pass locally but fail intermittently in CI
- Action:
  - Increase stability: use idling resources, remove time-based sleeps, and isolate tests
  - Retry flaky tests via CI test retry strategies (if supported)
  - Run instrumentation tests on device farms (Firebase Test Lab) for more reliable environments

6) Signing or publishing errors
- Symptom: Release build fails due to missing keystore or wrong credentials; publishing fails due to missing Play credentials
- Action:
  - Confirm keystore and passwords exist in CI secrets and are available to the build
  - Validate the flow to decrypt/import the keystore in CI, and run `./gradlew assembleRelease` locally using the same env vars
  - For Play publishing, ensure JSON key is valid and has necessary permissions

7) Emulator or Android SDK problems
- Symptom: Emulator-based jobs fail to start or cannot boot
- Action:
  - Use CI images with preinstalled Android SDK or add steps to install SDK components
  - Use provided emulator actions/orbs with proper system images
  - Increase startup timeouts in CI to allow emulator boot

8) Artifact upload failures
- Symptom: Upload action fails with path-not-found or permission errors
- Action:
  - Confirm the artifact path exists in the job workspace (print `ls -R` in CI step for debugging)
  - Ensure you set `if: always()` for artifact upload to capture artifacts on failure

Debugging tips

- Add a temporary debug job that prints environment, `java -version`, `./gradlew -version`, and `ls -la` of build directories.
- Reproduce the failing commands locally using Docker or a fresh VM to mimic CI environment
- Use `--stacktrace --info --scan` flags with Gradle to capture more detailed logs

If you still can't solve the issue

- Collect job logs, Gradle scan (if enabled), and test artifacts
- Open an issue in the repo describing:
  - The CI provider and workflow file
  - Reproduction steps and failing logs
  - What you've tried so far

Appendix: Useful commands for CI debugging

- Print Java & Gradle info
  - java -version
  - ./gradlew -version

- Clean and build
  - ./gradlew clean assembleDebug --no-daemon --console=plain

- Run tests
  - ./gradlew test --no-daemon --console=plain

- Run linters
  - ./gradlew ktlintCheck detekt --no-daemon --console=plain



