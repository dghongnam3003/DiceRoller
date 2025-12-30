# Dice Roller Android App

## Project Overview

Dice Roller is a simple Android application written in Kotlin that allows users to roll a six-sided dice and view the numeric result on the screen. This repository contains a minimal, fully functional example app suitable for learning Android fundamentals or as a starter template to extend with additional features.

## Key Features

- Single-screen app with a large numeric display of the dice result
- "Roll" button to roll a six-sided dice (random result from 1 to 6)
- Minimal, easy-to-read Kotlin implementation

## Technology Stack

- Kotlin
- Android SDK (AndroidX)
- Gradle (Kotlin DSL)
- ConstraintLayout for simple responsive UI

## Project Structure (high level)

- app/
  - src/main/java/com/example/diceroller/
    - MainActivity.kt       -> Activity implementing UI and dice logic
  - src/main/res/
    - layout/activity_main.xml -> UI layout (TextView + Button)
    - values/               -> colors, strings, themes
    - mipmap/               -> app icons
  - build.gradle.kts        -> app-level Gradle configuration
- build.gradle.kts          -> root Gradle configuration
- gradle/gradle-wrapper/   -> gradle wrapper

Files of interest:
- app/src/main/java/com/example/diceroller/MainActivity.kt
  - Contains MainActivity: sets the content view, wires the Roll button, and updates a TextView with the dice roll.
  - Contains a small Dice class (model) with a roll() method that returns a random int between 1 and the number of sides.
- app/src/main/res/layout/activity_main.xml
  - Defines the main UI: a large TextView (result) and a Button (roll)

## Architecture & Design (current)

- The app currently uses a very simple single-activity approach where UI and UI-driven logic live in MainActivity.
- Dice is implemented as a simple model class nested in the same file for convenience.

Pros:
- Extremely simple and easy to understand for beginners.
- Minimal boilerplate.

Cons / Limitations:
- Tight coupling between UI and logic makes the code harder to test and extend.
- No ViewModel, no separation of concerns, and no dependency injection.

## Suggested High-level Improvements / Roadmap

If you want to evolve this project into a more production-like app, consider the following:

1. Introduce MVVM pattern
   - Create a DiceViewModel to hold the dice state and expose LiveData for the UI.
   - Move all logic out of MainActivity so it only observes ViewModel state and handles UI updates.
2. Unit tests
   - Add unit tests for the Dice model and ViewModel.
   - Keep business logic out of Android framework types to make testing easy.
3. UI Tests
   - Add instrumented UI tests (Espresso) for rolling behavior and UI state updates.
4. Dependency Injection
   - Use Hilt or Dagger if you add more complex dependencies later.
5. Multiple dice / configurable sides
   - Make the number of dice and sides configurable in the UI or settings screen.
6. Animation and UX improvements
   - Add simple roll animation, sound feedback, or haptic feedback.
7. Accessibility
   - Ensure content descriptions, large text support, and proper focus navigation.

## How to Build and Run

Recommended: open in Android Studio (the wrapper and Gradle files are included).

From the command line (requires Android SDK and build tools installed):

- Build the app:
  ./gradlew assembleDebug

- Run unit tests:
  ./gradlew test

- Run instrumented tests (connected device/emulator required):
  ./gradlew connectedAndroidTest

- Install and run on a connected device/emulator:
  ./gradlew installDebug

## Testing

- Unit tests live in app/src/test (an example test file is included).
- Instrumented Android tests live in app/src/androidTest.

## Coding Guidelines

- Follow Kotlin idioms (avoid unnecessary nullability, prefer immutable vals where possible).
- Keep UI-free business logic in plain Kotlin classes (no Android SDK dependencies) to make unit testing straightforward.
- Use descriptive resource names for strings, colors, and layouts.

## Contribution

Contributions are welcome. For a small project like this, keep changes focused and add tests for new logic. Suggested workflow:

1. Fork the repository
2. Create a feature branch (feature/your-feature)
3. Implement changes and add/update tests
4. Create a pull request describing the intent and testing steps

## Licensing

Add a LICENSE file if you want to release this under an open-source license. If none is present, assume "All rights reserved".

## Contact

For questions about this repository, leave an issue or contact the maintainer in the repository settings.

---

This document is intended to provide a concise, high-level overview of the Dice Roller project and guidance for next steps. If you want, I can also:
- Add a CONTRIBUTING.md file with templates and checklists
- Refactor the project to MVVM with a ViewModel and unit tests
- Add sample UI tests (Espresso)

Tell me which follow-up you'd like and I can implement it next.