# Dice Roller Android App

A simple Android application that allows users to roll a virtual 6-sided dice and view the result on the screen.

## Features

- 🎲 Roll a 6-sided dice with a single button tap
- 📱 Clean and simple user interface
- 🔢 Display dice roll results instantly
- 📐 Built with modern Android development practices

## Screenshots

*Add screenshots of your app here*

## Tech Stack

- **Language**: Kotlin
- **Platform**: Android
- **Min SDK**: API level as configured
- **Build Tool**: Gradle with Kotlin DSL
- **Architecture**: Simple Activity-based architecture

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/diceroller/
│   │   │   └── MainActivity.kt          # Main activity with dice rolling logic
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # Main UI layout
│   │   │   ├── values/
│   │   │   │   ├── strings.xml          # App strings
│   │   │   │   ├── colors.xml           # Color definitions
│   │   │   │   └── themes.xml           # App themes
│   │   │   └── ...                      # Other resources
│   │   └── AndroidManifest.xml          # App manifest
│   ├── test/                            # Unit tests
│   └── androidTest/                     # Instrumented tests
└── build.gradle.kts                     # Module build configuration
```

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API level 21+ (recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd diceroller
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory and select it

3. **Build the project**
   - Let Android Studio sync the project
   - Build the project using `Build > Make Project` or `Ctrl+F9`

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift+F10`

### Building from Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## How to Use

1. Launch the Dice Roller app
2. Tap the "ROLL" button
3. View the dice roll result displayed on the screen
4. Tap the button again to roll the dice again

## Code Overview

### Main Components

- **MainActivity**: The main activity that handles the user interface and dice rolling logic
- **Dice**: A simple data class that represents a dice with configurable number of sides

### Key Features in Code

```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

The `Dice` class uses Kotlin's built-in `random()` function to generate random numbers within the specified range.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Testing

The project includes both unit tests and instrumented tests:

- **Unit Tests**: Located in `app/src/test/` - Test business logic without Android dependencies
- **Instrumented Tests**: Located in `app/src/androidTest/` - Test UI and Android-specific functionality

Run tests using:
```bash
# Unit tests
./gradlew test

# Instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

## Future Enhancements

- [ ] Add support for different dice types (4-sided, 8-sided, 12-sided, 20-sided)
- [ ] Include dice roll history
- [ ] Add sound effects for dice rolling
- [ ] Implement dice rolling animations
- [ ] Add multiple dice rolling capability
- [ ] Include statistics tracking

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built following Android development best practices
- Inspired by classic dice rolling applications
- Thanks to the Android development community

---

**Made with ❤️ for learning Android development**