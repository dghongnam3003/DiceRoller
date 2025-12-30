# Dice Roller Android App

A simple Android application that allows users to roll a virtual dice and see the result on the screen.

## 📱 Features

- **Simple Interface**: Clean and intuitive user interface with a single button to roll the dice
- **Random Number Generation**: Uses Kotlin's built-in random number generator for fair dice rolls
- **Instant Results**: Displays the dice roll result immediately on the screen
- **Material Design**: Follows Android Material Design guidelines

## 🛠️ Technical Specifications

- **Language**: Kotlin
- **Platform**: Android
- **Minimum SDK**: API 19 (Android 4.4 KitKat)
- **Target SDK**: API 33 (Android 13)
- **Compile SDK**: API 33
- **Build System**: Gradle with Kotlin DSL

## 📋 Dependencies

- **AndroidX Core KTX**: 1.9.0
- **AppCompat**: 1.6.1
- **Material Components**: 1.8.0
- **ConstraintLayout**: 2.1.4
- **JUnit**: 4.13.2 (Testing)
- **Espresso**: 3.5.1 (UI Testing)

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API level 33

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/DiceRoller.git
   cd DiceRoller
   ```

2. Open the project in Android Studio:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository folder
   - Click "OK"

3. Build and run:
   - Wait for Gradle sync to complete
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

## 🎮 How to Use

1. Launch the app on your Android device
2. Tap the "ROLL" button
3. See the dice result displayed on the screen (1-6)
4. Tap "ROLL" again to roll the dice again

## 🏗️ Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/diceroller/
│   │   │   └── MainActivity.kt          # Main activity with dice rolling logic
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # UI layout
│   │   │   ├── values/
│   │   │   │   ├── strings.xml         # String resources
│   │   │   │   ├── colors.xml          # Color resources
│   │   │   │   └── themes.xml          # App themes
│   │   │   └── ...
│   │   └── AndroidManifest.xml         # App manifest
│   ├── androidTest/                    # Instrumented tests
│   └── test/                           # Unit tests
└── build.gradle.kts                    # App-level build configuration
```

## 🔧 Key Components

### MainActivity.kt
- Handles the main app logic
- Contains the `rollDice()` function that generates random numbers
- Manages UI interactions and updates

### Dice Class
- Simple data class that encapsulates dice behavior
- Takes `numSides` parameter (currently set to 6)
- `roll()` method returns a random number between 1 and numSides

## 🧪 Testing

The project includes both unit tests and instrumented tests:

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## 🔄 Building the Project

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 📱 Screenshots

*Add screenshots of your app here*

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Known Issues

None at this time. If you find any bugs, please open an issue.

## 📈 Future Enhancements

- [ ] Add different dice types (4-sided, 8-sided, 10-sided, 12-sided, 20-sided)
- [ ] Add dice rolling animations
- [ ] Multiple dice rolling at once
- [ ] Dice roll history
- [ ] Custom dice colors/themes
- [ ] Sound effects
- [ ] Shake to roll feature

## 📞 Support

If you have any questions or need help, please open an issue in the GitHub repository.

---

Built with ❤️ using Android Studio and Kotlin