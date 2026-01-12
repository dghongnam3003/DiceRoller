# Dice Roller App

A simple Android application that allows users to roll a virtual dice and view the result.

## Documentation

For detailed information, please refer to our comprehensive documentation:

- **[Architecture Documentation](docs/ARCHITECTURE.md)**: High-level architecture, components, and design patterns
- **[Setup Guide](docs/SETUP.md)**: Installation, configuration, and running the app
- **[API Documentation](docs/API_DOCUMENTATION.md)**: Detailed API reference for all components
- **[Contributing Guide](docs/CONTRIBUTING.md)**: How to contribute to the project

## Quick Start

### Prerequisites

- Android Studio (latest version recommended)
- Java Development Kit (JDK 8 or later)
- Android SDK with API 21 or later

### Running the App

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/dice-roller.git
   ```

2. Open the project in Android Studio

3. Build and run on an emulator or physical device

## Features

- ✅ Simple and intuitive UI
- ✅ Standard 6-sided dice
- ✅ Instant results
- ✅ No ads or tracking
- ✅ Works offline

## Screenshots

*(Add screenshots here showing the app in action)*

## Architecture

The app follows a simple MVC (Model-View-Controller) pattern:

- **Model**: `Dice` class - handles the dice rolling logic
- **View**: XML layouts - defines the user interface
- **Controller**: `MainActivity` - mediates between model and view

For more details, see the [Architecture Documentation](docs/ARCHITECTURE.md).

## Code Structure

```
dice-roller/
├── app/                  # Main application module
│   ├── src/              # Source code
│   │   ├── main/         # Main application code
│   │   │   ├── java/     # Kotlin source files
│   │   │   │   └── com/example/diceroller/
│   │   │   │       ├── MainActivity.kt
│   │   │   │       └── Dice.kt (if separated)
│   │   │   └── res/      # Resources (layouts, strings, etc.)
│   │   └── test/         # Unit tests
│   └── build.gradle.kts  # Module build configuration
├── docs/                 # Documentation (architecture, setup, API)
├── build.gradle.kts      # Project build configuration
└── settings.gradle.kts   # Project settings
```

## Usage

1. Open the app
2. Click the "Roll" button
3. View the result displayed on screen
4. Repeat as desired

## Customization

The app can be easily customized:

- Change the number of dice sides in `MainActivity.kt`
- Modify the UI in `activity_main.xml`
- Update colors in `res/values/colors.xml`
- Change strings in `res/values/strings.xml`

## Testing

The project includes basic testing:

- **Unit tests**: Test the `Dice` class functionality
- **Instrumentation tests**: Test UI interactions

Run tests with:
```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## Contributing

We welcome contributions! Please see our [Contributing Guide](docs/CONTRIBUTING.md) for details on:

- Reporting issues
- Suggesting features
- Submitting code changes
- Development guidelines
- Testing requirements

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or feedback:
- Email: example@example.com
- GitHub: [Dice Roller Repository](https://github.com/your-repository/dice-roller)

## Roadmap

Future enhancements planned:

- [ ] Multiple dice support
- [ ] Customizable number of sides
- [ ] Roll history
- [ ] Animations for dice rolling
- [ ] Different dice types (D4, D8, D10, D12, D20)
- [ ] Sound effects
- [ ] Dark mode support

## Acknowledgements

- Android Open Source Project
- Kotlin development team
- All contributors to this project

## Support

If you encounter any issues or have questions:

1. Check the documentation
2. Look at existing issues
3. Open a new issue with detailed information
4. Contact the maintainers

Thank you for using Dice Roller!
