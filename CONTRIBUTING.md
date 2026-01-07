# Contributing to DiceRoller

Welcome! We're excited that you're interested in contributing to the DiceRoller Android application. This document provides guidelines and information for contributors to help make the process smooth and effective.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How to Contribute](#how-to-contribute)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Requirements](#testing-requirements)
- [Documentation](#documentation)
- [Issue Guidelines](#issue-guidelines)
- [Pull Request Process](#pull-request-process)
- [Community](#community)

---

## Code of Conduct

### Our Pledge

We pledge to make participation in our project a harassment-free experience for everyone, regardless of:
- Age, body size, disability, ethnicity, gender identity and expression
- Level of experience, education, socio-economic status
- Nationality, personal appearance, race, religion
- Sexual identity and orientation

### Our Standards

**Positive behavior includes:**
- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what is best for the community
- Showing empathy towards other community members

**Unacceptable behavior includes:**
- Harassment, trolling, or discriminatory language
- Personal attacks or political discussions
- Publishing others' private information
- Other conduct inappropriate in a professional setting

### Enforcement

Project maintainers are responsible for clarifying standards and will take appropriate action in response to unacceptable behavior. Report violations by contacting the project team.

---

## Getting Started

### Prerequisites

Before contributing, ensure you have:
- [ ] Android Studio Electric Eel (2022.1.1) or later
- [ ] JDK 11 or higher
- [ ] Git for version control
- [ ] Basic knowledge of Kotlin and Android development

### Initial Setup

1. **Fork the Repository**
   ```bash
   # Fork on GitHub, then clone your fork
   git clone https://github.com/YOUR-USERNAME/DiceRoller.git
   cd DiceRoller
   ```

2. **Set Up Development Environment**
   ```bash
   # Add upstream remote
   git remote add upstream https://github.com/ORIGINAL-OWNER/DiceRoller.git
   
   # Open in Android Studio
   # File -> Open -> Select the DiceRoller folder
   ```

3. **Verify Setup**
   ```bash
   # Run tests to ensure everything works
   ./gradlew test
   
   # Build the app
   ./gradlew assembleDebug
   ```

---

## How to Contribute

### Types of Contributions

#### 🐛 Bug Reports
- Found a bug? Help us fix it!
- Check existing issues first
- Provide clear reproduction steps
- Include device and version information

#### ✨ Feature Requests
- Have an idea for improvement?
- Check if it aligns with project goals
- Provide clear use case descriptions
- Consider implementation complexity

#### 📝 Documentation
- Improve README, guides, or code comments
- Fix typos or unclear explanations
- Add examples or tutorials
- Translate documentation

#### 🔧 Code Contributions
- Bug fixes
- Feature implementations
- Performance improvements
- Code refactoring
- Test improvements

#### 🎨 Design Contributions
- UI/UX improvements
- Asset creation (icons, illustrations)
- Accessibility enhancements
- User experience optimization

---

## Development Workflow

### Branch Strategy

We use a simplified Git flow:

```
main
├── develop (latest development)
├── feature/dice-animation
├── feature/multiple-dice
├── bugfix/roll-button-issue
└── hotfix/critical-crash
```

#### Branch Types

**Main Branch (`main`)**
- Contains stable, released code
- Direct commits not allowed
- Only accepts merges from release branches

**Development Branch (`develop`)**
- Integration branch for features
- Should always be buildable
- Base for feature branches

**Feature Branches (`feature/feature-name`)**
- New functionality development
- Branch from `develop`
- Merge back to `develop`

**Bug Fix Branches (`bugfix/issue-description`)**
- Non-critical bug fixes
- Branch from `develop`
- Merge back to `develop`

**Hotfix Branches (`hotfix/critical-issue`)**
- Critical production fixes
- Branch from `main`
- Merge to both `main` and `develop`

### Creating a Feature Branch

```bash
# Update your local develop branch
git checkout develop
git pull upstream develop

# Create and switch to feature branch
git checkout -b feature/multiple-dice-support

# Make your changes and commit
git add .
git commit -m \"Add support for rolling multiple dice\"

# Push to your fork
git push origin feature/multiple-dice-support
```

---

## Coding Standards

### Kotlin Style Guidelines

#### File Structure
```kotlin
// File: MainActivity.kt
package com.example.diceroller

// Android framework imports
import android.os.Bundle
import android.widget.Button

// AndroidX imports
import androidx.appcompat.app.AppCompatActivity

// Third-party imports
import kotlinx.coroutines.launch

// Local imports
import com.example.diceroller.utils.DiceHelper

/**
 * Main activity for dice rolling functionality.
 * 
 * Handles user interactions and coordinates between UI and business logic.
 */
class MainActivity : AppCompatActivity() {
    // Constants first
    companion object {
        private const val TAG = \"MainActivity\"
        private const val DEFAULT_DICE_SIDES = 6
    }
    
    // Properties
    private lateinit var rollButton: Button
    private lateinit var resultTextView: TextView
    
    // Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Implementation
    }
    
    // Public methods
    
    // Private methods
    private fun setupUI() {
        // Implementation
    }
    
    private fun rollDice() {
        // Implementation
    }
}
```

#### Naming Conventions

```kotlin
// Classes: PascalCase
class DiceRoller
class MainActivity
class UserPreferences

// Functions and variables: camelCase
fun rollDice()
fun calculateTotal()
val diceResult = 5
var isRolling = false

// Constants: SCREAMING_SNAKE_CASE
const val MAX_DICE_COUNT = 10
const val MIN_DICE_SIDES = 2

// Package names: lowercase
package com.example.diceroller
package com.example.diceroller.ui
package com.example.diceroller.data

// Resource IDs: snake_case with descriptive prefix
R.id.roll_button
R.id.dice_result_text
R.string.roll_button_text
```

#### Documentation Standards

```kotlin
/**
 * Represents a configurable dice for random number generation.
 *
 * This class provides functionality to simulate rolling dice with different
 * numbers of sides. Each instance maintains its configuration but doesn't
 * retain state between rolls.
 *
 * @param numSides Number of sides this dice has (must be positive)
 * @throws IllegalArgumentException if numSides is not positive
 *
 * Example usage:
 * ```kotlin
 * val d6 = Dice(6)
 * val result = d6.roll() // Returns 1-6
 * ```
 */
class Dice(private val numSides: Int) {
    
    init {
        require(numSides > 0) { \"Dice must have at least one side\" }
    }
    
    /**
     * Rolls the dice and returns a random result.
     *
     * @return Random integer between 1 and [numSides] inclusive
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### XML Style Guidelines

#### Layout Files
```xml
<?xml version=\"1.0\" encoding=\"utf-8\"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android=\"http://schemas.android.com/apk/res/android\"
    xmlns:app=\"http://schemas.android.com/apk/res-auto\"
    xmlns:tools=\"http://schemas.android.com/tools\"
    android:layout_width=\"match_parent\"
    android:layout_height=\"match_parent\"
    android:padding=\"@dimen/activity_padding\"
    tools:context=\".MainActivity\">

    <Button
        android:id=\"@+id/roll_button\"
        android:layout_width=\"wrap_content\"
        android:layout_height=\"wrap_content\"
        android:layout_marginTop=\"@dimen/button_margin_top\"
        android:text=\"@string/roll_button_text\"
        android:contentDescription=\"@string/roll_button_description\"
        app:layout_constraintEnd_toEndOf=\"parent\"
        app:layout_constraintStart_toStartOf=\"parent\"
        app:layout_constraintTop_toBottomOf=\"@+id/result_text\" />

    <TextView
        android:id=\"@+id/result_text\"
        android:layout_width=\"wrap_content\"
        android:layout_height=\"wrap_content\"
        android:textAppearance=\"@style/TextAppearance.App.DiceResult\"
        android:text=\"@string/initial_result_text\"
        app:layout_constraintBottom_toTopOf=\"@+id/roll_button\"
        app:layout_constraintEnd_toEndOf=\"parent\"
        app:layout_constraintStart_toStartOf=\"parent\"
        app:layout_constraintTop_toTopOf=\"parent\" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### Resource Organization
```xml
<!-- strings.xml - Group related strings -->
<resources>
    <!-- App metadata -->
    <string name=\"app_name\">DiceRoller</string>
    
    <!-- UI text -->
    <string name=\"roll_button_text\">ROLL</string>
    <string name=\"initial_result_text\">?</string>
    
    <!-- Accessibility -->
    <string name=\"roll_button_description\">Roll the dice to get a random number</string>
    <string name=\"result_description\">Dice roll result: %1$d</string>
    
    <!-- Error messages -->
    <string name=\"error_dice_creation\">Unable to create dice</string>
</resources>
```

---

## Testing Requirements

### Test Categories

#### Unit Tests (Required)
Every new feature must include unit tests:

```kotlin
// DiceTest.kt
class DiceTest {
    
    @Test
    fun `dice roll returns value within expected range`() {
        val dice = Dice(6)
        repeat(100) {
            val result = dice.roll()
            assertThat(result).isIn(1..6)
        }
    }
    
    @Test
    fun `dice with different sides work correctly`() {
        val testCases = listOf(4, 6, 8, 10, 12, 20)
        testCases.forEach { sides ->
            val dice = Dice(sides)
            val result = dice.roll()
            assertThat(result).isIn(1..sides)
        }
    }
    
    @Test
    fun `dice constructor validates input`() {
        assertThrows<IllegalArgumentException> {
            Dice(0)
        }
        
        assertThrows<IllegalArgumentException> {
            Dice(-5)
        }
    }
}
```

#### UI Tests (Recommended)
For UI changes, include instrumentation tests:

```kotlin
// MainActivityTest.kt
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun rollButton_click_updatesResultText() {
        onView(withId(R.id.roll_button))
            .perform(click())
            
        onView(withId(R.id.result_text))
            .check(matches(withText(matchesPattern(\"[1-6]\"))))
    }
    
    @Test
    fun multipleRolls_produceDifferentResults() {
        val results = mutableSetOf<String>()
        
        repeat(20) {
            onView(withId(R.id.roll_button)).perform(click())
            
            val resultText = getTextFromView(R.id.result_text)
            results.add(resultText)
        }
        
        // Should get multiple different results
        assertThat(results.size).isGreaterThan(1)
    }
}
```

#### Test Coverage Requirements
- **Minimum**: 80% line coverage for new code
- **Target**: 90% line coverage
- **Critical paths**: 100% coverage for core business logic

#### Running Tests
```bash
# Unit tests only
./gradlew test

# All tests (unit + instrumentation)
./gradlew connectedAndroidTest

# Test with coverage report
./gradlew jacocoTestReport
```

---

## Documentation

### Code Documentation

#### When to Document
- All public classes and methods
- Complex algorithms or business logic
- Non-obvious implementation decisions
- Workarounds for platform limitations

#### Documentation Style
```kotlin
/**
 * Calculates statistics for a series of dice rolls.
 *
 * This method analyzes roll patterns and provides insights into
 * distribution fairness and randomness quality.
 *
 * @param rolls List of dice roll results to analyze
 * @param diceType Type of dice used for the rolls
 * @return [DiceStatistics] containing analysis results
 * @throws IllegalArgumentException if rolls list is empty
 * 
 * @see DiceStatistics
 * @since 1.1.0
 */
fun calculateStatistics(
    rolls: List<Int>, 
    diceType: DiceType
): DiceStatistics {
    require(rolls.isNotEmpty()) { \"Cannot calculate statistics for empty roll list\" }
    
    // Implementation details that might not be obvious
    // could be documented with inline comments
}
```

### README Updates

When adding features, update relevant sections:
- Features list
- Usage examples
- Screenshots (if UI changes)
- Build instructions (if dependencies change)

### API Documentation

For new public APIs, update `API.md`:
- Class descriptions
- Method signatures
- Usage examples
- Migration notes (if changing existing APIs)

---

## Issue Guidelines

### Before Creating an Issue

1. **Search existing issues** to avoid duplicates
2. **Check documentation** for known solutions
3. **Reproduce the problem** consistently
4. **Test on latest version** if possible

### Bug Report Template

```markdown
## Bug Description
Brief description of what went wrong.

## Steps to Reproduce
1. Open the app
2. Tap the roll button
3. Notice the incorrect behavior

## Expected Behavior
Description of what should happen.

## Actual Behavior
Description of what actually happens.

## Environment
- **Device**: Pixel 4a
- **Android Version**: Android 12 (API 31)
- **App Version**: 1.0.0
- **Additional Context**: Any other relevant information

## Screenshots
If applicable, add screenshots to help explain the problem.

## Logs
If available, include relevant logcat output:
```
[Paste logcat output here]
```
```

### Feature Request Template

```markdown
## Feature Description
Clear description of the proposed feature.

## Use Case
Explain why this feature would be valuable:
- Who would benefit?
- What problem does it solve?
- How does it improve the user experience?

## Proposed Solution
Description of how you envision this working.

## Alternatives Considered
Other approaches you've considered and why this is preferred.

## Additional Context
Mock-ups, examples from other apps, or any other context.

## Implementation Complexity
- [ ] Simple (few hours)
- [ ] Medium (few days)
- [ ] Complex (weeks)
- [ ] Unsure

## Priority
- [ ] Critical
- [ ] High
- [ ] Medium
- [ ] Low
```

### Issue Labels

We use labels to categorize and prioritize issues:

**Type Labels**:
- `bug` - Something isn't working
- `enhancement` - New feature or request
- `documentation` - Improvements to docs
- `refactoring` - Code improvement without feature changes

**Priority Labels**:
- `priority:critical` - Security issues, crashes
- `priority:high` - Important features or major bugs
- `priority:medium` - Standard priority
- `priority:low` - Nice to have

**Component Labels**:
- `ui` - User interface related
- `logic` - Business logic or algorithms
- `testing` - Test-related issues
- `build` - Build system or dependencies

**Status Labels**:
- `good first issue` - Good for newcomers
- `help wanted` - Extra attention needed
- `wontfix` - This will not be worked on
- `duplicate` - This issue already exists

---

## Pull Request Process

### Before Submitting

#### Pre-submission Checklist
- [ ] Code follows project style guidelines
- [ ] Tests are written and passing
- [ ] Documentation is updated
- [ ] Commit messages are clear and descriptive
- [ ] No merge conflicts with target branch
- [ ] PR description clearly explains changes

#### Code Quality Checks
```bash
# Run all tests
./gradlew test connectedAndroidTest

# Check code style (if configured)
./gradlew ktlintCheck

# Run lint analysis
./gradlew lint

# Build release version
./gradlew assembleRelease
```

### Pull Request Template

```markdown
## Description
Brief description of changes made.

## Related Issue
Fixes #123 (if applicable)

## Type of Change
- [ ] Bug fix (non-breaking change that fixes an issue)
- [ ] New feature (non-breaking change that adds functionality)
- [ ] Breaking change (fix or feature that causes existing functionality to change)
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed
- [ ] Tested on multiple devices/versions

## Screenshots
If applicable, add screenshots of the changes.

## Checklist
- [ ] My code follows the project's style guidelines
- [ ] I have performed a self-review of my code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
```

### Review Process

#### Automatic Checks
- Continuous integration tests must pass
- Code style checks must pass
- No merge conflicts

#### Manual Review
- At least one maintainer approval required
- Code quality and adherence to standards
- Feature completeness and correctness
- Test coverage and quality

#### Review Criteria

**Code Quality**:
- [ ] Follows established patterns
- [ ] Is well-documented
- [ ] Handles errors appropriately
- [ ] Is efficiently implemented

**Testing**:
- [ ] Includes appropriate test coverage
- [ ] Tests are meaningful and comprehensive
- [ ] Manual testing has been performed

**Documentation**:
- [ ] Code is self-documenting or well-commented
- [ ] API documentation is updated
- [ ] User-facing changes are documented

#### Addressing Review Feedback

```bash
# Make changes based on feedback
git add .
git commit -m \"Address review feedback: improve error handling\"

# Push changes to your PR branch
git push origin feature/your-feature-name

# The PR will automatically update
```

### Merge Process

1. **Approval**: At least one maintainer approval
2. **CI Passing**: All automated checks must pass
3. **Conflicts Resolved**: No merge conflicts
4. **Squash and Merge**: Maintain clean commit history
5. **Branch Cleanup**: Delete feature branch after merge

---

## Community

### Communication Channels

#### GitHub
- **Issues**: Bug reports and feature requests
- **Discussions**: General questions and ideas
- **Pull Requests**: Code contributions

#### Best Practices for Communication
- Be respectful and constructive
- Provide clear, actionable feedback
- Ask questions if something is unclear
- Thank contributors for their time and effort

### Getting Help

#### For Contributors
- Check existing documentation first
- Search closed issues for similar problems
- Ask specific questions with context
- Provide reproduction steps when relevant

#### For Maintainers
- Respond to contributions promptly
- Provide clear, actionable feedback
- Recognize and appreciate contributions
- Maintain project documentation

### Recognition

We believe in recognizing contributions:

#### Types of Recognition
- **Contributor List**: All contributors listed in README
- **Release Notes**: Significant contributions mentioned
- **Special Thanks**: Outstanding contributions highlighted

#### How to Get Recognized
- Make meaningful contributions
- Help other contributors
- Improve documentation
- Provide thoughtful code reviews

---

## Development Resources

### Learning Resources

#### Android Development
- [Android Developer Documentation](https://developer.android.com/)
- [Android Kotlin Fundamentals](https://developer.android.com/courses/kotlin-android-fundamentals/overview)
- [Material Design Guidelines](https://material.io/design)

#### Kotlin Language
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Kotlin for Android](https://developer.android.com/kotlin)

#### Testing
- [Android Testing Documentation](https://developer.android.com/training/testing)
- [JUnit User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Espresso Testing Framework](https://developer.android.com/training/testing/espresso)

### Tools and Environment

#### Recommended Tools
- **Android Studio**: Primary IDE
- **Git**: Version control
- **Scrcpy**: Device screen mirroring
- **ADB**: Android debugging

#### Useful Plugins
- **Kotlin**: Language support
- **Android**: Android development
- **Git Blame**: Git integration
- **SonarLint**: Code quality

---

## FAQ

### General Questions

**Q: I'm new to Android development. Can I still contribute?**
A: Absolutely! Look for issues labeled \"good first issue\" and don't hesitate to ask questions.

**Q: How long does it take to review pull requests?**
A: We aim to provide initial feedback within 48 hours, though complex changes may take longer.

**Q: Can I work on multiple issues simultaneously?**
A: Yes, but we recommend focusing on one at a time to maintain quality.

### Technical Questions

**Q: What's the minimum Android version supported?**
A: Currently API 19 (Android 4.4). Check the build.gradle file for the most current information.

**Q: Are there any dependencies I should avoid?**
A: We prefer AndroidX libraries over legacy support libraries. Large dependencies should be discussed first.

**Q: How do I run tests locally?**
A: Use `./gradlew test` for unit tests and `./gradlew connectedAndroidTest` for instrumentation tests.

### Process Questions

**Q: Should I create an issue before starting work?**
A: For significant changes, yes. For small bug fixes, you can create a PR directly.

**Q: How do I know if a feature is wanted?**
A: Check existing issues and consider creating a feature request for discussion.

**Q: What if my PR conflicts with another change?**
A: Rebase your branch on the latest develop branch to resolve conflicts.

---

## Thank You!

Thank you for your interest in contributing to DiceRoller! Your contributions help make this project better for everyone. Whether you're fixing a bug, adding a feature, improving documentation, or helping other contributors, your efforts are appreciated.

Remember that everyone was new once, and we're here to help you succeed. Don't hesitate to ask questions, and welcome to the community!

---

*This contributing guide is a living document. If you have suggestions for improvements, please open an issue or submit a pull request.*