# DiceRoller Android App

A simple and intuitive Android application that simulates rolling a 6-sided dice. Built with Kotlin and Android SDK.

## Features

- **Simple Interface**: Clean and user-friendly design with a large dice result display
- **Random Dice Rolling**: Uses Kotlin's built-in random number generator for fair dice rolls
- **Instant Results**: Tap the "ROLL" button to get immediate dice results
- **6-Sided Dice**: Simulates a standard dice with values from 1 to 6

## Screenshots

*Screenshots will be added once the app is running*

## Technical Details

### Architecture
- **Language**: Kotlin
- **Minimum SDK**: Android API level as specified in build.gradle
- **Target SDK**: Latest Android version
- **Architecture Pattern**: Simple Activity-based architecture
- **UI Framework**: Android Views with ConstraintLayout

### Project Structure
```
app/
├── src/main/
│   ├── java/com/example/diceroller/
│   │   └── MainActivity.kt          # Main activity with dice logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # UI layout
│   │   ├── values/
│   │   │   ├── strings.xml          # String resources
│   │   │   ├── colors.xml           # Color definitions
│   │   │   └── themes.xml           # App themes
│   │   └── drawable/                # App icons and images
│   └── AndroidManifest.xml          # App configuration
└── build.gradle.kts                 # App-level build configuration
```

## Getting Started

### Prerequisites
- Android Studio (latest version recommended)
- Android SDK
- JDK 8 or higher
- Gradle (bundled with Android Studio)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/dghongnam3003/DiceRoller.git
   cd DiceRoller
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to the cloned directory and select it

3. **Build the project**
   - Wait for Gradle sync to complete
   - Click "Build" → "Make Project" or press `Ctrl+F9`

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" → "Run 'app'" or press `Shift+F10`

### Quick Start
1. Launch the app on your device/emulator
2. Tap the "ROLL" button
3. Watch the dice result appear on screen
4. Tap "ROLL" again for another random result

## How It Works

### Core Components

#### MainActivity.kt
The main activity handles the user interface and dice rolling logic:

- **onCreate()**: Sets up the UI and button click listener
- **rollDice()**: Creates a dice object, rolls it, and updates the display
- **Dice class**: Simple class that generates random numbers from 1-6

#### Key Code Snippets

**Dice Rolling Logic:**
```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

**Button Click Handler:**
```kotlin
rollButton.setOnClickListener { rollDice() }
```

**UI Update:**
```kotlin
val resultTextView: TextView = findViewById(R.id.textView)
resultTextView.text = diceRoll.toString()
```

## Development

### Building from Source

1. **Debug Build**
   ```bash
   ./gradlew assembleDebug
   ```

2. **Release Build**
   ```bash
   ./gradlew assembleRelease
   ```

### Running Tests

1. **Unit Tests**
   ```bash
   ./gradlew test
   ```

2. **Instrumented Tests**
   ```bash
   ./gradlew connectedAndroidTest
   ```

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Maintain consistent indentation (4 spaces)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Contribution Guidelines
- Write clear commit messages
- Test your changes thoroughly
- Update documentation if needed
- Follow existing code style
- Add unit tests for new features

## Future Enhancements

- [ ] Add dice animations
- [ ] Support for different dice types (4-sided, 8-sided, 10-sided, etc.)
- [ ] Dice roll history
- [ ] Multiple dice rolling at once
- [ ] Sound effects
- [ ] Custom dice themes/colors
- [ ] Dice roll statistics
- [ ] Save/load dice configurations

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

- **Developer**: dghongnam3003
- **GitHub**: [https://github.com/dghongnam3003](https://github.com/dghongnam3003)
- **Repository**: [https://github.com/dghongnam3003/DiceRoller](https://github.com/dghongnam3003/DiceRoller)

## Acknowledgments

- Built as part of Android development learning
- Inspired by classic dice rolling applications
- Thanks to the Android development community for resources and support

---

*Happy Rolling! 🎲*