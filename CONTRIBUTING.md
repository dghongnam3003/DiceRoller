# Contributing to Dice Roller

We love your input! We want to make contributing to Dice Roller as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## Pull Requests

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the documentation.
4. Ensure the test suite passes.
5. Make sure your code lints.
6. Issue that pull request!

## Any contributions you make will be under the MIT Software License

In short, when you submit code changes, your submissions are understood to be under the same [MIT License](http://choosealicense.com/licenses/mit/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs using GitHub's [issue tracker](https://github.com/yourusername/DiceRoller/issues)

We use GitHub issues to track public bugs. Report a bug by [opening a new issue](https://github.com/yourusername/DiceRoller/issues/new).

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

## Code Style

### Kotlin Style Guide

We follow the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use 4 spaces for indentation
- Use camelCase for function and variable names
- Use PascalCase for class names
- Use meaningful names for variables and functions
- Add comments for complex logic

### Android-specific Guidelines

- Follow [Android's code style guidelines](https://developer.android.com/kotlin/style-guide)
- Use AndroidX libraries when possible
- Follow Material Design principles
- Optimize for performance and battery life

## Setting Up Development Environment

1. **Install Android Studio**: Download the latest version from [developer.android.com](https://developer.android.com/studio)

2. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/DiceRoller.git
   cd DiceRoller
   ```

3. **Open in Android Studio**: 
   - Launch Android Studio
   - Choose "Open an existing project"
   - Select the DiceRoller folder

4. **Sync project**: 
   - Wait for Gradle sync to complete
   - Install any missing SDK components if prompted

## Testing

Before submitting a pull request, make sure all tests pass:

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Lint Check
```bash
./gradlew lint
```

## Commit Message Guidelines

Use clear and meaningful commit messages:

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line

Example:
```
Add shake-to-roll feature

- Implement shake detection using accelerometer
- Add haptic feedback when dice is rolled
- Update UI to show shake instruction

Fixes #123
```

## Feature Requests

We track feature requests through GitHub issues. Before creating a new feature request:

1. Check if a similar request already exists
2. Clearly describe the feature and its benefits
3. Consider the scope and complexity
4. Be open to discussion and feedback

## Code Review Process

The core team looks at Pull Requests on a regular basis. After feedback has been given we expect responses within two weeks. After two weeks we may close the pull request if it isn't showing any activity.

## Community

Be respectful and constructive in all interactions. We want this to be a welcoming environment for everyone.

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

## Questions?

Feel free to open an issue for any questions about contributing!