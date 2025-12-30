Dice Roller (Android)
======================

High-level overview
-------------------

This is a small Android application (Kotlin) that demonstrates a simple Dice Roller. The app shows a large numeric result on screen and a button to roll a six-sided dice. It is structured as a minimal, idiomatic Android project using AndroidX and ConstraintLayout and is suitable as a learning/sample project or a foundation to extend into a larger app.

Key features
------------

- Single activity (MainActivity) implemented in Kotlin.
- Simple Dice domain class (Dice) encapsulating dice behaviour.
- UI implemented with ConstraintLayout (activity_main.xml).
- Basic unit and instrumentation test scaffolding present.

Goals of this document
----------------------

- Provide a quick overview for new contributors and maintainers
- Describe the project structure and main components
- Explain build, run and test instructions
- Suggest extension points and possible improvements

Project structure (high-level)
-------------------------------

- app/
  - src/main/java/com/example/diceroller/
    - MainActivity.kt  -- UI controller and dice-roll logic wiring
  - src/main/res/layout/
    - activity_main.xml -- App UI (TextView and Button)
  - src/test/ -- JVM unit test(s)
  - src/androidTest/ -- Instrumentation (UI) test(s)
- build.gradle.kts, gradle wrapper, gradle.properties -- build files

Core components
---------------

1. MainActivity
   - Acts as the single Activity that loads activity_main.xml
   - Binds the roll button to a click listener which creates a Dice and updates the result TextView
   - Minimal, single-responsibility UI controller (presentation logic only)

2. Dice (domain model)
   - Simple class encapsulating the number of sides and providing a roll() method
   - Keeps domain logic decoupled from UI (easy to unit test)

3. Layout (activity_main.xml)
   - ConstraintLayout with a TextView for the result and a Button for triggering a roll
   - Values (strings, colors, themes) live in res/values

Build and run
-------------

Requirements
- JDK 11+ (use the version supported by the project's Gradle wrapper)
- Android SDK (matching the project's compile/target SDK configured in Gradle)

Run locally (emulator or device)
1. From project root, use the Gradle wrapper to assemble and run:
   - ./gradlew assembleDebug
   - ./gradlew installDebug
2. Or open the project in Android Studio (recommended), let it sync Gradle, and Run the app on an emulator or attached device.

Testing
-------

- Unit tests: ./gradlew test
  - JVM tests located under app/src/test
- Instrumentation/UI tests: ./gradlew connectedAndroidTest
  - Android instrumentation tests located under app/src/androidTest

Development workflow and conventions
------------------------------------

- Kotlin-first codebase: follow standard Kotlin style guides and Android best practices.
- Keep UI logic in Activities/Fragments/ViewModels and business logic in plain Kotlin classes where possible.
- Prefer small, well-tested functions and classes. Avoid long-lived static state.

Suggested branches / git workflow
- main - stable production-ready code
- feature/* - new features
- fix/* - bug fixes
- PRs should include: description, testing steps, affected modules

Extension ideas
---------------

This small sample can be extended in many ways:

- Replace single Activity with MVVM (add ViewModel and LiveData/StateFlow) to separate presentation from state.
- Add images for dice faces instead of numeric text.
- Make dice configurable (UI to choose number of sides, number of dice, keep/roll selected dice etc.).
- Add animations when rolling (shake, fade, image transitions).
- Add telemetry/analytics (for example, to count rolls) or logging for debugging.
- Add continuous integration: GitHub Actions or other CI to run ./gradlew test and lint on PRs.

Risks and assumptions
---------------------

- This repository is intentionally minimal and used for learning or as a template.
- Does not currently follow a strict architecture pattern (e.g., MVVM) — this is an intentional trade-off for simplicity.
- Device/SDK compatibility depends on the project's Gradle configuration. Ensure emulator/device SDKs match.

How to contribute
-----------------

1. Fork the repository and create a branch for your change.
2. Write tests for new behaviour where applicable.
3. Open a pull request with a clear description and testing instructions.

Contact / Maintainers
---------------------

- No dedicated maintainer information is provided in this repository. Add a MAINTAINERS or CONTRIBUTING file if this project will be shared broadly.

Next steps I can help with
--------------------------
- Convert the app to MVVM and add a ViewModel + unit tests
- Add dice-face images and animations
- Add a CI workflow (GitHub Actions) to run tests


