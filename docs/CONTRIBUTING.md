# Contributing Guide

Thank you for your interest in contributing to the Dice Roller project! We welcome contributions from everyone.

## Ways to Contribute

### 🐛 Bug Reports
Help us identify and fix issues:
- Report bugs via GitHub Issues
- Include steps to reproduce
- Provide device/OS information
- Attach screenshots if helpful

### 🚀 Feature Requests
Suggest new features or improvements:
- Open a GitHub Issue with your idea
- Describe the use case
- Explain why it would be valuable
- Include mockups if applicable

### 💻 Code Contributions
Submit pull requests for:
- Bug fixes
- New features
- Documentation improvements
- Test enhancements

### 📝 Documentation
Help improve our documentation:
- Fix typos or unclear explanations
- Add missing documentation
- Improve existing guides
- Create tutorials

### 🧪 Testing
Contribute to testing efforts:
- Write new test cases
- Improve test coverage
- Fix flaky tests
- Add performance tests

### 🌍 Localization
Help translate the app:
- Add new language support
- Improve existing translations
- Fix translation issues

## Getting Started

### Prerequisites
- Android Studio installed
- Basic Kotlin knowledge
- Understanding of Android development
- GitHub account

### Setup
1. Fork the repository
2. Clone your fork
3. Open in Android Studio
4. Build and run the app

```bash
git clone https://github.com/your-username/dice-roller.git
cd dice-roller
```

## Development Workflow

### 1. Find an Issue
- Check open GitHub Issues
- Look for "good first issue" labels
- Ask if you're unsure about anything

### 2. Create a Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Changes
- Follow coding standards
- Write tests for new functionality
- Update documentation
- Keep changes focused

### 4. Test Your Changes
- Run existing tests
- Add new tests
- Test on multiple devices
- Verify no regressions

### 5. Commit Changes
```bash
git add .
git commit -m "Add feature: brief description"
```

### 6. Push and Create PR
```bash
git push origin feature/your-feature-name
```
Then create a Pull Request on GitHub.

## Code Standards

### Kotlin Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4-space indentation
- Prefer immutability (`val` over `var`)
- Use meaningful names

### Android Best Practices
- Follow Android design guidelines
- Use AndroidX libraries
- Follow MVVM architecture patterns
- Handle configuration changes properly

### Testing
- Write tests for all new features
- Maintain high test coverage
- Follow Arrange-Act-Assert pattern
- Keep tests fast and reliable

### Documentation
- Update docs when changing functionality
- Use clear, concise language
- Include code examples
- Keep documentation up-to-date

## Pull Request Guidelines

### Before Submitting
- [ ] Code follows project standards
- [ ] All tests pass
- [ ] New tests added for new functionality
- [ ] Documentation updated
- [ ] No merge conflicts
- [ ] Changes are focused on one issue

### PR Template
```markdown
## Description

[Clear description of changes]

## Related Issue

Fixes #123 or Addresses #456

## Changes Made

- Change 1
- Change 2
- Change 3

## Testing

- Test 1: Description
- Test 2: Description
- Test 3: Description

## Screenshots (if applicable)

[Before/after screenshots]

## Checklist

- [ ] Code follows style guidelines
- [ ] Tests pass
- [ ] Documentation updated
- [ ] No breaking changes
```

## Review Process

### What to Expect
1. Initial review within 3-5 days
2. Feedback on code quality
3. Requests for changes if needed
4. Approval and merge

### Review Criteria
- Code quality and style
- Test coverage
- Documentation
- Performance impact
- Security considerations
- Backwards compatibility

### Common Feedback
- "Please add tests for this case"
- "Can you update the documentation?"
- "Let's discuss this approach"
- "Please rebase on latest main"

## Community Guidelines

### Code of Conduct
- Be respectful and inclusive
- Welcome new contributors
- Provide constructive feedback
- Be patient and helpful

### Communication
- Use GitHub Issues for discussions
- Keep conversations professional
- Be open to different viewpoints
- Focus on technical merits

### Recognition
- Contributors listed in project
- Significant contributions highlighted
- Regular contributor spotlights
- Public acknowledgment

## Technical Details

### Project Structure
```
dice-roller/
├── app/                  # Main application
│   ├── src/              # Source code
│   │   ├── main/         # Production code
│   │   └── test/         # Tests
├── docs/                 # Documentation
└── gradle/               # Build configuration
```

### Key Files
- `app/src/main/java/com/example/diceroller/MainActivity.kt` - Main logic
- `app/src/main/res/layout/activity_main.xml` - Main layout
- `app/build.gradle.kts` - Dependencies
- `docs/` - All documentation

### Build System
- Gradle Kotlin DSL
- Android Gradle Plugin
- Standard Android build process

## Advanced Contributions

### Architecture Changes
- Discuss in GitHub Issues first
- Provide detailed rationale
- Include performance analysis
- Consider backwards compatibility

### Major Features
- Break into smaller PRs
- Create design documents
- Get early feedback
- Implement incrementally

### Performance Improvements
- Profile before optimizing
- Provide benchmark data
- Consider trade-offs
- Document improvements

## Getting Help

### Resources
- Android Developer Documentation
- Kotlin Documentation
- Project README
- Existing code examples

### Asking Questions
- Open a GitHub Issue with your question
- Be specific about what you're trying to do
- Include relevant code snippets
- Describe what you've tried

### Mentorship
- Experienced contributors available
- Ask for guidance on complex issues
- Pair programming sessions possible
- Code review assistance

## Recognition

### Contributor Benefits
- Experience with real-world Android development
- Portfolio piece
- Networking opportunities
- Reference for future opportunities

### Contributor Levels
1. **First-time Contributor**: Small fixes and improvements
2. **Regular Contributor**: Multiple significant contributions
3. **Maintainer**: Review rights and project leadership
4. **Core Team**: Full project access and decision-making

## Legal

### License
- All contributions licensed under MIT License
- You retain copyright to your contributions
- Project maintains overall license

### Contributor License Agreement
- No formal CLA required
- By contributing, you agree to license your work under MIT
- You certify you have rights to contribute the code

## FAQ

### How do I get started?
Start with "good first issue" labeled issues and ask questions!

### What if I'm stuck?
Ask for help in the GitHub Issue - we're happy to assist!

### How long does review take?
Typically 3-5 days, depending on complexity and reviewer availability.

### Can I work on multiple issues?
Yes! But focus on completing one before starting another.

### What if my PR isn't accepted?
We'll explain why and suggest improvements. Don't be discouraged!

## Thank You!

We appreciate all contributions, big and small. Together we can make the Dice Roller app even better!

Happy coding! 🎲📱
