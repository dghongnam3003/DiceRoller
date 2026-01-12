# Contributing to Dice Roller App

Thank you for your interest in contributing to the Dice Roller app! We welcome contributions from everyone.

## How to Contribute

### Reporting Issues

If you find a bug or have a feature request:

1. Check the existing issues to avoid duplicates
2. Create a new issue with a clear title
3. Provide detailed information:
   - Steps to reproduce (for bugs)
   - Expected behavior
   - Actual behavior
   - Screenshots (if applicable)
   - Device and Android version

### Suggesting Features

For feature suggestions:

1. Open a new issue
2. Describe the feature in detail
3. Explain why it would be useful
4. Provide examples or mockups if possible

### Code Contributions

To contribute code:

1. Fork the repository
2. Create a new branch for your feature/bug fix:
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b bugfix/your-bug-fix
   ```
3. Make your changes
4. Commit your changes with clear messages:
   ```bash
   git commit -m "Add feature: your feature description"
   ```
5. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
6. Create a pull request to the main repository

## Development Guidelines

### Code Style

- Follow the existing code style and patterns
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions small and focused
- Follow Kotlin coding conventions

### Commit Messages

- Use present tense ("Add feature" not "Added feature")
- Keep the first line under 50 characters
- Provide detailed description in the body if needed
- Reference related issues (e.g., "Fixes #123")

### Pull Request Process

1. Ensure your code compiles without errors
2. Run existing tests to make sure nothing breaks
3. Add tests for new functionality
4. Update documentation if needed
5. Request review from maintainers
6. Address any feedback or requested changes

## Testing

### Running Tests

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

### Writing Tests

- Add unit tests for new functionality
- Test edge cases and error conditions
- Keep tests focused and fast
- Use descriptive test names

## Documentation

### Updating Documentation

If you add new features or change existing behavior:

1. Update relevant documentation files
2. Add code comments for complex logic
3. Update the README if public APIs change
4. Add examples if helpful

### Documentation Standards

- Use Markdown format
- Keep documentation clear and concise
- Use code examples where helpful
- Update architecture diagrams if needed

## Community Guidelines

### Code of Conduct

We expect all contributors to:

- Be respectful and inclusive
- Use welcoming and friendly language
- Be open to constructive feedback
- Focus on technical discussions
- Respect different viewpoints and experiences

### Communication

- Use clear and professional language
- Be patient and helpful
- Provide constructive feedback
- Ask questions when unsure
- Document decisions and discussions

## Getting Help

If you need help:

1. Check existing documentation
2. Look at similar code in the project
3. Ask questions in issues or discussions
4. Request clarification on requirements

## Recognition

All contributors will be recognized:

- In the project's CONTRIBUTORS file
- In release notes
- Through GitHub contributions

## License

By contributing to this project, you agree that your contributions will be licensed under the project's MIT License.

## Maintainers

Project maintainers are responsible for:

- Reviewing pull requests
- Merging approved changes
- Managing releases
- Maintaining documentation
- Ensuring code quality

## Release Process

1. Create a release branch
2. Update version numbers
3. Update changelog
4. Run final tests
5. Create GitHub release
6. Publish to app stores (if applicable)

## Questions?

If you have any questions about contributing, please open an issue or contact the maintainers.

Thank you for contributing to Dice Roller!
