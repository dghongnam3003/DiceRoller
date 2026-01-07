# Development Guide

## Overview

This document provides comprehensive guidelines for developing and contributing to the DiceRoller Android application. It covers development environment setup, coding standards, testing procedures, and best practices.

## Prerequisites

### Required Software

#### Android Studio
- **Version**: Android Studio Electric Eel (2022.1.1) or later
- **Download**: [developer.android.com/studio](https://developer.android.com/studio)
- **Components**: 
  - Android SDK
  - Android Emulator
  - Kotlin plugin

#### Java Development Kit (JDK)
- **Version**: JDK 11 or later
- **Recommended**: JDK 17 (LTS)
- **Source**: Oracle JDK, OpenJDK, or included with Android Studio

#### Git
- **Version**: Git 2.30+ 
- **Purpose**: Version control and collaboration
- **Configuration**:
  ```bash
  git config --global user.name \"Your Name\"
  git config --global user.email \"your.email@example.com\"
  ```

### Optional Tools

#### Command Line Tools
- **Gradle Wrapper**: Included in project (`./gradlew`)
- **ADB (Android Debug Bridge)**: For device debugging
- **Scrcpy**: Screen mirroring for testing

#### Code Quality Tools
- **Detekt**: Kotlin static analysis
- **KtLint**: Kotlin code formatting
- **SonarLint**: Code quality plugin for IDEs

---

## Development Environment Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd DiceRoller
```

### 2. Open in Android Studio
1. Launch Android Studio
2. Select \"Open an Existing Project\"
3. Navigate to the cloned repository
4. Wait for Gradle sync to complete

### 3. SDK Configuration
Ensure the following SDK components are installed:
- **Android SDK Platform 33** (for target SDK)
- **Android SDK Platform 19** (for minimum SDK)
- **Android SDK Build-Tools 33.0.0**
- **Google Play Services**

### 4. Emulator Setup
Create a virtual device for testing:
1. Open AVD Manager (Tools → Device Manager)
2. Create Virtual Device
3. Choose device definition (e.g., Pixel 4)
4. Select system image (API 33 recommended)
5. Configure hardware settings
6. Start emulator

### 5. Physical Device Setup
For testing on real devices:
1. Enable Developer Options on device
2. Enable USB Debugging
3. Connect device via USB
4. Accept debugging authorization

---

## Project Structure Deep Dive

```
DiceRoller/
├── .git/                           # Git version control
├── .gitignore                      # Git ignore patterns
├── .idea/                          # Android Studio settings
├── app/                            # Main application module
│   ├── .gitignore                 # Module-specific ignores
│   ├── build.gradle.kts           # Module build configuration
│   ├── proguard-rules.pro         # Code obfuscation rules
│   └── src/                       # Source code directory
│       ├── androidTest/           # Instrumented tests
│       │   └── java/com/example/diceroller/
│       │       └── ExampleInstrumentedTest.kt
│       ├── main/                  # Main source set
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/diceroller/
│       │   │   └── MainActivity.kt
│       │   └── res/               # Resources
│       │       ├── drawable/      # Vector graphics, images
│       │       ├── layout/        # UI layout files
│       │       ├── mipmap-*/      # App icons (various densities)
│       │       ├── values/        # Values (strings, colors, etc.)
│       │       ├── values-night/  # Dark theme values
│       │       └── xml/           # XML configurations
│       └── test/                  # Unit tests
│           └── java/com/example/diceroller/
│               └── ExampleUnitTest.kt
├── gradle/                        # Gradle wrapper
├── build.gradle.kts              # Project-level build config
├── gradle.properties            # Gradle configuration
├── gradlew                      # Gradle wrapper script (Unix)
├── gradlew.bat                  # Gradle wrapper script (Windows)
├── settings.gradle.kts          # Project settings
└── README.md                    # Project documentation
```

---

## Coding Standards

### Kotlin Style Guide

#### Naming Conventions
```kotlin
// Classes: PascalCase
class MainActivity : AppCompatActivity()
class DiceRoller

// Functions and variables: camelCase
fun rollDice()
val diceResult = 6
var isRolling = false

// Constants: SCREAMING_SNAKE_CASE
const val MAX_DICE_SIDES = 20
const val MIN_DICE_SIDES = 1

// Package names: lowercase with dots
package com.example.diceroller
```

#### File Organization
```kotlin
// File: MainActivity.kt
package com.example.diceroller  // Package declaration

import android.os.Bundle         // Android imports
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity  // AndroidX imports

/**
 * Class-level documentation
 */
class MainActivity : AppCompatActivity() {
    // Constants first
    companion object {
        private const val TAG = \"MainActivity\"
    }
    
    // Properties
    private lateinit var rollButton: Button
    
    // Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        // Implementation
    }
    
    // Private methods
    private fun rollDice() {
        // Implementation
    }
}

// Additional classes in same file (if small and related)
class Dice(private val numSides: Int) {
    fun roll(): Int = (1..numSides).random()
}
```

#### Documentation Standards
```kotlin
/**
 * Represents a dice with configurable number of sides.
 *
 * @param numSides The number of sides this dice should have (must be positive)
 * @throws IllegalArgumentException if numSides is not positive
 */
class Dice(private val numSides: Int) {
    
    /**
     * Rolls the dice and returns a random result.
     *
     * @return A random integer between 1 and [numSides] (inclusive)
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### XML Style Guide

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

    <!-- Use meaningful IDs -->
    <Button
        android:id=\"@+id/rollButton\"
        android:layout_width=\"wrap_content\"
        android:layout_height=\"wrap_content\"
        android:text=\"@string/roll_button_text\"
        app:layout_constraintBottom_toBottomOf=\"parent\"
        app:layout_constraintEnd_toEndOf=\"parent\"
        app:layout_constraintStart_toStartOf=\"parent\"
        app:layout_constraintTop_toTopOf=\"parent\" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### String Resources
```xml
<!-- strings.xml -->
<resources>
    <string name=\"app_name\">DiceRoller</string>
    <string name=\"roll_button_text\">ROLL</string>
    <string name=\"result_text\">%1$d</string>
    <string name=\"content_description_roll_button\">Roll the dice</string>
</resources>
```

---

## Build Configuration

### Gradle Build Scripts

#### Project-level `build.gradle.kts`
```kotlin
// Top-level build file
plugins {
    id(\"com.android.application\") version \"8.0.0\" apply false
    id(\"com.android.library\") version \"8.0.0\" apply false
    id(\"org.jetbrains.kotlin.android\") version \"1.8.10\" apply false
}
```

#### Module-level `build.gradle.kts`
```kotlin
plugins {
    id(\"com.android.application\")
    id(\"org.jetbrains.kotlin.android\")
}

android {
    namespace = \"com.example.diceroller\"
    compileSdk = 33

    defaultConfig {
        applicationId = \"com.example.diceroller\"
        minSdk = 19
        targetSdk = 33
        versionCode = 1
        versionName = \"1.0\"
        
        testInstrumentationRunner = \"androidx.test.runner.AndroidJUnitRunner\"
    }

    buildTypes {
        getByName(\"release\") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(\"proguard-android-optimize.txt\"),
                \"proguard-rules.pro\"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = \"1.8\"
    }
}

dependencies {
    implementation(\"androidx.core:core-ktx:1.9.0\")
    implementation(\"androidx.appcompat:appcompat:1.6.1\")
    implementation(\"com.google.android.material:material:1.8.0\")
    implementation(\"androidx.constraintlayout:constraintlayout:2.1.4\")
    
    testImplementation(\"junit:junit:4.13.2\")
    androidTestImplementation(\"androidx.test.ext:junit:1.1.5\")
    androidTestImplementation(\"androidx.test.espresso:espresso-core:3.5.1\")
}
```

### Build Commands

#### Basic Build Operations
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Clean build
./gradlew clean

# Build and run tests
./gradlew build
```

#### Advanced Build Operations
```bash
# Lint checking
./gradlew lint

# Generate test coverage report
./gradlew jacocoTestReport

# Dependency verification
./gradlew dependencyUpdates
```

---

## Testing

### Unit Testing

#### Test Structure
```kotlin
// app/src/test/java/com/example/diceroller/DiceTest.kt
package com.example.diceroller

import org.junit.Test
import org.junit.Assert.*

class DiceTest {
    
    @Test
    fun `dice with six sides should return values between 1 and 6`() {
        val dice = Dice(6)
        repeat(100) {
            val result = dice.roll()
            assertTrue(\"Result $result not in range 1-6\", result in 1..6)
        }
    }
    
    @Test
    fun `dice with different sides should work correctly`() {
        val testCases = listOf(4, 6, 8, 10, 12, 20)
        testCases.forEach { sides ->
            val dice = Dice(sides)
            repeat(50) {
                val result = dice.roll()
                assertTrue(
                    \"Result $result not in range 1-$sides\", 
                    result in 1..sides
                )
            }
        }
    }
    
    @Test
    fun `dice results should be reasonably distributed`() {
        val dice = Dice(6)
        val results = mutableMapOf<Int, Int>()
        
        repeat(6000) {
            val result = dice.roll()
            results[result] = results.getOrDefault(result, 0) + 1
        }
        
        // Each value should appear roughly 1000 times (±200)
        for (i in 1..6) {
            val count = results[i] ?: 0
            assertTrue(
                \"Value $i appeared $count times, expected ~1000\",
                count in 800..1200
            )
        }
    }
}
```

#### Running Unit Tests
```bash
# Run all unit tests
./gradlew test

# Run tests for specific variant
./gradlew testDebugUnitTest

# Generate test report
./gradlew test --continue
# Reports available at: app/build/reports/tests/testDebugUnitTest/index.html
```

### Instrumentation Testing

#### UI Test Example
```kotlin
// app/src/androidTest/java/com/example/diceroller/MainActivityTest.kt
package com.example.diceroller

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testDiceRollButtonExists() {
        onView(withId(R.id.button2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDiceRollUpdatesResult() {
        onView(withId(R.id.button2))
            .perform(click())
            
        onView(withId(R.id.textView))
            .check(matches(withText(matchesRegex(\"[1-6]\"))))
    }
    
    @Test
    fun testMultipleRollsProduceDifferentResults() {
        val results = mutableSetOf<String>()
        
        repeat(10) {
            onView(withId(R.id.button2)).perform(click())
            
            // Note: This is a simplified test - in practice you'd need
            // to extract the text value to compare results
        }
    }
}
```

#### Running Instrumentation Tests
```bash
# Run all instrumentation tests
./gradlew connectedAndroidTest

# Run on specific device
./gradlew connectedDebugAndroidTest
```

---

## Debugging

### Android Studio Debugging

#### Setting Breakpoints
1. Click in the left margin next to line numbers
2. Run app in debug mode (Shift + F9)
3. Interact with app to trigger breakpoints
4. Use debug controls to step through code

#### Useful Debug Views
- **Variables**: View current variable values
- **Call Stack**: See method call hierarchy
- **Logcat**: Runtime logs and system messages
- **Network Inspector**: Monitor network traffic (not applicable for this app)

### Logging Best Practices

#### Log Levels
```kotlin
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = \"MainActivity\"
    }
    
    private fun rollDice() {
        Log.d(TAG, \"rollDice() called\")
        
        val dice = Dice(6)
        val diceRoll = dice.roll()
        
        Log.i(TAG, \"Dice rolled: $diceRoll\")
        
        try {
            val resultTextView: TextView = findViewById(R.id.textView)
            resultTextView.text = diceRoll.toString()
            Log.v(TAG, \"UI updated successfully\")
        } catch (e: Exception) {
            Log.e(TAG, \"Error updating UI\", e)
        }
    }
}
```

#### Log Filtering in Logcat
```bash
# Filter by tag
adb logcat MainActivity:D *:S

# Filter by package
adb logcat | grep com.example.diceroller
```

---

## Performance Optimization

### Memory Management

#### Avoiding Memory Leaks
```kotlin
class MainActivity : AppCompatActivity() {
    // Avoid static references to activities/contexts
    // companion object {
    //     private var activityReference: MainActivity? = null  // BAD
    // }
    
    // Use weak references if needed
    private var backgroundTask: AsyncTask<*, *, *>? = null
    
    override fun onDestroy() {
        backgroundTask?.cancel(true)
        super.onDestroy()
    }
}
```

#### Efficient Object Creation
```kotlin
class MainActivity : AppCompatActivity() {
    // Reuse objects when possible
    private val dice = Dice(6)  // Create once, reuse multiple times
    
    private fun rollDice() {
        // Don't create new dice each time
        val diceRoll = dice.roll()
        
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = diceRoll.toString()
    }
}
```

### UI Performance

#### Layout Optimization
- Use `ConstraintLayout` to reduce view hierarchy depth
- Avoid nested layouts when possible
- Use `ViewStub` for conditionally displayed views
- Minimize overdraw with layout inspector

#### Responsive UI
```kotlin
private fun rollDice() {
    // For this simple app, all operations are lightweight
    // For heavy operations, consider:
    
    // Option 1: Coroutines
    lifecycleScope.launch(Dispatchers.Default) {
        val result = heavyComputation()
        withContext(Dispatchers.Main) {
            updateUI(result)
        }
    }
    
    // Option 2: AsyncTask (deprecated but still used)
    // Use WorkManager or coroutines instead
}
```

---

## Version Control

### Git Workflow

#### Branch Naming
```bash
# Feature branches
git checkout -b feature/multiple-dice-support
git checkout -b feature/dice-animation

# Bug fix branches  
git checkout -b bugfix/roll-button-not-responding
git checkout -b hotfix/crash-on-rotation

# Release branches
git checkout -b release/v1.1.0
```

#### Commit Messages
```bash
# Good commit messages
git commit -m \"Add support for different dice types (4, 8, 12, 20 sides)\"
git commit -m \"Fix: Dice result not updating on button click\"
git commit -m \"Refactor: Extract dice logic into separate class\"
git commit -m \"Test: Add unit tests for Dice class\"

# Bad commit messages
git commit -m \"Fix stuff\"
git commit -m \"WIP\"
git commit -m \"asdfadsf\"
```

#### Pre-commit Hooks
Create `.git/hooks/pre-commit`:
```bash
#!/bin/sh
# Run tests before commit
./gradlew test
if [ $? -ne 0 ]; then
    echo \"Tests failed. Commit aborted.\"
    exit 1
fi

# Run lint
./gradlew lint
if [ $? -ne 0 ]; then
    echo \"Lint errors found. Commit aborted.\"
    exit 1
fi
```

---

## Code Review Guidelines

### What to Look For

#### Code Quality
- [ ] Code follows Kotlin style guidelines
- [ ] Meaningful variable and function names
- [ ] Appropriate comments and documentation
- [ ] No hardcoded strings (use string resources)
- [ ] Proper error handling

#### Functionality
- [ ] Feature works as described
- [ ] No regressions in existing functionality
- [ ] Edge cases are handled
- [ ] UI is responsive and intuitive

#### Testing
- [ ] Unit tests for new functionality
- [ ] UI tests for user interactions
- [ ] Tests pass consistently
- [ ] Good test coverage

#### Performance
- [ ] No memory leaks
- [ ] Efficient algorithms
- [ ] Minimal UI blocking operations
- [ ] Appropriate resource usage

### Code Review Checklist

```markdown
## Code Review Checklist

### Functionality ✅
- [ ] Feature works as expected
- [ ] No crashes or ANRs
- [ ] Handles edge cases
- [ ] Backwards compatible

### Code Quality ✅  
- [ ] Follows project coding standards
- [ ] Well-documented
- [ ] No code duplication
- [ ] Proper error handling

### Testing ✅
- [ ] Unit tests included
- [ ] Tests pass
- [ ] Good test coverage
- [ ] Manual testing completed

### Performance ✅
- [ ] No memory leaks
- [ ] Efficient implementation
- [ ] UI remains responsive
- [ ] Resource usage optimized

### Security ✅
- [ ] No sensitive data exposure
- [ ] Proper input validation
- [ ] Safe coding practices
```

---

## Continuous Integration

### GitHub Actions Example

Create `.github/workflows/android.yml`:
```yaml
name: Android CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Run tests
      run: ./gradlew test
      
    - name: Run lint
      run: ./gradlew lint
      
    - name: Build APK
      run: ./gradlew assembleDebug
      
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: debug-apk
        path: app/build/outputs/apk/debug/*.apk
```

---

## Troubleshooting

### Common Issues

#### Build Errors
```bash
# Gradle sync issues
./gradlew clean
# File → Sync Project with Gradle Files

# Dependency issues
./gradlew app:dependencies

# Cache issues  
./gradlew clean build --refresh-dependencies
```

#### Runtime Issues
```kotlin
// Check for null views
val button: Button? = findViewById(R.id.button2)
if (button == null) {
    Log.e(\"MainActivity\", \"Button not found in layout\")
    return
}

// Verify layout resources
Log.d(\"MainActivity\", \"Layout resource ID: ${R.layout.activity_main}\")
```

#### Testing Issues
```bash
# Device not found
adb devices

# Test failures
./gradlew test --info

# Emulator issues
./gradlew uninstallAll
```

### Debug Utilities

#### ADB Commands
```bash
# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# View logs
adb logcat

# Clear app data
adb shell pm clear com.example.diceroller

# Take screenshot
adb shell screencap /sdcard/screenshot.png
adb pull /sdcard/screenshot.png
```

---

## Resources and Learning

### Official Documentation
- [Android Developer Documentation](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)

### Best Practices
- [Android Development Best Practices](https://developer.android.com/topic/performance)
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Material Design Guidelines](https://material.io/design)

### Tools and Libraries
- [Android Studio User Guide](https://developer.android.com/studio/intro)
- [Gradle Build Tool](https://gradle.org/guides/)
- [JUnit Testing Framework](https://junit.org/junit5/docs/current/user-guide/)

---

This development guide provides a comprehensive foundation for working on the DiceRoller project. As the project evolves, update this document to reflect new practices, tools, and procedures.