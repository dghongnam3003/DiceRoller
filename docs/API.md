# API Documentation

## Overview

This document describes the public APIs and interfaces available in the Dice Roller application. While the app doesn't expose external APIs, it contains internal APIs that can be used for testing, extension, or integration.

## Core APIs

### Dice Class

The `Dice` class is the core API for dice rolling functionality.

#### Constructor

```kotlin
Dice(numSides: Int)
```

**Parameters:**
- `numSides` (Int): The number of sides on the dice. Must be a positive integer.

**Example:**
```kotlin
val sixSidedDice = Dice(6)
val twentySidedDice = Dice(20)
```

#### Methods

##### roll()

Generates a random number representing a dice roll.

```kotlin
fun roll(): Int
```

**Returns:**
- `Int`: A random integer between 1 and `numSides` (inclusive)

**Example:**
```kotlin
val dice = Dice(6)
val result = dice.roll() // Returns 1, 2, 3, 4, 5, or 6
```

**Thread Safety:**
- ✅ Thread-safe: Uses Kotlin's built-in random function
- ✅ Stateless: No shared mutable state

**Performance:**
- **Time Complexity**: O(1)
- **Memory**: Minimal allocation

### MainActivity API

The `MainActivity` class provides the main interface for user interactions.

#### Public Methods

##### onCreate(savedInstanceState: Bundle?)

Standard Android activity lifecycle method that initializes the UI.

```kotlin
override fun onCreate(savedInstanceState: Bundle?)
```

**Parameters:**
- `savedInstanceState` (Bundle?): Previously saved instance state

#### Private Methods

##### rollDice()

Handles the dice rolling logic and UI updates.

```kotlin
private fun rollDice()
```

**Behavior:**
1. Creates a new `Dice(6)` instance
2. Calls `dice.roll()` to get result
3. Updates the result TextView
4. Displays result to user

## UI Component APIs

### Button Interaction

#### Roll Button

**Resource ID**: `R.id.button2`
**Type**: `Button`
**Function**: Triggers dice rolling when clicked

**Usage:**
```kotlin
val rollButton: Button = findViewById(R.id.button2)
rollButton.setOnClickListener { rollDice() }
```

#### Result Display

**Resource ID**: `R.id.textView`
**Type**: `TextView`
**Function**: Displays the dice roll result

**Usage:**
```kotlin
val resultTextView: TextView = findViewById(R.id.textView)
resultTextView.text = diceRoll.toString()
```

## Extension Points

### Custom Dice Types

The `Dice` class can be extended to support different dice types:

```kotlin
// Standard dice types
val d4 = Dice(4)      // 4-sided die
val d6 = Dice(6)      // 6-sided die (standard)
val d8 = Dice(8)      // 8-sided die
val d10 = Dice(10)    // 10-sided die
val d12 = Dice(12)    // 12-sided die
val d20 = Dice(20)    // 20-sided die

// Custom dice
val percentile = Dice(100)  // Percentile die
val coin = Dice(2)          // Coin flip (1=heads, 2=tails)
```

### Multiple Dice Rolling

Example implementation for rolling multiple dice:

```kotlin
fun rollMultipleDice(count: Int, sides: Int): List<Int> {
    val dice = Dice(sides)
    return (1..count).map { dice.roll() }
}

// Usage
val results = rollMultipleDice(3, 6) // Roll 3 six-sided dice
val total = results.sum()            // Get total
```

### Statistical Analysis

Example API for dice statistics:

```kotlin
fun analyzeRolls(rolls: List<Int>): DiceStats {
    return DiceStats(
        total = rolls.sum(),
        average = rolls.average(),
        min = rolls.minOrNull() ?: 0,
        max = rolls.maxOrNull() ?: 0,
        count = rolls.size
    )
}
```

## Testing APIs

### Unit Test Support

The `Dice` class can be easily unit tested:

```kotlin
@Test
fun `dice roll returns value in valid range`() {
    val dice = Dice(6)
    val result = dice.roll()
    assertThat(result).isInRange(1, 6)
}

@Test
fun `dice roll distribution is approximately uniform`() {
    val dice = Dice(6)
    val results = (1..6000).map { dice.roll() }
    val frequencies = results.groupingBy { it }.eachCount()
    
    // Each number should appear approximately 1000 times
    frequencies.values.forEach { count ->
        assertThat(count).isInRange(900, 1100)
    }
}
```

### Mock and Test Doubles

For testing UI interactions:

```kotlin
class TestDice(private val fixedResult: Int) : DiceInterface {
    override fun roll(): Int = fixedResult
}

// Usage in tests
val mockDice = TestDice(4)
val result = mockDice.roll() // Always returns 4
```

## Error Handling

### Current Behavior

The current implementation has minimal error handling:

- **Invalid numSides**: No validation (could cause unexpected behavior)
- **UI errors**: Standard Android exception handling

### Recommended Enhancements

```kotlin
class Dice(private val numSides: Int) {
    init {
        require(numSides > 0) { "Number of sides must be positive" }
    }
    
    fun roll(): Int {
        return try {
            (1..numSides).random()
        } catch (e: Exception) {
            throw DiceRollException("Failed to roll dice", e)
        }
    }
}

class DiceRollException(message: String, cause: Throwable? = null) : 
    Exception(message, cause)
```

## Integration Examples

### Custom Activity Integration

```kotlin
class CustomDiceActivity : AppCompatActivity() {
    private lateinit var dice: Dice
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize with custom dice
        val sides = intent.getIntExtra("dice_sides", 6)
        dice = Dice(sides)
        
        setupUI()
    }
    
    private fun rollAndDisplay() {
        val result = dice.roll()
        updateUI(result)
    }
}
```

### Fragment Integration

```kotlin
class DiceFragment : Fragment() {
    private val dice = Dice(6)
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment implementation
        return inflater.inflate(R.layout.fragment_dice, container, false)
    }
    
    fun rollDice(): Int {
        return dice.roll()
    }
}
```

## Future API Enhancements

### Planned Features

1. **Async Rolling**:
```kotlin
suspend fun rollAsync(): Int
```

2. **Observable Results**:
```kotlin
fun rollWithCallback(callback: (Int) -> Unit)
```

3. **Dice Configuration**:
```kotlin
data class DiceConfiguration(
    val sides: Int,
    val modifier: Int = 0,
    val advantage: Boolean = false
)
```

4. **Roll History**:
```kotlin
interface DiceHistory {
    fun getLastRolls(count: Int): List<Int>
    fun clearHistory()
    fun getTotalRolls(): Int
}
```

## Version Compatibility

### Current Version: 1.0
- **API Level**: Stable
- **Breaking Changes**: None planned
- **Backwards Compatibility**: N/A (initial version)

### Future Versioning Strategy
- **Minor updates**: New features, backwards compatible
- **Major updates**: Breaking changes, migration guide provided
- **Patch updates**: Bug fixes, full compatibility

## Performance Considerations

### API Performance Characteristics

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|----------------|------------------|--------|
| Dice() | O(1) | O(1) | Constructor |
| roll() | O(1) | O(1) | Random generation |
| UI Update | O(1) | O(1) | TextView update |

### Memory Usage
- **Dice instance**: ~16 bytes
- **Roll result**: 4 bytes (Int)
- **Total footprint**: Negligible for single-dice usage

### Optimization Tips
1. **Reuse Dice instances** for multiple rolls
2. **Batch UI updates** for multiple dice
3. **Use coroutines** for heavy statistical analysis

## Conclusion

The Dice Roller APIs provide a simple but flexible foundation for dice-rolling functionality. The clean separation between logic (`Dice`) and presentation (`MainActivity`) makes the code testable and extensible. Future enhancements can build upon this foundation while maintaining backwards compatibility.