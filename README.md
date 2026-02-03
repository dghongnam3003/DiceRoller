# DiceRoller (Android)

A simple Android sample app that demonstrates rolling a dice and updating the UI. This project is implemented in Kotlin and uses the Android Gradle build system.

## Features

- Tap a button to roll a die
- Displays result on screen
- Small sample suitable for learning Android app basics

## Prerequisites

- JDK 11+ (or the version supported by your Android Gradle plugin)
- Android Studio (recommended) or command-line Gradle
- Android SDK with an emulator or a connected device

## Build & run

Using Android Studio:
1. Open Android Studio and choose "Open an existing Android Studio project".
2. Select the project root directory and let Gradle sync.
3. Run the app on an emulator or device.

Using command line (Unix/macOS/Linux):

# Build
./gradlew assembleDebug

# Install to a connected device or emulator
./gradlew installDebug

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Tests

- Unit tests are located under `app/src/test/java/`.
- Instrumented tests are under `app/src/androidTest/java/`.

Run unit tests:

./gradlew test

Run instrumented tests (connected device/emulator required):

./gradlew connectedAndroidTest

## Project structure

- app/ - Android application module
  - src/main/java/com/example/diceroller - App source code (Kotlin)
  - src/main/res - Resources (layouts, drawables, values)
  - src/test - Local unit tests
  - src/androidTest - Instrumented tests

## Contributing

Contributions are welcome. Please open an issue or submit a pull request with a clear description of changes.

## License

This repository currently does not include a license file. Add a LICENSE if you want to specify terms.

## Notes

This README was added automatically to describe the project and provide basic build/run instructions. If anything in this README is inaccurate for your environment, please update it accordingly.
