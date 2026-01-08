# API Documentation

## Overview

This document provides comprehensive API documentation for the Dice Roller Android application. While this is a simple app, documenting the public interfaces helps maintain code clarity and enables future extensibility.

## Public APIs

### Dice Class

The `Dice` class provides the core functionality for dice rolling operations.

#### Class Declaration
```kotlin
class Dice(private val numSides: Int)
```

#### Constructor Parameters

| Parameter | Type | Description | Constraints |
|-----------|------|-------------|-------------|
| `numSides` | `Int` | Number of sides on the dice | Must be positive (> 0) |

#### Public Methods

##### `roll(): Int`

Generates a random number representing a dice roll.

**Returns:**
- `Int`: A random integer between 1 and `numSides` (inclusive)

**Time Complexity:** O(1)  
**Space Complexity:** O(1)

**Example Usage:**
```kotlin
val dice = Dice(6)
val result = dice.roll()
println(result) // Prints a number between 1 and 6
```

**Implementation Details:**
- Uses Kotlin's built-in `random()` function
- Ensures uniform distribution across all possible values
- Thread-safe (no shared mutable state)

#### Usage Examples

##### Basic Six-Sided Dice
```kotlin
val standardDice = Dice(6)
val rollResult = standardDice.roll()
// rollResult will be 1, 2, 3, 4, 5, or 6
```

##### Custom Dice
```kotlin
val twentySidedDice = Dice(20)
val d20Roll = twentySidedDice.roll()
// d20Roll will be between 1 and 20
```

##### Multiple Rolls
```kotlin
val dice = Dice(6)
val rolls = (1..5).map { dice.roll() }
// rolls contains 5 independent dice rolls
```

### MainActivity Class

The `MainActivity` class handles the user interface and coordinates dice rolling operations.

#### Class Declaration
```kotlin
class MainActivity : AppCompatActivity()
```

#### Key Methods

##### `onCreate(savedInstanceState: Bundle?)`

**Access:** `protected override`  
**Purpose:** Initializes the activity and sets up the user interface

**Parameters:**
- `savedInstanceState: Bundle?`: Saved state from previous instance (nullable)

**Implementation:**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    val rollButton: Button = findViewById(R.id.button2)
    rollButton.setOnClickListener { rollDice() }
}
```

##### `rollDice()`

**Access:** `private`  
**Purpose:** Handles dice rolling logic and UI updates

**Implementation Flow:**
1. Creates a new `Dice` instance with 6 sides
2. Calls `roll()` method to generate random result
3. Updates the result TextView with the new value

**Example:**
```kotlin
private fun rollDice() {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    
    val resultTextView: TextView = findViewById(R.id.textView)
    resultTextView.text = diceRoll.toString()
}
```

## Interface Contracts

### Dice Class Contract

#### Invariants
- `numSides` must remain constant after object creation
- `numSides` must be a positive integer

#### Preconditions
- Constructor: `numSides > 0`

#### Postconditions
- `roll()`: Returns value in range `[1, numSides]`
- `roll()`: Each call is independent (no side effects)

#### Error Handling
Currently, the Dice class doesn't perform explicit validation, but future versions should:

```kotlin
class Dice(private val numSides: Int) {
    init {
        require(numSides > 0) { "Number of sides must be positive" }
    }
    
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### MainActivity Contract

#### Lifecycle Contract
- Follows standard Android Activity lifecycle
- Properly initializes UI components in `onCreate()`
- Handles user interactions through event listeners

#### UI State Management
- Maintains current dice roll result in TextView
- Updates UI immediately upon user interaction
- No persistent state between app sessions

## Extension Points

### Custom Dice Types

The current API can be extended to support different dice types:

```kotlin
interface DiceInterface {
    fun roll(): Int
    fun getRange(): IntRange
}

class StandardDice(sides: Int) : DiceInterface {
    private val numSides = sides
    
    override fun roll(): Int = (1..numSides).random()
    override fun getRange(): IntRange = 1..numSides
}

class LoadedDice(sides: Int, private val bias: Int) : DiceInterface {
    private val numSides = sides
    
    override fun roll(): Int {
        // Implementation with bias toward specific number
        return if (Random.nextFloat() < 0.3f) bias else (1..numSides).random()
    }
    
    override fun getRange(): IntRange = 1..numSides
}
```

### Multiple Dice Support

Extend the API to handle multiple dice:

```kotlin
class DiceSet(private val dice: List<Dice>) {
    fun rollAll(): List<Int> = dice.map { it.roll() }
    fun rollSum(): Int = rollAll().sum()
    fun rollAndFormat(): String = rollAll().joinToString(" + ") + " = ${rollSum()}"
}

// Usage
val twoDice = DiceSet(listOf(Dice(6), Dice(6)))
val result = twoDice.rollSum() // Sum of two dice
```

### Animation Support

Add animation callbacks:

```kotlin
interface RollAnimationListener {
    fun onRollStart()
    fun onRollComplete(result: Int)
}

class AnimatedDice(private val numSides: Int) {
    private var animationListener: RollAnimationListener? = null
    
    fun setAnimationListener(listener: RollAnimationListener) {
        this.animationListener = listener
    }
    
    fun rollWithAnimation(): Int {
        animationListener?.onRollStart()
        
        // Simulate animation delay
        val result = (1..numSides).random()
        
        animationListener?.onRollComplete(result)
        return result
    }
}
```

## Data Models

### Roll Result

For future enhancements, consider a structured result model:

```kotlin
data class RollResult(
    val value: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val diceType: String = "6-sided"
) {
    fun isMaxRoll(maxValue: Int): Boolean = value == maxValue
    fun isMinRoll(): Boolean = value == 1
}
```

### Dice Configuration

For configurable dice:

```kotlin
data class DiceConfiguration(
    val sides: Int = 6,
    val label: String = "Standard Die",
    val color: String = "#FFFFFF",
    val customFaces: List<String>? = null
) {
    fun isValid(): Boolean = sides > 0 && (customFaces?.size == sides ?: true)
}
```

## Error Handling

### Current Error Handling

The current implementation has minimal error handling. Here are the potential error scenarios:

#### Runtime Errors
- **OutOfMemoryError**: Highly unlikely given simple operations
- **IllegalStateException**: Possible if UI components not found
- **NumberFormatException**: Not applicable (no string parsing)

#### Future Error Handling Strategy

```kotlin
sealed class DiceError : Exception() {
    object InvalidSides : DiceError()
    object RollFailed : DiceError()
    data class UIError(val component: String) : DiceError()
}

class SafeDice(private val numSides: Int) {
    fun roll(): Result<Int> {
        return try {
            if (numSides <= 0) {
                Result.failure(DiceError.InvalidSides)
            } else {
                val result = (1..numSides).random()
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(DiceError.RollFailed)
        }
    }
}
```

## Performance Characteristics

### Time Complexity
- **Dice.roll()**: O(1) - Constant time random number generation
- **MainActivity.rollDice()**: O(1) - Simple UI updates

### Space Complexity
- **Dice**: O(1) - Stores only the number of sides
- **MainActivity**: O(1) - No dynamic memory allocation for rolling

### Scalability
- **Memory**: Each Dice object uses minimal memory (~4 bytes for numSides)
- **CPU**: Random number generation is highly optimized
- **Concurrency**: Thread-safe for read operations

## Testing APIs

### Unit Test Helpers

```kotlin
object DiceTestUtils {
    fun testRandomness(dice: Dice, iterations: Int = 1000): Boolean {
        val results = (1..iterations).map { dice.roll() }
        val distribution = results.groupingBy { it }.eachCount()
        
        // Check if all possible values appear
        val expectedValues = 1..dice.numSides
        return expectedValues.all { it in distribution.keys }
    }
    
    fun testBounds(dice: Dice, iterations: Int = 1000): Boolean {
        val results = (1..iterations).map { dice.roll() }
        return results.all { it in 1..dice.numSides }
    }
}
```

### Mock Dice for Testing

```kotlin
class MockDice(private val predefinedResults: List<Int>) : DiceInterface {
    private var currentIndex = 0
    
    override fun roll(): Int {
        val result = predefinedResults[currentIndex % predefinedResults.size]
        currentIndex++
        return result
    }
    
    override fun getRange(): IntRange = 
        predefinedResults.minOrNull()!!..predefinedResults.maxOrNull()!!
}
```

## Version History

### Version 1.0.0 (Current)
- Basic `Dice` class with configurable sides
- Simple `roll()` method returning random integers
- Integration with `MainActivity` for UI updates

### Future Versions

#### Version 1.1.0 (Planned)
- Add input validation to Dice constructor
- Implement proper error handling with Result types
- Add roll history functionality

#### Version 1.2.0 (Planned)
- Support for custom dice faces (not just numbers)
- Multiple dice rolling in single operation
- Roll statistics and analytics

#### Version 2.0.0 (Future)
- Complete API redesign with interfaces
- Animation system integration
- Persistent storage for roll history
- Network multiplayer dice rolling

---

*This API documentation is maintained alongside the codebase. Please update when making changes to public interfaces.*