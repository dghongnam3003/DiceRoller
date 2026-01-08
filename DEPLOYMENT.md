# Deployment Guide

## Overview

This document outlines the deployment process for the Dice Roller Android application, covering everything from local builds to production releases on the Google Play Store.

## Table of Contents
- [Build Types](#build-types)
- [Signing Configuration](#signing-configuration)
- [Local Deployment](#local-deployment)
- [CI/CD Pipeline](#cicd-pipeline)
- [Play Store Deployment](#play-store-deployment)
- [Release Management](#release-management)
- [Rollback Procedures](#rollback-procedures)
- [Monitoring and Analytics](#monitoring-and-analytics)

## Build Types

### Debug Build
The debug build is used for development and testing purposes.

```kotlin
// app/build.gradle.kts
buildTypes {
    debug {
        isDebuggable = true
        applicationIdSuffix = \".debug\"
        versionNameSuffix = \"-DEBUG\"
        isMinifyEnabled = false
        
        // Enable R8 full mode for testing
        proguardFiles(
            getDefaultProguardFile(\"proguard-android.txt\"),
            \"proguard-rules.pro\"
        )
        
        // Add debug-specific configurations
        buildConfigField(\"boolean\", \"DEBUG_MODE\", \"true\")
        resValue(\"string\", \"app_name\", \"Dice Roller (Debug)\")
    }
}
```

**Characteristics:**
- Includes debug symbols
- Allows debugging with Android Studio
- Has separate application ID to install alongside release
- Includes debug-specific logging
- No code obfuscation

### Release Build
The release build is optimized for production deployment.

```kotlin
// app/build.gradle.kts
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(
            getDefaultProguardFile(\"proguard-android-optimize.txt\"),
            \"proguard-rules.pro\"
        )
        signingConfig = signingConfigs.getByName(\"release\")
        
        // Production configurations
        buildConfigField(\"boolean\", \"DEBUG_MODE\", \"false\")
        resValue(\"string\", \"app_name\", \"Dice Roller\")
        
        // Enable full R8 optimization
        isDebuggable = false
    }
}
```

**Characteristics:**
- Code obfuscation and minification enabled
- Resource shrinking to reduce APK size
- Signed with release key
- All debug code removed
- Optimized for performance

### Staging Build (Optional)
For testing release configurations before production.

```kotlin
buildTypes {
    create(\"staging\") {
        initWith(getByName(\"release\"))
        applicationIdSuffix = \".staging\"
        versionNameSuffix = \"-STAGING\"
        isDebuggable = true
        
        // Use staging-specific configurations
        buildConfigField(\"boolean\", \"DEBUG_MODE\", \"true\")
        resValue(\"string\", \"app_name\", \"Dice Roller (Staging)\")
    }
}
```

## Signing Configuration

### Debug Signing
Debug builds use a default debug keystore provided by Android SDK.

```kotlin
signingConfigs {
    getByName(\"debug\") {
        // Uses default debug keystore
        // ~/.android/debug.keystore
    }
}
```

### Release Signing
Production builds require a custom keystore for security.

#### Creating a Release Keystore
```bash
# Generate keystore (do this once and keep secure)
keytool -genkey -v -keystore dice-roller-release.keystore \\
    -alias dice-roller-key \\
    -keyalg RSA \\
    -keysize 2048 \\
    -validity 10000

# Verify keystore
keytool -list -v -keystore dice-roller-release.keystore
```

#### Signing Configuration
```kotlin
// app/build.gradle.kts
android {
    signingConfigs {
        create(\"release\") {
            storeFile = file(\"../release-keystore/dice-roller-release.keystore\")
            storePassword = project.findProperty(\"RELEASE_STORE_PASSWORD\") as String? ?: \"\"
            keyAlias = project.findProperty(\"RELEASE_KEY_ALIAS\") as String? ?: \"\"
            keyPassword = project.findProperty(\"RELEASE_KEY_PASSWORD\") as String? ?: \"\"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName(\"release\")
        }
    }
}
```

#### Environment Variables
```bash
# ~/.gradle/gradle.properties (local development)
RELEASE_STORE_PASSWORD=your_store_password
RELEASE_KEY_ALIAS=dice-roller-key
RELEASE_KEY_PASSWORD=your_key_password

# For CI/CD, set as secure environment variables
```

### Security Best Practices
- **Never commit keystore files** to version control
- Store passwords in secure environment variables
- Use different keys for different environments
- Backup keystore files in multiple secure locations
- Consider using Google Play App Signing

## Local Deployment

### Building APKs

#### Debug APK
```bash
# Generate debug APK
./gradlew assembleDebug

# Output location
# app/build/outputs/apk/debug/app-debug.apk
```

#### Release APK
```bash
# Generate release APK
./gradlew assembleRelease

# Output location
# app/build/outputs/apk/release/app-release.apk
```

### Installing on Devices

#### ADB Installation
```bash
# Install debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Install release APK
adb install app/build/outputs/apk/release/app-release.apk

# Install and replace existing
adb install -r app-release.apk

# Install on specific device (if multiple connected)
adb -s DEVICE_ID install app-release.apk
```

#### Android Studio Installation
1. Connect device or start emulator
2. Click \"Run\" button or use `Shift + F10`
3. Select target device
4. App installs and launches automatically

### Bundle Generation

For Play Store deployment, use Android App Bundles (AAB):

```bash
# Generate debug bundle
./gradlew bundleDebug

# Generate release bundle
./gradlew bundleRelease

# Output location
# app/build/outputs/bundle/release/app-release.aab
```

### Size Analysis

```bash
# Analyze APK size
./gradlew analyzeReleaseApk

# Generate size report
./gradlew app:analyzeReleaseBundle
```

## CI/CD Pipeline

### GitHub Actions Configuration

Create `.github/workflows/ci.yml`:

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  release:
    types: [ published ]

jobs:
  test:
    name: Unit Tests
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          
      - name: Cache Gradle packages
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: |
            ${{ runner.os }}-gradle-
            
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        
      - name: Run unit tests
        run: ./gradlew test
        
      - name: Run lint
        run: ./gradlew lint
        
      - name: Upload test results
        uses: actions/upload-artifact@v3
        if: always()
        with:
          name: test-results
          path: |
            app/build/reports/
            app/build/test-results/

  build:
    name: Build APK
    runs-on: ubuntu-latest
    needs: test
    if: github.event_name == 'push'
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          
      - name: Cache Gradle packages
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        
      - name: Build debug APK
        run: ./gradlew assembleDebug
        
      - name: Upload debug APK
        uses: actions/upload-artifact@v3
        with:
          name: debug-apk
          path: app/build/outputs/apk/debug/app-debug.apk

  release:
    name: Release Build
    runs-on: ubuntu-latest
    needs: test
    if: github.event_name == 'release'
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          
      - name: Decode keystore
        run: |
          echo \"${{ secrets.RELEASE_KEYSTORE }}\" | base64 -d > release-keystore.jks
          
      - name: Build release bundle
        run: ./gradlew bundleRelease
        env:
          RELEASE_STORE_PASSWORD: ${{ secrets.RELEASE_STORE_PASSWORD }}
          RELEASE_KEY_ALIAS: ${{ secrets.RELEASE_KEY_ALIAS }}
          RELEASE_KEY_PASSWORD: ${{ secrets.RELEASE_KEY_PASSWORD }}
          
      - name: Upload release bundle
        uses: actions/upload-artifact@v3
        with:
          name: release-bundle
          path: app/build/outputs/bundle/release/app-release.aab
```

### Setup CI/CD Secrets

In GitHub repository settings, add these secrets:
- `RELEASE_KEYSTORE`: Base64 encoded keystore file
- `RELEASE_STORE_PASSWORD`: Keystore password
- `RELEASE_KEY_ALIAS`: Key alias
- `RELEASE_KEY_PASSWORD`: Key password

## Play Store Deployment

### Google Play Console Setup

1. **Create Developer Account**
   - Sign up at [Google Play Console](https://play.google.com/console)
   - Pay one-time registration fee
   - Complete account verification

2. **Create New App**
   - Click \"Create app\"
   - Fill in app details (name, language, type)
   - Choose content declarations

3. **App Content Configuration**
   - Content rating questionnaire
   - Target audience and content
   - Privacy policy (if applicable)
   - App category and tags

### Store Listing

#### Required Assets
- **App icon**: 512x512 PNG
- **Feature graphic**: 1024x500 PNG
- **Screenshots**: Phone (min 2), Tablet (optional)
- **Short description**: Max 80 characters
- **Full description**: Max 4000 characters

#### Store Listing Example
```
Short Description:
\"Simple and fun dice rolling app with clean Material Design interface\"

Full Description:
\"Dice Roller is a beautifully designed dice rolling app that brings the classic dice experience to your Android device.

Features:
• Clean and intuitive Material Design interface
• Instant dice rolling with tap of a button
• Random number generation for fair results
• Lightweight and fast performance
• No ads or in-app purchases

Perfect for board games, decision making, or just for fun! The app uses secure random number generation to ensure every roll is completely fair and unpredictable.

Simple, fast, and reliable - just like a real dice!\"
```

### Release Process

#### Internal Testing
```bash
# Build and upload to internal testing
./gradlew bundleRelease
# Upload via Play Console or use Play Console API
```

#### Closed Testing (Beta)
- Create closed testing track
- Add test users via email lists or Google Groups
- Distribute to beta testers for feedback

#### Production Release
1. **Upload Release Bundle**
   ```bash
   ./gradlew bundleRelease
   ```

2. **Release Notes**
   ```
   Version 1.0.0
   • Initial release of Dice Roller
   • Simple dice rolling functionality
   • Clean Material Design interface
   • Fast and reliable random number generation
   ```

3. **Staged Rollout**
   - Start with 1% rollout
   - Monitor crash reports and ratings
   - Gradually increase to 100%

### Automated Play Store Deployment

Use GitHub Actions with Play Console API:

```yaml
- name: Deploy to Play Store
  uses: r0adkll/upload-google-play@v1
  with:
    serviceAccountJsonPlainText: ${{ secrets.PLAY_STORE_SERVICE_ACCOUNT }}
    packageName: com.example.diceroller
    releaseFiles: app/build/outputs/bundle/release/app-release.aab
    track: production
    status: completed
    whatsNewDirectory: distribution/whatsnew
```

## Release Management

### Versioning Strategy

Follow [Semantic Versioning](https://semver.org/):
- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)  
- **PATCH**: Bug fixes (backward compatible)

```kotlin
// app/build.gradle.kts
android {
    defaultConfig {
        versionCode = 1      // Increment for each release
        versionName = \"1.0.0\" // Semantic version
    }
}
```

### Version Code Management
```kotlin
// Use timestamp-based version codes for unique builds
val versionCodeTimestamp = (System.currentTimeMillis() / 1000).toInt()

android {
    defaultConfig {
        versionCode = versionCodeTimestamp
        versionName = \"1.0.0\"
    }
}
```

### Release Checklist

#### Pre-Release
- [ ] All tests pass
- [ ] Code reviewed and approved
- [ ] Version numbers updated
- [ ] Release notes written
- [ ] Store listing updated
- [ ] Screenshots current
- [ ] Performance tested

#### Release
- [ ] Build release bundle
- [ ] Sign with release key
- [ ] Upload to Play Console
- [ ] Configure rollout percentage
- [ ] Monitor initial rollout
- [ ] Full release deployment

#### Post-Release
- [ ] Monitor crash reports
- [ ] Check user reviews
- [ ] Monitor app performance
- [ ] Plan next release
- [ ] Update documentation

### Hotfix Process

For critical bugs:

1. **Create Hotfix Branch**
   ```bash
   git checkout -b hotfix/critical-bug-fix main
   ```

2. **Fix and Test**
   ```bash
   # Implement minimal fix
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

3. **Emergency Release**
   ```bash
   # Increment patch version
   # Update version code
   ./gradlew bundleRelease
   ```

4. **Deploy with 100% Rollout**
   - Skip staged rollout for critical fixes
   - Monitor closely for additional issues

## Rollback Procedures

### Play Store Rollback
1. **Halt Rollout**
   - Stop current release in Play Console
   - Prevents new installs

2. **Promote Previous Version**
   - Select last known good version
   - Promote to production track

3. **Communicate Issues**
   - Update release notes
   - Notify users of known issues

### APK Rollback
For direct APK distribution:
```bash
# Reinstall previous version
adb install -r app-previous-version.apk
```

## Monitoring and Analytics

### Crash Reporting
Consider integrating crash reporting:
```kotlin
// Example with Firebase Crashlytics
dependencies {
    implementation 'com.google.firebase:firebase-crashlytics-ktx'
}
```

### Performance Monitoring
Track app performance metrics:
- App startup time
- Memory usage
- ANR (Application Not Responding) rates
- User engagement metrics

### Play Console Metrics
Monitor in Google Play Console:
- Install/uninstall rates
- User ratings and reviews
- Technical performance
- Financial performance (if monetized)

## Security Considerations

### Release Security
- Keep signing keys secure and backed up
- Use different keys for different environments
- Consider Google Play App Signing
- Monitor for unauthorized re-signing

### Build Security
- Scan dependencies for vulnerabilities
- Use ProGuard/R8 for code obfuscation
- Remove debug information from release builds
- Validate all external inputs

---

*This deployment guide should be updated as the project grows and deployment processes evolve.*