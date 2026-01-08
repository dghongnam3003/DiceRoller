# Contributing to Dice Roller

Thank you for your interest in contributing to the Dice Roller project! This document provides guidelines and instructions for contributors.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Process](#development-process)
- [Contribution Types](#contribution-types)
- [Pull Request Process](#pull-request-process)
- [Code Standards](#code-standards)
- [Testing Requirements](#testing-requirements)
- [Documentation Guidelines](#documentation-guidelines)
- [Issue Reporting](#issue-reporting)
- [Community](#community)

## Code of Conduct

### Our Standards

We are committed to providing a welcoming and inclusive environment for all contributors. Expected behavior includes:

- **Respectful Communication**: Use welcoming and inclusive language
- **Constructive Feedback**: Focus on what is best for the community
- **Empathy**: Show understanding towards other community members
- **Professional Conduct**: Accept constructive criticism gracefully
- **Community Focus**: Focus on what is best for the overall project

### Unacceptable Behavior

- Harassment, discrimination, or offensive comments
- Personal attacks or trolling
- Publishing private information without permission
- Any conduct that could be considered inappropriate in a professional setting

## Getting Started

### Prerequisites

Before contributing, ensure you have:
- Android Studio (latest stable version)
- JDK 11 or higher
- Git installed and configured
- Basic knowledge of Kotlin and Android development
- Familiarity with the project architecture (see [ARCHITECTURE.md](ARCHITECTURE.md))

### Development Environment Setup

1. **Fork the Repository**
   ```bash
   # Click 'Fork' on GitHub, then clone your fork
   git clone https://github.com/YOUR_USERNAME/dice-roller.git
   cd dice-roller
   ```

2. **Set Up Upstream Remote**
   ```bash
   git remote add upstream https://github.com/ORIGINAL_OWNER/dice-roller.git
   git remote -v
   ```

3. **Import into Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

4. **Verify Setup**
   ```bash
   # Run tests to ensure everything works
   ./gradlew test
   
   # Build the project
   ./gradlew assembleDebug
   ```

### First Contribution

For first-time contributors:
1. Look for issues labeled `good first issue` or `beginner-friendly`
2. Read the existing code to understand the project structure
3. Start with small changes like documentation improvements or bug fixes
4. Ask questions in issues or discussions if you need clarification

## Development Process

### Branching Strategy

We use a feature-branch workflow:

```bash
# Always start from the latest main
git checkout main
git pull upstream main

# Create a feature branch
git checkout -b feature/your-feature-name
```

#### Branch Naming Conventions
- **Features**: `feature/add-dice-animation`
- **Bug Fixes**: `bugfix/fix-roll-button-crash`
- **Documentation**: `docs/update-contributing-guide`
- **Refactoring**: `refactor/extract-dice-logic`

### Development Workflow

1. **Plan Your Changes**
   - Review existing issues or create a new one
   - Discuss your approach in the issue comments
   - Ensure the change aligns with project goals

2. **Implement Changes**
   - Write clean, well-documented code
   - Follow the existing code style and patterns
   - Add tests for new functionality
   - Update documentation as needed

3. **Test Your Changes**
   ```bash
   # Run unit tests
   ./gradlew test
   
   # Run Android tests (if device connected)
   ./gradlew connectedAndroidTest
   
   # Run lint checks
   ./gradlew lint
   ```

4. **Commit Changes**
   ```bash
   # Stage your changes
   git add .
   
   # Commit with a descriptive message
   git commit -m "feat: add dice roll animation with bounce effect"
   ```

5. **Push and Create Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```

## Contribution Types

### Code Contributions

#### New Features
- Discuss the feature in an issue before implementing
- Ensure it fits the project's scope and goals
- Include comprehensive tests
- Update documentation

**Examples:**
- Add dice roll animations
- Implement roll history
- Support for different dice types
- Sound effects for rolls

#### Bug Fixes
- Reference the issue number in your commit message
- Include steps to reproduce the bug
- Add regression tests when possible
- Verify the fix doesn't break existing functionality

#### Performance Improvements
- Provide benchmarks showing the improvement
- Ensure changes don't affect functionality
- Document any API changes

### Documentation Contributions

#### Code Documentation
- Add KDoc comments for public APIs
- Include usage examples
- Document complex algorithms or business logic

#### User Documentation
- Update README for new features
- Improve setup instructions
- Add troubleshooting guides

#### Technical Documentation
- Architecture documentation
- API reference updates
- Development guides

### Testing Contributions

#### Unit Tests
```kotlin
@Test
fun `dice roll returns value within bounds`() {
    val dice = Dice(6)
    val result = dice.roll()
    assertThat(result).isBetween(1, 6)
}
```

#### Integration Tests
```kotlin
@Test
fun `rolling dice updates UI correctly`() {
    // Test complete user workflow
}
```

#### Test Infrastructure
- Improve test utilities
- Add test data builders
- Enhance test readability

## Pull Request Process

### Before Submitting

- [ ] Code follows project style guidelines
- [ ] All tests pass locally
- [ ] New code has appropriate test coverage
- [ ] Documentation is updated
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up to date with main

### PR Description Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix (non-breaking change that fixes an issue)
- [ ] New feature (non-breaking change that adds functionality)
- [ ] Breaking change (fix or feature that changes existing functionality)
- [ ] Documentation update

## How Has This Been Tested?
- [ ] Unit tests
- [ ] Integration tests
- [ ] Manual testing on device/emulator

## Screenshots (if applicable)
Add screenshots for UI changes

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed a self-review of my code
- [ ] I have commented my code where necessary
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix/feature works
- [ ] New and existing tests pass locally
```

### Review Process

1. **Automated Checks**: CI/CD pipeline runs tests and linting
2. **Code Review**: Maintainers review for code quality and design
3. **Testing**: Changes are tested on different devices/API levels
4. **Approval**: At least one maintainer approval required
5. **Merge**: Squash and merge to main branch

### After Merge

- Delete your feature branch
- Pull the latest main to get your changes
- Consider contributing to related issues

## Code Standards

### Kotlin Style Guide

Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

```kotlin
// Good
class DiceRoller {
    private val numSides: Int = 6
    
    fun roll(): Int {
        return (1..numSides).random()
    }
}

// Bad
class diceroller{
    var numSides:Int=6
    fun Roll():Int{
        return (1..numSides).random()
    }
}
```

### Android Conventions

- Follow [Android Code Style Guidelines](https://source.android.com/setup/contribute/code-style)
- Use meaningful resource names
- Follow material design principles
- Handle configuration changes properly

### Documentation Standards

```kotlin
/**
 * Represents a configurable dice for rolling operations.
 *
 * This class provides thread-safe dice rolling functionality
 * with uniform distribution across all possible values.
 *
 * @param numSides The number of sides on the dice
 * @constructor Creates a dice with the specified number of sides
 * @throws IllegalArgumentException if numSides is less than 1
 *
 * @author Your Name
 * @since 1.0
 */
class Dice(private val numSides: Int) {
    // Implementation
}
```

## Testing Requirements

### Minimum Test Coverage
- All public methods must have unit tests
- Critical paths require integration tests
- UI components need basic interaction tests

### Test Quality Standards
- Tests should be deterministic and fast
- Use descriptive test names
- Follow AAA pattern (Arrange, Act, Assert)
- Mock external dependencies

### Running Tests Locally
```bash
# Unit tests
./gradlew test

# Android instrumentation tests
./gradlew connectedAndroidTest

# Specific test class
./gradlew test --tests DiceTest

# With coverage report
./gradlew testDebugUnitTestCoverage
```

## Documentation Guidelines

### Code Comments
- Explain **why**, not **what**
- Comment complex algorithms and business logic
- Keep comments up to date with code changes

### Commit Messages
Use [Conventional Commits](https://conventionalcommits.org/) format:

```
type(scope): description

[optional body]

[optional footer(s)]
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Build process or auxiliary tool changes

**Examples:**
```
feat(dice): add support for custom dice faces

- Allow dice to have custom face values instead of just numbers
- Add validation for face count matching sides
- Update UI to display custom faces

Closes #123
```

## Issue Reporting

### Bug Reports

Use the bug report template and include:
- **Device/OS version**: Android 10, Pixel 4
- **App version**: 1.0.0
- **Steps to reproduce**: Numbered list
- **Expected behavior**: What should happen
- **Actual behavior**: What actually happens
- **Screenshots/logs**: If applicable

### Feature Requests

Include:
- **Problem description**: What problem does this solve?
- **Proposed solution**: How should it work?
- **Alternatives considered**: Other approaches
- **Additional context**: Screenshots, mockups, examples

### Question/Support

- Check existing documentation first
- Search closed issues for similar questions
- Provide context about what you're trying to achieve

## Community

### Communication Channels
- **GitHub Issues**: Bug reports, feature requests
- **GitHub Discussions**: Questions, ideas, general discussion
- **Pull Request Comments**: Code-specific discussions

### Getting Help
- Read the documentation (README, DEVELOPMENT_GUIDE)
- Search existing issues and discussions
- Ask specific questions with context
- Be patient and respectful

### Helping Others
- Answer questions in issues and discussions
- Review pull requests
- Improve documentation
- Share your experience with the project

## Recognition

Contributors will be recognized in:
- **Contributors section** in README
- **Release notes** for significant contributions
- **Special mentions** for exceptional help

Thank you for contributing to Dice Roller! 🎲

---

*This guide is maintained by the project maintainers. Suggestions for improvements are always welcome.*