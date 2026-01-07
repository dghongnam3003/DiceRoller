# Deployment Guide

## Overview

This guide covers the complete deployment process for the Dice Roller Android application, from development builds to production release on Google Play Store.

## Prerequisites

### Development Environment
- **Android Studio**: Version 4.0 or higher
- **JDK**: Java 8 or higher
- **Android SDK**: API levels 19-33
- **Kotlin**: Version 1.9.0

### Build Tools
- **Gradle**: Version 8.1.2
- **Android Gradle Plugin**: Version 8.1.2
- **Git**: For version control

### Signing Requirements (Production)
- **Keystore file**: For app signing
- **Key passwords**: Secure storage required
- **Google Play Developer account**: For store publishing

## Build Types

### Debug Build

**Purpose**: Development and testing

**Configuration**: `app/build.gradle.kts`
```kotlin
buildTypes {
    debug {
        isMinifyEnabled = false
        isDebuggable = true
        applicationIdSuffix = ".debug"
        versionNameSuffix = "-debug"
    }
}
```

**Build Commands**:
```bash
# Android Studio
./gradlew assembleDebug

# Command line
./gradlew app:assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build

**Purpose**: Production deployment

**Configuration**:
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**Build Commands**:
```bash
# Unsigned release
./gradlew assembleRelease

# Signed release (requires keystore)
./gradlew bundleRelease
```

## Signing Configuration

### Create Keystore

**Generate new keystore**:
```bash
keytool -genkey -v -keystore diceroller-release-key.keystore \
    -alias diceroller \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000
```

**Keystore Information**:
- **Alias**: diceroller
- **Validity**: 10000 days (~27 years)
- **Algorithm**: RSA 2048-bit
- **Password**: [Secure password required]

### Configure Gradle Signing

**gradle.properties** (local, not in version control):
```properties
DICEROLLER_UPLOAD_STORE_FILE=../diceroller-release-key.keystore
DICEROLLER_UPLOAD_STORE_PASSWORD=your_store_password
DICEROLLER_UPLOAD_KEY_ALIAS=diceroller
DICEROLLER_UPLOAD_KEY_PASSWORD=your_key_password
```

**app/build.gradle.kts**:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file(project.findProperty("DICEROLLER_UPLOAD_STORE_FILE") ?: "")
            storePassword = project.findProperty("DICEROLLER_UPLOAD_STORE_PASSWORD") as String?
            keyAlias = project.findProperty("DICEROLLER_UPLOAD_KEY_ALIAS") as String?
            keyPassword = project.findProperty("DICEROLLER_UPLOAD_KEY_PASSWORD") as String?
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

## Build Process

### Local Development Build

1. **Clean project**:
```bash
./gradlew clean
```

2. **Run tests**:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

3. **Build debug**:
```bash
./gradlew assembleDebug
```

4. **Install on device**:
```bash
./gradlew installDebug
```

### Production Release Build

1. **Update version**:
   - Increment `versionCode` in `app/build.gradle.kts`
   - Update `versionName` for user-visible version

2. **Run full test suite**:
```bash
./gradlew check
./gradlew connectedCheck
```

3. **Build release bundle**:
```bash
./gradlew bundleRelease
```

4. **Verify signing**:
```bash
jarsigner -verify -verbose -certs app/build/outputs/bundle/release/app-release.aab
```

## Continuous Integration

### GitHub Actions Workflow

**`.github/workflows/android.yml`**:
```yaml
name: Android CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Run tests
      run: ./gradlew test
    
    - name: Run lint
      run: ./gradlew lintDebug
    
    - name: Build debug APK
      run: ./gradlew assembleDebug
    
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk

  release:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
    
    - name: Decode Keystore
      env:
        ENCODED_STRING: ${{ secrets.KEYSTORE_BASE64 }}
      run: |
        echo $ENCODED_STRING | base64 -di > app/diceroller-release-key.keystore
    
    - name: Build Release AAB
      env:
        SIGNING_KEY_ALIAS: ${{ secrets.SIGNING_KEY_ALIAS }}
        SIGNING_KEY_PASSWORD: ${{ secrets.SIGNING_KEY_PASSWORD }}
        SIGNING_STORE_PASSWORD: ${{ secrets.SIGNING_STORE_PASSWORD }}
      run: ./gradlew bundleRelease
    
    - name: Upload Release Bundle
      uses: actions/upload-artifact@v3
      with:
        name: app-release
        path: app/build/outputs/bundle/release/app-release.aab
```

### Required Secrets

Add these to GitHub repository secrets:
- `KEYSTORE_BASE64`: Base64 encoded keystore file
- `SIGNING_KEY_ALIAS`: Key alias from keystore
- `SIGNING_KEY_PASSWORD`: Password for the key
- `SIGNING_STORE_PASSWORD`: Password for the keystore

## Google Play Store Deployment

### Initial Setup

1. **Create Google Play Developer Account**
   - Pay $25 one-time registration fee
   - Complete account verification

2. **Create App in Google Play Console**
   - Choose "Create app"
   - Fill in app details
   - Select target audience

3. **Upload Release Bundle**
   - Go to "Release" → "Production"
   - Upload AAB file
   - Fill in release notes

### Release Process

#### 1. Pre-launch Checklist

- [ ] Version code incremented
- [ ] Version name updated
- [ ] All tests passing
- [ ] Release notes written
- [ ] Screenshots updated
- [ ] Store listing reviewed

#### 2. Bundle Upload

```bash
# Generate signed bundle
./gradlew bundleRelease

# Verify bundle
bundletool build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app-release.apks

# Test locally
bundletool install-apks --apks=app-release.apks
```

#### 3. Google Play Console Steps

1. **Upload Bundle**:
   - Navigate to "Production" track
   - Click "Create new release"
   - Upload `app-release.aab`

2. **Release Notes**:
   ```
   Version 1.0.0
   - Initial release
   - Simple dice rolling functionality
   - Clean, intuitive interface
   - Supports 6-sided dice
   ```

3. **Review and Publish**:
   - Check all warnings
   - Confirm target countries
   - Click "Review release"
   - Click "Start rollout to production"

### Staged Rollout

**Recommended approach**:
1. **5%** rollout initially
2. **20%** after 24 hours if no issues
3. **50%** after 48 hours
4. **100%** after 72 hours

**Monitoring during rollout**:
- Crash rate < 1%
- ANR rate < 0.5%
- User ratings > 4.0

## Environment-Specific Configuration

### Development
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://dev-api.diceroller.com\"")
buildConfigField("boolean", "DEBUG_MODE", "true")
buildConfigField("String", "ANALYTICS_KEY", "\"dev-key\"")
```

### Staging
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://staging-api.diceroller.com\"")
buildConfigField("boolean", "DEBUG_MODE", "false")
buildConfigField("String", "ANALYTICS_KEY", "\"staging-key\"")
```

### Production
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://api.diceroller.com\"")
buildConfigField("boolean", "DEBUG_MODE", "false")
buildConfigField("String", "ANALYTICS_KEY", "\"prod-key\"")
```

## Monitoring and Analytics

### Firebase Crashlytics Setup

**app/build.gradle.kts**:
```kotlin
plugins {
    id("com.google.firebase.crashlytics")
}

dependencies {
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}
```

**Initialize in Application class**:
```kotlin
class DiceRollerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
```

### Play Console Monitoring

Monitor these metrics:
- **Crashes and ANRs**: Should be < 1%
- **Installation success rate**: Should be > 95%
- **User ratings**: Target > 4.0
- **User reviews**: Respond within 24 hours

## Rollback Procedures

### Emergency Rollback

If critical issues are discovered:

1. **Immediate action**:
   - Halt rollout in Play Console
   - Prepare hotfix if possible

2. **Communication**:
   - Update app description with known issues
   - Respond to user reviews
   - Post on social media if applicable

3. **Technical steps**:
   ```bash
   # Create hotfix branch
   git checkout -b hotfix/critical-fix main
   
   # Make minimal fix
   # Test thoroughly
   # Build and deploy
   ```

### Version Rollback

If rollback is necessary:
1. Stop current release rollout
2. Increase previous version's rollout to 100%
3. Fix issues in new version
4. Re-release with incremented version code

## Security Considerations

### Code Obfuscation

**proguard-rules.pro**:
```pro
# Keep application class
-keep public class * extends android.app.Application

# Keep activity classes
-keep public class * extends androidx.appcompat.app.AppCompatActivity

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
```

### Certificate Pinning

For future API integration:
```kotlin
val certificatePinner = CertificatePinner.Builder()
    .add("api.diceroller.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
    .build()

val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinner)
    .build()
```

## Backup and Recovery

### Keystore Management
- **Primary keystore**: Secure vault storage
- **Backup keystore**: Separate physical location
- **Key information**: Documented and encrypted
- **Recovery plan**: Tested annually

### Build Artifacts
- **APK/AAB files**: Archived for each release
- **Mapping files**: Required for crash analysis
- **Release notes**: Version control tracked
- **Build logs**: Retained for debugging

## Performance Optimization

### APK Size Reduction

Current size optimizations:
```kotlin
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    
    bundle {
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
        language {
            enableSplit = false
        }
    }
}
```

### Build Time Optimization

**gradle.properties**:
```properties
org.gradle.jvmargs=-Xmx4096m
org.gradle.parallel=true
org.gradle.caching=true
android.useAndroidX=true
android.enableJetifier=true
```

## Troubleshooting

### Common Build Issues

**Issue**: "Duplicate class" errors
**Solution**: 
```kotlin
configurations.all {
    exclude(group = "org.jetbrains", module = "annotations")
}
```

**Issue**: ProGuard obfuscation problems
**Solution**: Add specific keep rules in `proguard-rules.pro`

**Issue**: Signing configuration not found
**Solution**: Verify `gradle.properties` file exists and has correct values

### Deployment Issues

**Issue**: Google Play rejects bundle
**Common causes**:
- Missing permissions in manifest
- Incorrect target API level
- Security vulnerabilities

**Solution**: Check Play Console pre-launch report

## Conclusion

This deployment guide covers the complete lifecycle from development to production release. Following these procedures ensures reliable, secure, and efficient deployment of the Dice Roller application. Regular review and updates of these procedures help maintain deployment quality and security standards.