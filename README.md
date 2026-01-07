# DiceRoller Android App 🎲

A simple Android application that allows users to roll a virtual dice and view the result on screen.

## Features

- **Simple Dice Rolling**: Tap the "ROLL" button to roll a 6-sided dice
- **Instant Results**: The result is immediately displayed on screen
- **Clean Interface**: Minimalist design focused on functionality
- **Random Generation**: Uses Kotlin's built-in random number generation

## Screenshots

*Add screenshots of your app here*

## Requirements

- Android API level 19 (Android 4.4) or higher
- Kotlin 1.8+
- Android Studio (for development)

## Installation

### For Users
1. Download the APK from the releases section
2. Enable "Install from Unknown Sources" in your device settings
3. Install the APK file

### For Developers
1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd DiceRoller
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## Build Instructions

### Using Android Studio
1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Click "Run" or use `Shift + F10`

### Using Command Line
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/diceroller/
│   │   │   └── MainActivity.kt          # Main activity with dice logic
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # Main layout
│   │   │   ├── values/
│   │   │   │   ├── strings.xml          # App strings
│   │   │   │   ├── colors.xml           # App colors
│   │   │   │   └── themes.xml           # App themes
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   ├── test/                            # Unit tests
│   └── androidTest/                     # Instrumented tests
└── build.gradle.kts                     # App-level Gradle config
```

## Technical Details

- **Language**: Kotlin
- **Minimum SDK**: API 19 (Android 4.4 KitKat)
- **Target SDK**: API 33 (Android 13)
- **Architecture**: Single Activity with simple MVC pattern
- **Dependencies**:
  - AndroidX Core KTX
  - AppCompat
  - Material Design Components
  - ConstraintLayout

## How It Works

1. The app displays a button labeled "ROLL"
2. When pressed, it creates a `Dice` object with 6 sides
3. The `roll()` method generates a random number between 1-6
4. The result is displayed in a TextView on screen

## Code Highlights

The core dice functionality is implemented in the `Dice` class:

```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Future Enhancements

- [ ] Multiple dice support
- [ ] Different dice types (4, 8, 10, 12, 20 sides)
- [ ] Roll history
- [ ] Dice animation
- [ ] Sound effects
- [ ] Custom dice colors/themes
- [ ] Statistics tracking

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Created as part of Android development learning.

## Support

If you encounter any issues or have questions, please open an issue in the repository.

---

**Happy Rolling! 🎲**