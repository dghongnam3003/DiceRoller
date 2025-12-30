# Dice Roller Android Application

## Overview
The Dice Roller is a simple Android application that allows users to simulate rolling a six-sided die. The app displays the result of the roll on the screen.

## Project Structure

```
DiceRoller/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/diceroller/
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/activity_main.xml
│   │   │   │   ├── values/strings.xml
│   │   │   │   └── ... (other resources)
│   │   │   └── AndroidManifest.xml
│   │   └── ... (test directories)
│   ├── build.gradle.kts
│   └── ...
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Features

### Current Features
1. **Dice Rolling**: Simulates rolling a standard six-sided die
2. **Result Display**: Shows the result of the dice roll on the screen
3. **Simple UI**: Clean interface with a button to roll the dice and a text view to display the result

### Technical Details
- **Language**: Kotlin
- **Platform**: Android
- **Minimum SDK**: Not specified (default in build.gradle)
- **Build System**: Gradle (Kotlin DSL)

## Components

### MainActivity.kt
The main activity class that:
- Sets up the user interface
- Handles button click events
- Manages dice rolling logic
- Updates the UI with roll results

### Dice Class
A simple utility class that:
- Takes the number of sides as a parameter
- Provides a `roll()` method that returns a random number between 1 and the number of sides

### Layout (activity_main.xml)
- ConstraintLayout containing:
  - TextView to display the dice roll result
  - Button to trigger dice rolling

## How It Works

1. User opens the app and sees the main screen with a button labeled "Roll"
2. User taps the "Roll" button
3. The app:
   - Creates a Dice object with 6 sides
   - Calls the roll() method to get a random number between 1-6
   - Updates the TextView to display the result
4. User can roll again by tapping the button

## Future Enhancements

Potential features that could be added:
- Multiple dice rolling (e.g., 2d6, 3d6)
- Different types of dice (d4, d8, d10, d12, d20)
- Roll history
- Animations for dice rolling
- Sound effects
- Customizable dice colors/themes
- Statistics tracking

## Build Instructions

1. Ensure you have Android Studio installed
2. Open the project in Android Studio
3. Build and run on an emulator or physical device

## Dependencies

The project uses standard Android dependencies:
- Android Gradle Plugin
- Kotlin Android Plugin
- AndroidX libraries

## Testing

The project includes basic test directories:
- Unit tests in `app/src/test/`
- Instrumentation tests in `app/src/androidTest/`

## License

This project is created as a learning exercise and doesn't specify a particular license.

## Author

Created as part of Android development learning materials.

## Version

1.0 - Initial release with basic dice rolling functionality
