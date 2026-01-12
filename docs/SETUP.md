# Dice Roller App - Setup and Installation Guide

## Prerequisites

To build and run the Dice Roller app, you'll need:

- **Java Development Kit (JDK) 8 or later**
- **Android Studio** (recommended) or any Kotlin/Android compatible IDE
- **Android SDK** with API 21 or later
- **Gradle** (included with Android Studio)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-repository/dice-roller.git
cd dice-roller
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select "Open an existing project"
3. Navigate to the cloned repository directory
4. Click "OK" to open the project

### 3. Sync Gradle

Android Studio will automatically sync the Gradle files. If it doesn't:

1. Click "Sync Project with Gradle Files" in the toolbar
2. Wait for the sync to complete

### 4. Build the Project

1. Click "Build" > "Make Project" in the menu
2. Alternatively, use the build button in the toolbar
3. Wait for the build to complete successfully

## Running the App

### On an Emulator

1. Create an Android Virtual Device (AVD):
   - Click "Tools" > "AVD Manager"
   - Click "Create Virtual Device"
   - Select a device (e.g., Pixel 5)
   - Choose a system image (API 21 or later)
   - Click "Finish"

2. Run the app:
   - Select your AVD from the device dropdown
   - Click the "Run" button (green triangle)
   - Wait for the emulator to start and app to install

### On a Physical Device

1. Enable USB debugging on your Android device:
   - Go to "Settings" > "About phone"
   - Tap "Build number" 7 times to enable Developer options
   - Go back to "Settings" > "Developer options"
   - Enable "USB debugging"

2. Connect your device to your computer via USB
3. In Android Studio, select your device from the device dropdown
4. Click the "Run" button

## Project Structure

```
dice-roller/
├── app/                  # Main application module
│   ├── src/              # Source code
│   │   ├── main/         # Main application code
│   │   │   ├── java/     # Kotlin/Java source files
│   │   │   │   └── com/example/diceroller/
│   │   │   │       ├── MainActivity.kt
│   │   │   │       └── Dice.kt (if separated)
│   │   │   └── res/      # Resources (layouts, strings, etc.)
│   │   └── test/         # Unit tests
│   └── build.gradle.kts  # Module build configuration
├── build.gradle.kts      # Project build configuration
├── settings.gradle.kts   # Project settings
└── docs/                 # Documentation (this folder)
```

## Configuration

### Build Configuration

The app uses Gradle with Kotlin DSL for build configuration. Key settings:

- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: Latest stable Android version
- **Compile SDK**: Latest stable Android version

### App Configuration

The app doesn't require any special configuration. All settings are defined in:

- `app/build.gradle.kts` - Build configuration
- `app/src/main/AndroidManifest.xml` - App manifest
- `app/src/main/res/values/strings.xml` - String resources

## Troubleshooting

### Common Issues

1. **Gradle Sync Failed**:
   - Check your internet connection
   - Ensure you have the latest Android Studio
   - Try "File" > "Invalidate Caches / Restart"

2. **Build Failed**:
   - Clean the project: "Build" > "Clean Project"
   - Rebuild: "Build" > "Rebuild Project"
   - Check for specific error messages

3. **Emulator Not Starting**:
   - Ensure you have enough disk space
   - Check that virtualization is enabled in your BIOS
   - Try a different system image

4. **Device Not Detected**:
   - Check USB connection
   - Enable USB debugging on the device
   - Install proper USB drivers for your device

### Logs and Debugging

- View logs in Android Studio's "Logcat" window
- Use `Log.d()` statements in code for debugging
- Check "Run" window for build errors

## Dependencies

The app uses standard AndroidX libraries:

- `androidx.appcompat:appcompat` - AppCompat library
- `androidx.core:core-ktx` - Kotlin extensions
- `org.jetbrains.kotlin:kotlin-stdlib` - Kotlin standard library

All dependencies are defined in `app/build.gradle.kts`.

## Updating Dependencies

To update dependencies:

1. Open `app/build.gradle.kts`
2. Update version numbers for dependencies
3. Sync Gradle files
4. Test the app thoroughly

## Continuous Integration

For CI/CD setup, you can use:

- GitHub Actions
- GitLab CI
- Bitrise
- CircleCI

Example GitHub Actions workflow:

```yaml
name: Android CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    - name: Build with Gradle
      run: ./gradlew build
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for contribution guidelines.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
