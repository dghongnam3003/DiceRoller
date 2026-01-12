# Architecture Documentation

## Overview
The Dice Roller app follows a simple but effective architecture pattern that separates concerns and makes the code maintainable.

## Architecture Pattern
The app uses a simplified MVVM (Model-View-ViewModel) pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                        VIEW (UI)                             │
│  - XML layouts in res/layout/                                │
│  - Displays dice images and buttons                          │
│  - Handles user interactions (button clicks)                 │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                     VIEWMODEL (Logic)                        │
│  - MainActivity.kt                                            │
│  - Handles dice rolling logic                                │
│  - Manages state changes                                     │
│  - Updates the UI based on user actions                      │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                        MODEL (Data)                          │
│  - Simple data structures for dice rolls                     │
│  - Random number generation                                  │
│  - Basic state management                                    │
└─────────────────────────────────────────────────────────────┘
```

## Key Components

### 1. View Layer
**Location**: `app/src/main/res/layout/`

The View layer consists of XML layout files that define the user interface:

- **activity_main.xml**: Main layout containing:
  - Dice image views
  - Roll button
  - Result display

### 2. ViewModel Layer
**Location**: `app/src/main/java/com/example/diceroller/MainActivity.kt`

The ViewModel contains the business logic:

- **Dice Rolling Logic**: Generates random numbers between 1-6
- **State Management**: Tracks current dice value
- **UI Updates**: Changes dice images based on roll results
- **Event Handling**: Responds to button clicks

### 3. Model Layer
**Location**: Embedded in MainActivity.kt

The Model layer handles data:

- **Dice Class**: Represents a dice with rolling capability
- **Random Number Generation**: Uses Kotlin's Random class
- **State Objects**: Tracks current dice value and UI state

## Data Flow

1. **User Interaction**: User taps the "Roll" button
2. **Event Handling**: Button click is captured in MainActivity
3. **Business Logic**: Dice rolling logic generates a random number
4. **State Update**: Current dice value is updated
5. **UI Update**: Appropriate dice image is displayed

## Technical Details

### Random Number Generation
The app uses Kotlin's built-in `Random` class to generate dice rolls:

```kotlin
val randomNumber = Random.nextInt(6) + 1  // Generates 1-6
```

### Image Management
Dice images are stored as drawable resources and selected based on the roll result:

```kotlin
when (diceRoll) {
    1 -> R.drawable.dice_1
    2 -> R.drawable.dice_2
    // ... etc
}
```

### State Management
The app maintains simple state:
- Current dice value
- UI visibility states
- Button enabled/disabled states

## Best Practices Followed

1. **Separation of Concerns**: UI, logic, and data are separated
2. **Single Responsibility**: Each component has a clear purpose
3. **Resource Management**: Proper use of Android resources
4. **Error Handling**: Basic error handling for edge cases
5. **Testability**: Code structured for easy testing

## Future Architecture Improvements

As the app grows, consider:
1. **Full MVVM Implementation**: Separate ViewModel class
2. **Dependency Injection**: For better testability
3. **Repository Pattern**: For data management
4. **State Management Library**: Like ViewModel or LiveData
5. **Modularization**: Split into feature modules

## Testing Strategy

The architecture supports testing at multiple levels:

1. **Unit Tests**: Test individual functions (dice rolling logic)
2. **UI Tests**: Test user interactions (button clicks, image changes)
3. **Integration Tests**: Test complete user flows

## Performance Considerations

- **Minimal Memory Usage**: Only necessary resources loaded
- **Efficient Updates**: Only UI elements that change are updated
- **No Blocking Operations**: All operations run on main thread (for this simple app)

## Accessibility

The architecture supports accessibility features:
- Proper content descriptions for images
- Clear button labels
- Standard Android accessibility patterns
