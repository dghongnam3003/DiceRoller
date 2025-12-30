# Contributing to DiceRoller

Thank you for your interest in contributing to the DiceRoller Android app! We welcome contributions from developers of all skill levels.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Process](#development-process)
- [Submitting Changes](#submitting-changes)
- [Code Style Guidelines](#code-style-guidelines)
- [Testing](#testing)
- [Issue Reporting](#issue-reporting)

## Code of Conduct

This project adheres to a code of conduct that promotes a welcoming and inclusive environment for all contributors. Please be respectful and professional in all interactions.

## Getting Started

### Prerequisites
- Android Studio (latest stable version)
- JDK 8 or higher
- Android SDK
- Git

### Setting up the Development Environment

1. **Fork the repository**
   - Click the "Fork" button on the GitHub repository page
   - Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/DiceRoller.git
   cd DiceRoller
   ```

2. **Set up upstream remote**
   ```bash
   git remote add upstream https://github.com/dghongnam3003/DiceRoller.git
   ```

3. **Open in Android Studio**
   - Launch Android Studio
   - Open the project directory
   - Wait for Gradle sync to complete

4. **Verify setup**
   - Build the project: `Build > Make Project`
   - Run the app on an emulator or device

## Development Process

### Before You Start
1. Check existing [issues](https://github.com/dghongnam3003/DiceRoller/issues) to see if your idea is already being worked on
2. Create a new issue to discuss your proposed changes
3. Wait for maintainer approval before starting significant work

### Branching Strategy
1. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Keep your branch updated**
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

3. **Branch naming conventions:**
   - `feature/feature-name` for new features
   - `bugfix/issue-description` for bug fixes
   - `docs/documentation-update` for documentation changes
   - `refactor/component-name` for code refactoring

## Submitting Changes

### Pull Request Process

1. **Ensure your code follows the style guidelines**
2. **Update documentation** if you've made changes to functionality
3. **Add tests** for new features or bug fixes
4. **Make sure all tests pass**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

5. **Create a pull request**
   - Use a clear and descriptive title
   - Fill out the pull request template
   - Link any related issues

### Pull Request Template
```markdown
## Description
Brief description of the changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Refactoring
- [ ] Performance improvement

## Testing
- [ ] I have tested these changes locally
- [ ] I have added tests that prove my fix/feature works
- [ ] All existing tests pass

## Screenshots (if applicable)
Add screenshots to help explain your changes.

## Checklist
- [ ] My code follows the project's style guidelines
- [ ] I have performed a self-review of my code
- [ ] I have commented my code where necessary
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
```

## Code Style Guidelines

### Kotlin Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Use meaningful names for variables, functions, and classes
- Add KDoc comments for public functions and classes

### Android-Specific Guidelines
- Follow [Android Code Style Guidelines](https://developer.android.com/kotlin/style-guide)
- Use `findViewById` with proper type declarations
- Handle activity lifecycle properly
- Use appropriate naming for resources (layouts, strings, etc.)

### Example Code Style
```kotlin
/**
 * Represents a dice with a specified number of sides.
 * 
 * @param numSides The number of sides on the dice (must be positive)
 */
class Dice(private val numSides: Int) {
    
    init {
        require(numSides > 0) { "Number of sides must be positive" }
    }
    
    /**
     * Rolls the dice and returns a random number.
     * 
     * @return A random integer between 1 and numSides (inclusive)
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### XML Style
- Use proper indentation (4 spaces)
- Group related attributes together
- Use descriptive IDs for views
- Follow naming conventions for resources

```xml
<Button
    android:id=\"@+id/rollDiceButton\"
    android:layout_width=\"wrap_content\"
    android:layout_height=\"wrap_content\"
    android:text=\"@string/roll_dice\"
    android:textSize=\"18sp\"
    app:layout_constraintEnd_toEndOf=\"parent\"
    app:layout_constraintStart_toStartOf=\"parent\"
    app:layout_constraintTop_toBottomOf=\"@id/diceResultTextView\" />
```

## Testing

### Types of Tests
1. **Unit Tests** (`app/src/test/`) - Test individual components
2. **Instrumented Tests** (`app/src/androidTest/`) - Test UI and integration

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# All tests
./gradlew check
```

### Writing Tests
- Write tests for new functionality
- Use descriptive test names
- Test edge cases and error conditions
- Mock dependencies when appropriate

### Example Unit Test
```kotlin
class DiceTest {
    
    @Test
    fun rollDice_returnsValueInRange() {
        val dice = Dice(6)
        val result = dice.roll()
        assertTrue(\"Dice result should be between 1 and 6\", result in 1..6)
    }
    
    @Test
    fun rollDice_withInvalidSides_throwsException() {
        assertThrows<IllegalArgumentException> {
            Dice(0)
        }
    }
}
```

## Issue Reporting

### Before Creating an Issue
1. Search existing issues to avoid duplicates
2. Check if the issue exists in the latest version
3. Gather relevant information about your environment

### Issue Template
```markdown
## Issue Type
- [ ] Bug Report
- [ ] Feature Request
- [ ] Documentation Issue
- [ ] Question

## Environment
- Android Studio version:
- Android SDK version:
- Device/Emulator:
- OS version:

## Description
Clear description of the issue or feature request.

## Steps to Reproduce (for bugs)
1. Step 1
2. Step 2
3. Step 3

## Expected Behavior
What you expected to happen.

## Actual Behavior
What actually happened.

## Screenshots
If applicable, add screenshots to help explain the issue.

## Additional Context
Any other context about the issue.
```

## Feature Requests

We welcome feature suggestions! When submitting a feature request:

1. **Check existing issues** to see if it's already requested
2. **Provide clear use cases** for the feature
3. **Consider implementation complexity**
4. **Be open to discussion** about the feature design

### Current Feature Backlog
- Dice animations
- Multiple dice types
- Roll history
- Sound effects
- Custom themes

## Questions and Support

If you have questions about contributing:

1. Check the existing documentation
2. Search closed issues for similar questions
3. Create a new issue with the \"Question\" label
4. Join discussions in existing issues

## Recognition

Contributors will be recognized in the following ways:
- Listed in the repository contributors
- Mentioned in release notes for significant contributions
- Added to the README acknowledgments section

Thank you for contributing to DiceRoller! 🎲