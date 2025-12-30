Project: DiceRoller (Android)

Overview
--------
DiceRoller is a small Android application (Kotlin) that allows a user to "roll" a six-sided dice and view the result on screen. It is a compact sample app intended for learning Android app structure and Kotlin basics. The app is simple by design and suitable as a starting point for adding features, tests, or learning UI and architecture patterns.

Key Features
------------
- Tap a button to roll a dice
- Displays the numeric result in a centered TextView
- Minimal, easy-to-read Kotlin implementation

Architecture & Design
---------------------
- Platform: Android (minimum SDK 19, target/compile SDK 33)
- Language: Kotlin
- UI: Single Activity (MainActivity) with a ConstraintLayout
- Business logic: Dice class encapsulates dice behavior (numSides, roll())
- No external architecture framework (no ViewModel / LiveData / Jetpack Compose) — intentionally minimal

Project Structure (high level)
-------------------------------
- settings.gradle.kts, build.gradle.kts (Top-level Gradle configuration)
- app/
  - build.gradle.kts (module Gradle file)
  - src/
    - main/
      - AndroidManifest.xml (app manifest, launcher activity)
      - java/com/example/diceroller/MainActivity.kt (single activity + Dice class)
      - res/layout/activity_main.xml (UI: TextView + Button)
      - res/values/strings.xml (app strings)
      - res/values/themes.xml, colors.xml (theme and colors)
    - test/ (unit tests skeleton)
    - androidTest/ (instrumented test skeleton)

How the app works (runtime)
---------------------------
1. MainActivity.onCreate inflates activity_main.xml
2. The Roll button (R.id.button2) has an onClick listener that calls rollDice()
3. rollDice() instantiates Dice(6) and calls roll(), which returns a random int in 1..6
4. The returned value is displayed in the TextView (R.id.textView)

Build, Run & Test
------------------
- Requirements: Java 11 or 8 compatible JDK, Android SDK (API 33 recommended), Gradle (wrapper included)
- Common commands:
  - ./gradlew assembleDebug  (build debug APK)
  - ./gradlew installDebug   (build and install on connected device/emulator)
  - ./gradlew test           (run unit tests)
  - ./gradlew connectedAndroidTest (run instrumentation tests on a device/emulator)

Dependencies (selected)
-----------------------
- androidx.core:core-ktx
- androidx.appcompat:appcompat
- com.google.android.material:material
- androidx.constraintlayout:constraintlayout
- junit (unit tests), androidx.test (instrumentation tests)

Testing
-------
- A basic unit test and an instrumentation test skeleton are present (app/src/test, app/src/androidTest). Expand tests to cover:
  - Dice.roll() deterministic behavior (use seeding or abstract randomness for deterministic tests)
  - UI behavior using Espresso (verify button click updates the TextView)

Areas for Improvement / Extension Ideas
--------------------------------------
- Use ViewModel + LiveData or Flow to separate UI and logic
- Replace random generation with an injectable Random provider to make tests deterministic
- Add images for dice faces and animate rolls
- Add settings (e.g., number of sides, roll history)
- Add instrumentation tests and CI pipeline (GitHub Actions) for automated builds/tests
- Migration to Jetpack Compose for modern UI

Coding & Contribution Guidelines (suggested)
--------------------------------------------
- Follow Kotlin coding conventions (use idiomatic Kotlin, concise expressions)
- Write unit tests for business logic and UI tests for interaction
- Keep single responsibility per class and small methods
- Open pull requests with descriptive titles and reference to issues

Known Limitations
-----------------
- Minimal error handling and no offline telemetry or analytics
- Dice randomness is not injectable, making unit testing non-deterministic by default
- Single-activity, not structured for larger apps without refactor

Contact / Maintainers
---------------------
- This repository has no explicit maintainers listed. For changes, open issues or pull requests on the project remote repository.

Notes
-----
This document is intended to give a concise, high-level overview to help new contributors or stakeholders quickly understand the codebase and development workflow. If you want, I can also:
- generate a CONTRIBUTING.md
- add unit/instrumentation tests for Dice and MainActivity
- propose a simple ViewModel refactor and implement it
