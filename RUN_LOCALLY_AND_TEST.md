# Run Locally & Test — DiceRoller Android Project

This guide walks you through the steps to run the DiceRoller app locally, install it on an emulator or device, and run the full test suite (unit + instrumented tests). It covers both IDE (Android Studio) and command-line workflows and includes troubleshooting tips.

Prerequisites
- JDK 11+ installed and JAVA_HOME set
- Android SDK (platforms, build-tools) installed
- Android Studio (recommended) OR command-line Android SDK tools
- Gradle wrapper (included: ./gradlew / gradlew.bat)
- An emulator (AVD) or a physical Android device with USB debugging enabled

Quick checklist
1. Open project in Android Studio: File -> Open -> select project root.
2. Let Gradle sync finish and ensure SDK/Gradle plugin versions install if prompted.
3. Create or start an emulator (AVD) or connect a device.

Common project entry points
- MainActivity: app/src/main/java/com/example/diceroller/MainActivity.kt
- Layout: app/src/main/res/layout/activity_main.xml
- Unit tests: app/src/test/java/com/example/diceroller/ExampleUnitTest.kt
- Instrumented tests: app/src/androidTest/java/com/example/diceroller/ExampleInstrumentedTest.kt

Build & Install (Command-line)
(Unix/macOS/Linux)
# Build debug APK
./gradlew assembleDebug

# Install debug APK on connected device/emulator
./gradlew installDebug

(Windows - use gradlew.bat)
# Build debug APK
gradlew.bat assembleDebug

# Install debug APK
gradlew.bat installDebug

If you prefer to install manually, the APK is produced under app/build/outputs/apk/debug/. You can use adb:
adb install -r app/build/outputs/apk/debug/app-debug.apk

Start an emulator (Android Studio)
- Open AVD Manager -> Create Virtual Device -> choose device and system image -> Finish
- Start the AVD from AVD Manager, or Run the app which will start the AVD automatically

Start an emulator (CLI - optional advanced)
# Example (sdk tools must be in PATH):
# Create AVD (one-time)
avdmanager create avd -n dice_avd -k "system-images;android-31;google_apis;x86" --device "pixel"

# Start emulator
emulator -avd dice_avd

Run from Android Studio
- Select the app module and a run configuration (app). Click the Run ▶ button. Choose an emulator or connected device.

Run unit tests (local JVM tests)
# Run all unit tests
./gradlew test

# Run tests for a specific module/class
./gradlew :app:test --tests "com.example.diceroller.ExampleUnitTest"

Run instrumented tests (on device/emulator)
# Make sure an emulator or device is available
./gradlew connectedAndroidTest

Notes on instrumented tests
- connectedAndroidTest runs Espresso/UI/instrumented tests on the device.
- Ensure the device/emulator has the matching SDK level for the app.
- If you get timeout errors, increase emulator boot time, restart adb (adb kill-server; adb start-server) and retry.

Run a single instrumented test (example using Gradle flavors/tasks may vary):
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="com.example.diceroller.ExampleInstrumentedTest"

(If the above doesn’t work on your Gradle/Android plugin version, run the whole connectedAndroidTest and filter results in Android Studio’s Test runner.)

Verifying full functionality (manual checks)
1. Launch the app on an emulator/device.
2. The main screen shows a button to "Roll" (see activity_main.xml). Tap the button.
3. Confirm the UI updates with a new dice value or image.
4. Repeat the roll; values should appear in the expected range (typically 1–6).

If you want to automate UI verification, add Espresso tests that click the roll button and assert TextView/ImageView content.

Debugging & Common Troubleshooting
- Gradle sync errors: Open Android Studio’s Gradle panel and read the error. Ensure correct SDK and plugin versions.
- Emulator not listed: Start an emulator from AVD Manager or ensure $ANDROID_HOME/emulator is in PATH.
- adb errors: Restart adb with adb kill-server; adb start-server. Verify device by adb devices.
- connectedAndroidTest failures: Open Logcat, check stack traces. Ensure instrumentation runner is configured in app/build.gradle if custom.

CI Tips (headless emulator)
- Use GitHub Actions / CircleCI with actions that provide Android SDK and start an emulator before running connectedAndroidTest.
- Alternatives: Use Firebase Test Lab or Gradle managed Virtual Devices for CI.

Where to extend tests
- Move dice-generation code into a testable utility class and add unit tests for deterministic behavior.
- Add Espresso UI tests under app/src/androidTest/ for button click and visual updates.

Appendix: Useful commands
# Show connected devices/emulators
adb devices

# Uninstall app from device/emulator
adb uninstall com.example.diceroller

# Run lint
./gradlew lint

# Clean build
./gradlew clean

If anything fails or you want, I can:
- Add example Espresso tests that verify the roll button behavior,
- Add a short script to start an emulator and run tests,
- Move this guide into docs/ directory and link it from README.md.

