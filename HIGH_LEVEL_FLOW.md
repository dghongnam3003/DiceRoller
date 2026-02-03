# High-level Flow: DiceRoller Android Project

This document describes the end-to-end (full) flow of the DiceRoller sample Android project at a high level — how the app is structured, what happens when a user interacts with it, how the build/test processes work, and where to extend or debug functionality.

## Goal

A concise Android sample app that demonstrates a simple UI interaction: the user taps a button to roll a die and the UI updates to show the rolled value.

## High-level Components

- app/src/main/java/com/example/diceroller/MainActivity.kt
  - Entry point Activity for the app and contains the UI interaction logic (button listener and UI updates).
- app/src/main/res/layout/activity_main.xml
  - XML layout describing the UI (button to roll the die, TextView/ImageView for result display).
- app/src/test/java/com/example/diceroller/ExampleUnitTest.kt
  - Local unit test(s).
- app/src/androidTest/java/com/example/diceroller/ExampleInstrumentedTest.kt
  - Instrumented tests that run on a device/emulator.
- build.gradle.kts, app/build.gradle.kts, gradle wrapper
  - Build configuration and Gradle wrapper for building/testing/installing the app.

## Runtime Flow (User Interaction)

1. App Launch
   - Android launches the app and creates MainActivity.
   - onCreate(...) in MainActivity is invoked and the activity's layout (activity_main.xml) is inflated.
   - Views (button, image/text) are bound (via findViewById or view binding depending on implementation).

2. User Taps "Roll" Button
   - A click listener attached to the roll button runs.
   - The listener triggers the dice-rolling logic.

3. Dice Roll Logic
   - The app generates a random integer in the expected range (commonly 1..6 for a six-sided die).
   - The numeric result may be converted into a drawable resource id or string for display.

4. UI Update
   - The TextView and/or ImageView in the activity is updated to reflect the new roll value (change text and/or image resource).
   - Optionally, an animation or brief visual feedback is shown.

5. End State
   - The user sees the result on screen and can roll again; the flow repeats on subsequent button taps.

Sequence (simple):
- Android system -> Activity lifecycle -> MainActivity.onCreate -> setContentView -> attach click listener -> onClick -> generate random value -> update UI

## Data & State

- The app uses minimal local in-memory state: the latest roll result is held in local variables in the Activity (or ViewModel if present).
- No persistence layer (database, network) is used in this sample app.

## Build & Run Flow

1. Local build with Gradle (wrapper):
   - ./gradlew assembleDebug  -> compiles sources, resources, packages an APK
   - ./gradlew installDebug   -> installs the debug APK onto a connected device/emulator
   - ./gradlew connectedAndroidTest -> runs instrumented tests on a connected device

2. Android Studio:
   - Open project -> Gradle sync -> Run configuration -> Build & deploy to device/emulator

## Test Flow

- Unit tests (JVM) run on the host machine using the Gradle `test` task:
  - ./gradlew test
- Instrumented tests run on a device/emulator using the `connectedAndroidTest` task:
  - ./gradlew connectedAndroidTest
- Example tests demonstrate how to validate UI or logic; extend them to cover more behavior.

## Debugging & Logging

- Use Logcat and breakpoints from Android Studio to inspect runtime behavior and view exceptions.
- Add Log.d(...) calls in MainActivity to print the generated roll value and lifecycle events.

## Extending the Flow

- Make logic testable: move dice-roll logic to a utility class or ViewModel and add unit tests for deterministic behavior.
- Add persistence: save history of rolls to a local database (Room) to replay or visualize past rolls.
- Add animations: make the dice roll feel more interactive using animation frameworks or Lottie.
- Add configuration: support different dice sizes (d4, d6, d20) and make them selectable by the user.

## File Map (quick reference)

- MainActivity: app/src/main/java/com/example/diceroller/MainActivity.kt
- Layout: app/src/main/res/layout/activity_main.xml
- Unit tests: app/src/test/java/com/example/diceroller/ExampleUnitTest.kt
- Instrumented tests: app/src/androidTest/java/com/example/diceroller/ExampleInstrumentedTest.kt
- Gradle build files: build.gradle.kts, app/build.gradle.kts

If you'd like, I can:
- Add this file into a docs/ directory instead of project root,
- Expand any section into step-by-step diagrams or code snippets,
- Move dice-roll logic into a separate class and add unit tests to demonstrate best practices.

