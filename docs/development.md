# Development Guide

## Setting Up the Development Environment

### Prerequisites
- **Android Studio**: Latest stable version (recommended: Electric Eel or newer)
- **Java JDK**: Version 17 (required for Android development)
- **Android SDK**: API level 33 or higher
- **Kotlin Plugin**: Latest version

### Installation Steps

1. **Install Android Studio**:
   - Download from [developer.android.com/studio](https://developer.android.com/studio)
   - Follow installation instructions for your platform

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/dice-roller.git
   cd dice-roller
   ```

3. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

4. **Sync Gradle**:
   - Android Studio will automatically sync Gradle
   - If not, click "Sync Project with Gradle Files" in the toolbar

5. **Set Up Emulator** (optional):
   - Open AVD Manager
   - Create a new virtual device (recommended: Pixel 5, API 33)

## Project Configuration

### Gradle Setup
The project uses the following Gradle configuration:

- **Kotlin Version**: 1.8.0
- **Android Gradle Plugin**: 8.0.0
- **Compile SDK**: 33
- **Min SDK**: 21
- **Target SDK**: 33

### Dependencies
Current dependencies (in `app/build.gradle.kts`):

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## Development Workflow

### Making Changes

1. **Create a Feature Branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Implement Changes**:
   - Follow existing code style
   - Add appropriate comments
   - Write tests for new functionality

3. **Test Locally**:
   - Run on emulator or physical device
   - Execute unit tests
   - Verify all functionality works

4. **Commit Changes**:
   ```bash
   git add .
   git commit -m "Add feature: brief description"
   ```

5. **Push to Remote**:
   ```bash
   git push origin feature/your-feature-name
   ```

### Code Style Guidelines

1. **Kotlin Style**: Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
2. **Naming**: Use camelCase for variables and functions, PascalCase for classes
3. **Indentation**: 4 spaces (Android Studio default)
4. **Comments**: Use `//` for single line, `/** */` for documentation
5. **Imports**: Organize imports (Android Studio can do this automatically)

### Testing

#### Running Tests
- **Unit Tests**: Right-click test directory → Run Tests
- **UI Tests**: Run on connected device or emulator
- **All Tests**: Use Gradle test task

#### Writing Tests
- Place unit tests in `app/src/test/`
- Place UI tests in `app/src/androidTest/`
- Follow standard JUnit testing patterns

### Debugging

1. **Logcat**: Use Android Studio's Logcat for debugging output
2. **Breakpoints**: Set breakpoints in code for debugging
3. **Logging**: Use `Log.d()` for debug messages (remove before production)

## Building and Running

### Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### Running the App

1. **From Android Studio**: Click the "Run" button (green triangle)
2. **From Command Line**:
   ```bash
   ./gradlew installDebug
   ```

## Common Development Tasks

### Adding New Features

1. **Add New Activity**:
   - Create new Kotlin class in appropriate package
   - Create corresponding layout XML
   - Add to AndroidManifest.xml

2. **Add Resources**:
   - Images: Place in `res/drawable/`
   - Strings: Add to `res/values/strings.xml`
   - Colors: Add to `res/values/colors.xml`

3. **Add Dependencies**:
   - Edit `app/build.gradle.kts`
   - Add to dependencies block
   - Sync Gradle

### Working with UI

1. **Preview Layouts**: Use Android Studio's design view
2. **Theme Customization**: Edit `res/values/themes.xml`
3. **Add Animations**: Use Android's animation framework

## Troubleshooting

### Common Issues

1. **Gradle Sync Failed**:
   - Check internet connection
   - Try "File → Invalidate Caches / Restart"
   - Ensure you have the correct JDK version

2. **App Crashes on Launch**:
   - Check Logcat for error messages
   - Verify all resources exist
   - Check AndroidManifest.xml for correct configuration

3. **UI Not Updating**:
   - Verify data binding is correct
   - Check for typos in resource names
   - Ensure proper state management

### Debugging Tips

- Use `Log.d(TAG, "message")` for debugging
- Check Logcat filters
- Use Android Profiler for performance issues
- Verify all permissions in AndroidManifest.xml

## Deployment

### Generating Signed APK

1. Generate keystore if you don't have one:
   ```bash
   keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Create `keystore.properties` file:
   ```properties
   storePassword=your_password
   keyPassword=your_password
   keyAlias=alias_name
   storeFile=my-release-key.keystore
   ```

3. Configure signing in `app/build.gradle.kts`

4. Build signed APK:
   ```bash
   ./gradlew assembleRelease
   ```

### Publishing to Google Play

1. Create developer account
2. Prepare store listing (screenshots, description, etc.)
3. Upload signed APK
4. Complete content rating questionnaire
5. Publish to production

## Continuous Integration

### Setting Up CI/CD

Consider using:
- GitHub Actions
- GitLab CI/CD
- Bitrise
- CircleCI

Basic workflow:
1. Run unit tests
2. Build debug APK
3. Run UI tests on emulator
4. Build release APK
5. Upload artifacts

## Best Practices

1. **Version Control**: Commit often with meaningful messages
2. **Code Reviews**: Get feedback before merging
3. **Testing**: Write tests for all new features
4. **Documentation**: Update docs when making changes
5. **Performance**: Profile your app regularly
6. **Security**: Follow Android security best practices

## Resources

- [Android Developer Documentation](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Android API Reference](https://developer.android.com/reference)
- [Material Design Guidelines](https://material.io/design)
