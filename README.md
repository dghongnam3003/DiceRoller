# Dice Roller 🎲

A simple Android application that simulates rolling a dice. Built with Kotlin and Android SDK.

## Features

- **Simple Interface**: Clean and intuitive user interface
- **Random Dice Roll**: Simulates a 6-sided dice with random number generation
- **Instant Results**: Tap the roll button to get immediate results
- **Material Design**: Follows Android design guidelines

## Screenshots

*Add screenshots of your app here*

## Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or later
- Android SDK API 21+ (Android 5.0 Lollipop)
- Kotlin 1.9.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd diceroller
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory and select it

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/diceroller/
│   │   │   └── MainActivity.kt          # Main activity with dice logic
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # Main layout file
│   │   │   ├── values/
│   │   │   │   ├── strings.xml          # String resources
│   │   │   │   ├── colors.xml           # Color definitions
│   │   │   │   └── themes.xml           # App themes
│   │   │   └── AndroidManifest.xml      # App manifest
│   │   └── androidTest/                 # Instrumented tests
│   └── test/                            # Unit tests
├── build.gradle.kts                     # App-level build configuration
└── proguard-rules.pro                   # ProGuard rules
```

## How It Works

The app contains a simple `Dice` class that generates random numbers:

```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

When the user taps the "ROLL" button, the app:
1. Creates a new 6-sided dice object
2. Calls the `roll()` method to generate a random number (1-6)
3. Updates the TextView to display the result

## Key Components

- **MainActivity.kt**: Contains the main application logic and UI handling
- **Dice class**: Handles the dice rolling mechanics
- **activity_main.xml**: Defines the user interface layout
- **strings.xml**: Contains app text resources

## Development

### Running Tests

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

### Building APK

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Customization

You can easily customize the dice:

1. **Change number of sides**: Modify the `numSides` parameter in MainActivity
2. **Add multiple dice**: Create multiple Dice objects
3. **Customize UI**: Edit the layout files in `res/layout/`
4. **Add animations**: Implement roll animations for better UX

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Technologies Used

- **Kotlin**: Primary programming language
- **Android SDK**: Android development framework
- **Gradle**: Build automation tool
- **Android Studio**: Integrated development environment

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Android Developers Documentation
- Kotlin Documentation
- Material Design Guidelines

---

**Happy Rolling!** 🎲