# Contributing to Dice Roller

Thank you for your interest in contributing to the Dice Roller Android app! This document provides guidelines and information for contributors.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Issue Reporting](#issue-reporting)
- [Feature Requests](#feature-requests)
- [Documentation](#documentation)
- [Community](#community)

## Code of Conduct

### Our Pledge

We are committed to providing a friendly, safe, and welcoming environment for all contributors, regardless of experience level, gender, gender identity, sexual orientation, disability, personal appearance, body size, race, ethnicity, age, religion, or nationality.

### Expected Behavior

- Be respectful and inclusive in all interactions
- Welcome newcomers and help them get started
- Focus on constructive feedback and solutions
- Respect different viewpoints and experiences
- Show empathy towards other community members

### Unacceptable Behavior

- Harassment, discrimination, or offensive comments
- Personal attacks or trolling
- Publishing private information without permission
- Spam or excessive self-promotion
- Any other conduct inappropriate in a professional setting

### Enforcement

Project maintainers are responsible for enforcing these standards. Report unacceptable behavior to [project-email@example.com]. All complaints will be reviewed and investigated confidentially.

## Getting Started

### Prerequisites

Before contributing, ensure you have:

- **Android Studio**: Arctic Fox (2020.3.1) or newer
- **JDK**: Java 8 or 11
- **Git**: For version control
- **GitHub account**: For submitting pull requests

### Fork and Clone

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/DiceRoller.git
   cd DiceRoller
   ```

3. **Add upstream remote**:
   ```bash
   git remote add upstream https://github.com/ORIGINAL_OWNER/DiceRoller.git
   ```

### Environment Setup

1. **Open in Android Studio**:
   - File → Open → Select the project directory
   - Wait for Gradle sync to complete

2. **Verify setup**:
   ```bash
   ./gradlew check
   ```

3. **Run the app**:
   - Select a device/emulator
   - Click "Run" (Shift + F10)

## Development Workflow

### Branch Strategy

We use **Git Flow** with the following branches:

- **`main`**: Production-ready code
- **`develop`**: Integration branch for features
- **`feature/*`**: Individual feature development
- **`hotfix/*`**: Critical production fixes
- **`release/*`**: Release preparation

### Creating a Feature

1. **Create feature branch**:
   ```bash
   git checkout develop
   git pull upstream develop
   git checkout -b feature/your-feature-name
   ```

2. **Develop your feature**:
   - Make commits with clear messages
   - Follow coding standards
   - Add/update tests
   - Update documentation

3. **Test thoroughly**:
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ./gradlew lintDebug
   ```

4. **Push and create PR**:
   ```bash
   git push origin feature/your-feature-name
   ```

### Commit Guidelines

Follow **Conventional Commits** format:

```
type(scope): description

[optional body]

[optional footer]
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code formatting (no logic changes)
- `refactor`: Code restructuring (no feature/bug changes)
- `test`: Adding or modifying tests
- `chore`: Build/dependency updates

**Examples**:
```bash
feat(dice): add support for custom number of sides
fix(ui): resolve button click issue on Android 11
docs(readme): update installation instructions
test(dice): add unit tests for edge cases
```

## Coding Standards

### Kotlin Style Guide

Follow [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide):

#### Naming Conventions

```kotlin
// Classes: PascalCase
class DiceRoller

// Functions and variables: camelCase
fun rollDice()
val numberOfSides = 6

// Constants: SCREAMING_SNAKE_CASE
const val MAX_DICE_SIDES = 100

// Resource IDs: snake_case
R.id.roll_button
R.string.dice_result
```

#### Code Formatting

```kotlin
// Function declarations
fun rollDice(
    sides: Int = 6,
    modifier: Int = 0
): Int {
    return Dice(sides).roll() + modifier
}

// Class structure
class Dice(private val numSides: Int) {
    
    init {
        require(numSides > 0) { "Sides must be positive" }
    }
    
    fun roll(): Int {
        return (1..numSides).random()
    }
}

// Control flow
if (result > 0) {
    displayResult(result)
} else {
    showError()
}

// Collections
val standardDice = listOf(4, 6, 8, 10, 12, 20)
```

#### Documentation

```kotlin
/**
 * Represents a dice with a specified number of sides.
 *
 * This class encapsulates the logic for rolling dice and generating
 * random numbers within the valid range.
 *
 * @param numSides The number of sides on the dice. Must be positive.
 * @throws IllegalArgumentException if numSides is not positive.
 */
class Dice(private val numSides: Int) {
    
    /**
     * Rolls the dice and returns a random result.
     *
     * @return A random integer between 1 and [numSides], inclusive.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### XML Formatting

#### Layout Files

```xml
<?xml version=\"1.0\" encoding=\"utf-8\"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android=\"http://schemas.android.com/apk/res/android\"
    xmlns:app=\"http://schemas.android.com/apk/res-auto\"
    xmlns:tools=\"http://schemas.android.com/tools\"
    android:layout_width=\"match_parent\"
    android:layout_height=\"match_parent\"
    tools:context=\".MainActivity\">

    <Button
        android:id=\"@+id/roll_button\"
        android:layout_width=\"wrap_content\"
        android:layout_height=\"wrap_content\"
        android:text=\"@string/roll_button_text\"
        app:layout_constraintBottom_toBottomOf=\"parent\"
        app:layout_constraintEnd_toEndOf=\"parent\"
        app:layout_constraintStart_toStartOf=\"parent\"
        app:layout_constraintTop_toTopOf=\"parent\" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### Resource Files

```xml
<!-- strings.xml -->
<resources>
    <string name=\"app_name\">Dice Roller</string>
    <string name=\"roll_button_text\">ROLL</string>
    <string name=\"dice_result_description\">Dice result: %1$d</string>
</resources>

<!-- colors.xml -->
<resources>
    <color name=\"primary\">#6200EE</color>
    <color name=\"primary_variant\">#3700B3</color>
    <color name=\"secondary\">#03DAC6</color>
</resources>
```

### Architecture Guidelines

#### Single Responsibility

Each class should have one reason to change:

```kotlin
// Good: Focused responsibility
class Dice(private val sides: Int) {
    fun roll(): Int = (1..sides).random()
}

class DiceResultFormatter {
    fun format(result: Int): String = \"Result: $result\"
}

// Avoid: Multiple responsibilities
class DiceManager {
    fun roll(): Int { /* ... */ }
    fun formatResult(result: Int): String { /* ... */ }
    fun saveToDatabase(result: Int) { /* ... */ }
    fun sendAnalytics(result: Int) { /* ... */ }
}
```

#### Dependency Injection

Prefer constructor injection:

```kotlin
class MainActivity(
    private val diceRepository: DiceRepository = DefaultDiceRepository(),
    private val analytics: Analytics = FirebaseAnalytics()
) : AppCompatActivity() {
    // Implementation
}
```

## Testing Guidelines

### Testing Pyramid

Follow the Android testing pyramid:

```
        E2E Tests (Few)
           /\\
          /  \\
         /    \\
    Integration Tests (Some)
       /          \\
      /            \\
     /              \\
  Unit Tests (Many)
```

### Unit Tests

```kotlin
class DiceTest {
    
    @Test
    fun `roll returns value within valid range`() {
        val dice = Dice(6)
        val result = dice.roll()
        
        assertThat(result).isInRange(1, 6)
    }
    
    @Test
    fun `dice with zero sides throws exception`() {
        assertThrows<IllegalArgumentException> {
            Dice(0)
        }
    }
    
    @Test
    fun `multiple rolls produce different results eventually`() {
        val dice = Dice(6)
        val results = (1..100).map { dice.roll() }.toSet()
        
        // Should get multiple different values
        assertThat(results.size).isGreaterThan(1)
    }
}
```

### Integration Tests

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun clickRollButton_displaysResult() {
        onView(withId(R.id.roll_button))
            .perform(click())
        
        onView(withId(R.id.result_text))
            .check(matches(withText(matchesPattern(\"[1-6]\"))))
    }
}
```

### Test Naming Convention

Use descriptive test names:

```kotlin
// Good: Describes what is being tested
@Test
fun `dice roll with 6 sides returns value between 1 and 6`() { }

@Test
fun `clicking roll button updates result text view`() { }

// Avoid: Generic names
@Test
fun testRoll() { }

@Test
fun testButton() { }
```

### Code Coverage

Maintain minimum **80% code coverage** for:
- All business logic classes
- Public API methods
- Critical UI flows

Check coverage with:
```bash
./gradlew testDebugUnitTestCoverage
```

## Pull Request Process

### Before Submitting

- [ ] All tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation is updated
- [ ] Feature is complete and tested
- [ ] Commit messages are descriptive

### PR Description Template

```markdown
## Description
Brief description of changes and motivation.

## Type of Change
- [ ] Bug fix (non-breaking change that fixes an issue)
- [ ] New feature (non-breaking change that adds functionality)
- [ ] Breaking change (fix or feature that causes existing functionality to change)
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing completed
- [ ] All existing tests pass

## Screenshots (if applicable)
[Include screenshots for UI changes]

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No new warnings introduced
```

### Review Process

1. **Automated checks**: All CI/CD checks must pass
2. **Code review**: At least one maintainer approval required
3. **Testing**: Manual testing on different devices/API levels
4. **Documentation**: Verify docs are accurate and complete

### Addressing Feedback

- Respond to all review comments
- Make requested changes in new commits
- Don't force-push after review starts
- Request re-review when ready

## Issue Reporting

### Bug Reports

Use the bug report template:

```markdown
**Bug Description**
Clear description of the bug.

**Steps to Reproduce**
1. Go to...
2. Click on...
3. See error

**Expected Behavior**
What should happen.

**Actual Behavior**
What actually happens.

**Environment**
- Device: [e.g., Pixel 5]
- Android Version: [e.g., Android 12]
- App Version: [e.g., 1.2.0]

**Screenshots**
If applicable, add screenshots.

**Additional Context**
Any other relevant information.
```

### Performance Issues

For performance-related bugs:

- Include profiling data if available
- Describe performance expectations
- Mention device specifications
- Include steps to reproduce consistently

## Feature Requests

### Proposal Format

```markdown
**Feature Summary**
Brief description of the proposed feature.

**Problem Statement**
What problem does this solve?

**Proposed Solution**
Detailed description of how it should work.

**Alternatives Considered**
Other approaches you've considered.

**Additional Context**
Mockups, references, or examples.

**Implementation Notes**
Technical considerations (optional).
```

### Feature Evaluation

Features are evaluated based on:

- **User value**: How many users benefit?
- **Complexity**: Implementation effort required
- **Maintenance**: Long-term maintenance burden
- **Compatibility**: Impact on existing features
- **Performance**: Effect on app performance

## Documentation

### Required Documentation

For new features, include:

- **API documentation**: Public method signatures
- **User documentation**: How to use the feature
- **Technical documentation**: Implementation details
- **Examples**: Code samples and use cases

### Documentation Standards

- Use clear, concise language
- Include code examples
- Keep documentation up-to-date with code changes
- Use proper markdown formatting

### Documentation Structure

```
docs/
├── ARCHITECTURE.md      # System architecture
├── API.md              # API documentation
├── DEPLOYMENT.md       # Deployment guide
├── CONTRIBUTING.md     # This file
├── SECURITY.md         # Security guidelines
└── TROUBLESHOOTING.md  # Common issues and solutions
```

## Community

### Communication Channels

- **GitHub Issues**: Bug reports and feature requests
- **GitHub Discussions**: General questions and ideas
- **Email**: [maintainer-email@example.com] for security issues

### Getting Help

- Check existing issues and documentation first
- Use GitHub Discussions for questions
- Be specific and provide context
- Include relevant code snippets or screenshots

### Recognition

Contributors are recognized through:

- **Contributors file**: Listed in CONTRIBUTORS.md
- **Release notes**: Major contributions mentioned
- **GitHub**: Contributor badges and statistics

## Release Process

### Version Numbering

We use [Semantic Versioning](https://semver.org/):

- **MAJOR**: Breaking changes
- **MINOR**: New features (backwards compatible)
- **PATCH**: Bug fixes (backwards compatible)

Examples:
- `1.0.0` → `1.1.0` (new feature)
- `1.1.0` → `1.1.1` (bug fix)
- `1.1.1` → `2.0.0` (breaking change)

### Release Timeline

- **Minor releases**: Monthly
- **Patch releases**: As needed
- **Major releases**: Quarterly

### Beta Testing

- **Alpha**: Internal testing
- **Beta**: Public beta through Google Play
- **RC**: Release candidate (final testing)

## Thank You

We appreciate your contributions to making Dice Roller better! Every contribution, whether it's code, documentation, bug reports, or feature ideas, helps improve the app for everyone.

For questions about contributing, please open a GitHub Discussion or contact the maintainers directly.