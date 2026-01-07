# API Documentation

## Overview

This document provides detailed documentation for all classes, methods, and interfaces in the DiceRoller Android application. The API is designed to be simple, intuitive, and extensible.

## Package Structure

```
com.example.diceroller
├── MainActivity        # Main activity controller
└── Dice               # Core dice model
```

---

## Classes

### MainActivity

**Package**: `com.example.diceroller`  
**Extends**: `AppCompatActivity`  
**Purpose**: Main application controller that manages UI interactions and dice rolling logic.

#### Class Declaration
```kotlin
class MainActivity : AppCompatActivity()
```

#### Methods

##### `onCreate(savedInstanceState: Bundle?)`
**Visibility**: `override`  
**Parameters**: 
- `savedInstanceState: Bundle?` - Saved instance state from previous activity lifecycle

**Description**: Initializes the activity, sets up the UI layout, and configures event listeners.

**Implementation Details**:
- Sets content view to `activity_main.xml`
- Finds and configures the roll button
- Sets up click listener for dice rolling

**Example**:
```kotlin
// Automatically called by Android framework
// No direct usage required
```

**Annotations**: `@SuppressLint("MissingInflatedId")`

---

##### `rollDice()`
**Visibility**: `private`  
**Parameters**: None  
**Returns**: `Unit` (void)  

**Description**: Handles the dice rolling logic and updates the UI with the result.

**Algorithm**:
1. Creates a new 6-sided `Dice` object
2. Calls `roll()` to generate random number
3. Updates the result TextView with the dice value

**Implementation**:
```kotlin
private fun rollDice() {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    val resultTextView: TextView = findViewById(R.id.textView)
    resultTextView.text = diceRoll.toString()
}
```

**Side Effects**: Updates UI TextView with new dice roll result

---

### Dice

**Package**: `com.example.diceroller`  
**Purpose**: Model class representing a configurable dice with random number generation capability.

#### Class Declaration
```kotlin
class Dice(private val numSides: Int)
```

#### Constructor

##### Primary Constructor
**Parameters**:
- `numSides: Int` - The number of sides the dice should have

**Constraints**: 
- Must be positive integer
- Typical values: 4, 6, 8, 10, 12, 20

**Example**:
```kotlin
val standardDice = Dice(6)        // Standard 6-sided dice
val d20 = Dice(20)                // 20-sided dice for RPG
val coin = Dice(2)                // 2-sided for coin flip simulation
```

#### Properties

##### `numSides: Int`
**Visibility**: `private`  
**Type**: `Int`  
**Mutability**: Immutable (val)  
**Description**: Stores the number of sides for this dice instance.

#### Methods

##### `roll(): Int`
**Visibility**: `public`  
**Parameters**: None  
**Returns**: `Int` - Random number between 1 and `numSides` (inclusive)

**Description**: Generates a random number representing a dice roll result.

**Algorithm**: Uses Kotlin's `IntRange.random()` method to generate uniformly distributed random integers.

**Implementation**:
```kotlin
fun roll(): Int {
    return (1..numSides).random()
}
```

**Time Complexity**: O(1)  
**Space Complexity**: O(1)  

**Example Usage**:
```kotlin
val dice = Dice(6)
val result1 = dice.roll()  // Returns 1-6
val result2 = dice.roll()  // Independent roll, returns 1-6
```

**Thread Safety**: Not thread-safe. Create separate instances for concurrent use.

---

## Resource IDs

### Layout Resources

#### `R.layout.activity_main`
**File**: `app/src/main/res/layout/activity_main.xml`  
**Description**: Main activity layout containing UI elements for dice rolling interface.

#### UI Elements

##### `R.id.button2`
**Type**: `Button`  
**Purpose**: Trigger dice rolling action  
**Accessibility**: Should have contentDescription for screen readers

##### `R.id.textView`
**Type**: `TextView`  
**Purpose**: Display dice roll result  
**Content**: Numeric string representation of dice roll (1-6)

### String Resources

#### `R.string.app_name`
**Value**: "DiceRoller"  
**Usage**: Application display name

---

## Usage Examples

### Basic Usage
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Set up dice rolling
        val rollButton: Button = findViewById(R.id.button2)
        rollButton.setOnClickListener { rollDice() }
    }
    
    private fun rollDice() {
        val dice = Dice(6)
        val result = dice.roll()
        
        val resultView: TextView = findViewById(R.id.textView)
        resultView.text = result.toString()
    }
}
```

### Advanced Usage Examples

#### Multiple Dice Rolling
```kotlin
private fun rollMultipleDice() {
    val dice1 = Dice(6)
    val dice2 = Dice(6)
    
    val result1 = dice1.roll()
    val result2 = dice2.roll()
    val total = result1 + result2
    
    // Display results
    val resultView: TextView = findViewById(R.id.textView)
    resultView.text = "Dice 1: $result1, Dice 2: $result2, Total: $total"
}
```

#### Different Dice Types
```kotlin
private fun rollDifferentDice() {
    val d4 = Dice(4)      // 4-sided dice
    val d6 = Dice(6)      // 6-sided dice  
    val d8 = Dice(8)      // 8-sided dice
    val d10 = Dice(10)    // 10-sided dice
    val d12 = Dice(12)    // 12-sided dice
    val d20 = Dice(20)    // 20-sided dice
    
    val results = listOf(
        "D4: ${d4.roll()}",
        "D6: ${d6.roll()}",
        "D8: ${d8.roll()}",
        "D10: ${d10.roll()}",
        "D12: ${d12.roll()}",
        "D20: ${d20.roll()}"
    )
    
    val resultView: TextView = findViewById(R.id.textView)
    resultView.text = results.joinToString("\\n")
}
```

#### Statistics Collection
```kotlin
private fun collectStatistics() {
    val dice = Dice(6)
    val results = mutableListOf<Int>()
    
    // Roll 100 times
    repeat(100) {
        results.add(dice.roll())
    }
    
    // Calculate statistics
    val average = results.average()
    val distribution = results.groupingBy { it }.eachCount()
    
    // Display stats
    val resultView: TextView = findViewById(R.id.textView)
    resultView.text = """
        Average: %.2f
        Distribution: $distribution
    """.trimIndent().format(average)
}
```

---

## Error Handling

### Potential Issues

#### Invalid Dice Configuration
```kotlin
// Problematic usage - negative sides
val invalidDice = Dice(-1)  // Could cause issues
val result = invalidDice.roll()  // May return unexpected values
```

**Recommendation**: Add validation to Dice constructor:
```kotlin
class Dice(private val numSides: Int) {
    init {
        require(numSides > 0) { "Number of sides must be positive" }
    }
    // ... rest of implementation
}
```

#### UI Element Access
```kotlin
// Potential issue - UI element not found
val button: Button = findViewById(R.id.nonexistent_button)  // Returns null
button.setOnClickListener { ... }  // NullPointerException
```

**Solution**: Use safe casting and null checks:
```kotlin
val button: Button? = findViewById(R.id.button2)
button?.setOnClickListener { rollDice() }
```

---

## Testing Guidelines

### Unit Testing

#### Testing Dice Class
```kotlin
@Test
fun `dice roll returns value within range`() {
    val dice = Dice(6)
    repeat(100) {
        val result = dice.roll()
        assertTrue("Roll result $result not in range 1-6", result in 1..6)
    }
}

@Test
fun `dice with different sides work correctly`() {
    listOf(4, 6, 8, 10, 12, 20).forEach { sides ->
        val dice = Dice(sides)
        repeat(50) {
            val result = dice.roll()
            assertTrue("Roll result $result not in range 1-$sides", result in 1..sides)
        }
    }
}
```

### UI Testing

#### Testing Button Interaction
```kotlin
@Test
fun testDiceRollButtonClick() {
    onView(withId(R.id.button2))
        .perform(click())
    
    onView(withId(R.id.textView))
        .check(matches(withText(matchesPattern("[1-6]"))))
}
```

---

## Performance Considerations

### Random Number Generation
- **Algorithm**: Uses `kotlin.random.Random` (based on `java.util.Random`)
- **Performance**: O(1) time complexity
- **Quality**: Pseudo-random, sufficient for gaming applications
- **Thread Safety**: Not thread-safe, create separate instances for concurrent use

### Memory Usage
- **Dice Objects**: Lightweight, single integer field
- **UI Updates**: Minimal string allocation for result display
- **Lifecycle**: Objects are garbage collected when out of scope

### Best Practices
- Reuse Dice objects when possible
- Avoid creating new Dice objects in tight loops
- Consider using a single Dice instance per dice type

---

## Compatibility

### Android API Levels
- **Minimum SDK**: API 19 (Android 4.4 KitKat)
- **Target SDK**: API 33 (Android 13)
- **Compile SDK**: API 33

### Kotlin Version
- **Minimum**: Kotlin 1.8+
- **Features Used**: Range expressions, lambda expressions

### Dependencies
- AndroidX Core KTX 1.9.0
- AppCompat 1.6.1
- Material Design Components 1.8.0
- ConstraintLayout 2.1.4

---

## Migration Guide

### From Java to Kotlin
If converting similar Java code:

**Java**:
```java
public class Dice {
    private int numSides;
    
    public Dice(int numSides) {
        this.numSides = numSides;
    }
    
    public int roll() {
        Random random = new Random();
        return random.nextInt(numSides) + 1;
    }
}
```

**Kotlin**:
```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### API Changes
This is the initial API version. Future changes will be documented here with deprecation notices and migration paths.

---

## Support and Troubleshooting

### Common Issues

1. **Dice always returns same number**: Check if you're creating multiple Dice instances vs reusing one
2. **UI not updating**: Verify TextView ID matches layout file
3. **App crashes on button click**: Check button ID and ensure click listener is properly set

### Debug Information
Enable verbose logging by adding:
```kotlin
private fun rollDice() {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    
    // Debug logging
    Log.d("DiceRoller", "Rolled: $diceRoll")
    
    val resultTextView: TextView = findViewById(R.id.textView)
    resultTextView.text = diceRoll.toString()
}
```

For additional support, refer to the main README.md file or open an issue in the project repository.