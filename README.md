# Dice Roller (Android)

A small Android sample app written in Kotlin that demonstrates basic app structure, unit tests, and build configuration using Gradle. This repository contains an example app (app module) that rolls a die and displays results.

## Table of Contents
- About
- High-Level Flow
- CI/CD Guide
- Technologies
- Getting Started
- Running Tests
- Contributing
- License

## About
This is a simple demo Android application intended for learning and reference. It includes:
- Kotlin-based Android app module
- Unit tests and instrumentation test samples
- Gradle build configuration

## High-Level Flow
See docs/high_level_flow.md for a diagram and explanation of the main application flow and components.

## CI/CD Guide
A starter CI/CD guide for this project is available at docs/ci_cd_guide.md. It includes recommended pipeline stages, a sample GitHub Actions workflow, and tips for signing and publishing.

## Technologies
- Kotlin
- Android SDK
- Gradle (Kotlin DSL)
- JUnit (unit testing)

## Getting Started
Prerequisites:
- JDK 11 or newer
- Android SDK (installed via Android Studio or command line)
- Gradle wrapper (included)

Clone and build:

1. git clone <repository-url>
2. cd <repo>
3. ./gradlew build

## Running Tests
- Unit tests: ./gradlew test
- Instrumented tests (on an emulator/device): ./gradlew connectedAndroidTest

## Contributing
Contributions are welcome. Please open an issue or submit a pull request. Follow the project's code style and add tests for new behavior.

## License
This project is MIT Licensed - see the LICENSE file for details (or add your preferred license).
