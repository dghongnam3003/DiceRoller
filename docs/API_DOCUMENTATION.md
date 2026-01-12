# Dice Roller App - API Documentation

## Overview

This document provides detailed API documentation for the Dice Roller app's components and classes.

## MainActivity

### Class: MainActivity

**Package**: `com.example.diceroller`

**Inherits**: `AppCompatActivity`

**Description**: The main activity that handles the dice rolling functionality and user interface.

### Methods

#### `onCreate(savedInstanceState: Bundle?)`

**Description**: Called when the activity is starting. Sets up the UI and event listeners.

**Parameters**:
- `savedInstanceState`: Bundle? - If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied

**Returns**: None

**Example**:
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    val rollButton: Button = findViewById(R.id.button2)
    rollButton.setOnClickListener { rollDice() }
}
```

#### `rollDice()`

**Description**: Handles the dice rolling logic. Creates a Dice instance, rolls it, and updates the UI with the result.

**Parameters**: None

**Returns**: None

**Example**:
```kotlin
private fun rollDice() {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    
    val resultTextView: TextView = findViewById(R.id.textView)
    resultTextView.text = diceRoll.toString()
}
```

## Dice Class

### Class: Dice

**Package**: `com.example.diceroller`

**Description**: Represents a dice with a configurable number of sides and provides rolling functionality.

### Constructor

#### `Dice(numSides: Int)`

**Description**: Creates a new Dice instance with the specified number of sides.

**Parameters**:
- `numSides`: Int - The number of sides on the dice (must be positive)

**Returns**: Dice instance

**Example**:
```kotlin
val dice = Dice(6)  // Creates a standard 6-sided dice
```

### Methods

#### `roll(): Int`

**Description**: Rolls the dice and returns a random result.

**Parameters**: None

**Returns**: Int - A random number between 1 and the number of sides (inclusive)

**Example**:
```kotlin
val dice = Dice(6)
val result = dice.roll()  // Returns a number between 1 and 6
```

**Implementation Details**:
- Uses Kotlin's range random function: `(1..numSides).random()`
- Thread-safe for single instances
- Returns values uniformly distributed

## UI Components

### Layout: activity_main.xml

**Description**: The main layout file that defines the user interface.

**Key Components**:
- `Button` (id: button2) - The roll button
- `TextView` (id: textView) - Displays the dice roll result

**Example Usage**:
```kotlin
val rollButton: Button = findViewById(R.id.button2)
val resultTextView: TextView = findViewById(R.id.textView)
```

## Resources

### Strings

**File**: `res/values/strings.xml`

**Description**: Contains string resources used in the app.

**Example**:
```xml
<resources>
    <string name="app_name">Dice Roller</string>
</resources>
```

### Colors

**File**: `res/values/colors.xml`

**Description**: Contains color definitions used in the app.

**Example**:
```xml
<resources>
    <color name="colorPrimary">#6200EE</color>
    <color name="colorOnPrimary">#FFFFFF</color>
</resources>
```

## Error Handling

### Potential Errors

1. **Invalid Number of Sides**: If `numSides` is less than 1, the `roll()` method may throw an exception.

2. **UI Not Found**: If layout IDs don't match, `findViewById()` will throw an exception.

### Best Practices

1. Always validate the number of sides before creating a Dice instance.
2. Ensure layout IDs match between XML and Kotlin code.
3. Handle potential exceptions in production code.

## Usage Examples

### Basic Usage

```kotlin
// Create a dice and roll it
val dice = Dice(6)
val result = dice.roll()
println("Rolled: $result")
```

### Multiple Rolls

```kotlin
val dice = Dice(6)
val results = mutableListOf<Int>()

repeat(10) {
    results.add(dice.roll())
}

println("Results: $results")
```

### Different Dice Types

```kotlin
// Standard 6-sided dice
val d6 = Dice(6)

// 20-sided dice (like in D&D)
val d20 = Dice(20)

// 100-sided dice
val d100 = Dice(100)
```

## Performance Characteristics

### Dice Class

- **Memory**: Minimal memory usage (stores only the number of sides)
- **CPU**: Very fast - uses Kotlin's optimized random function
- **Thread Safety**: Safe for single instances, but create separate instances for concurrent use

### MainActivity

- **Memory**: Standard activity memory usage
- **CPU**: Minimal processing - only handles UI updates
- **Battery**: Negligible impact

## Compatibility

### Android Versions

- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: Latest stable Android version
- **Tested on**: API 21 through latest

### Device Compatibility

- Works on all Android devices (phones, tablets)
- Supports all screen sizes and orientations
- No special hardware requirements

## Future API Enhancements

### Planned Features

1. **Multiple Dice Rolling**:
   ```kotlin
   fun rollMultiple(numDice: Int): List<Int>
   ```

2. **Custom Randomization**:
   ```kotlin
   fun roll(seed: Long): Int
   ```

3. **Dice Statistics**:
   ```kotlin
   fun getStatistics(numRolls: Int): Map<Int, Double>
   ```

## Migration Guide

### From Version 1.0 to 2.0

If the Dice class is moved to a separate file:

**Before**:
```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

**After**:
```kotlin
// Import the Dice class
import com.example.diceroller.Dice

// Usage remains the same
val dice = Dice(6)
val result = dice.roll()
```

## Deprecation Policy

- Deprecated APIs will be marked with `@Deprecated` annotation
- Deprecation notices will include migration guidance
- Deprecated APIs will be supported for at least one major version before removal

## Support

For API-related questions or issues:

1. Check the documentation
2. Look at example code
3. Open an issue with detailed information
4. Contact maintainers if needed

## Changelog

### Version 1.0

- Initial release
- Basic Dice class with roll functionality
- Simple UI with roll button and result display

### Version 1.1 (Planned)

- Separate Dice class into its own file
- Add input validation
- Improve error handling

## Additional Resources

- [Android Developer Documentation](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [AndroidX Documentation](https://developer.android.com/jetpack/androidx)
