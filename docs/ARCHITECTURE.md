# Architecture Documentation

## Overview

The Dice Roller app follows a simple but effective architecture pattern suitable for small Android applications. This document outlines the architectural decisions, patterns, and structure of the application.

## Architecture Pattern

### Single Activity Architecture
The app uses a **Single Activity Architecture** with a simple **Model-View-Controller (MVC)** pattern:

- **Model**: `Dice` class - Contains the business logic for dice rolling
- **View**: `activity_main.xml` - Defines the UI layout
- **Controller**: `MainActivity` - Handles user interactions and updates the view

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   MainActivity  │───▶│   Dice Model    │───▶│  Random Number  │
│   (Controller)  │    │   (Business)    │    │   Generator     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │
         ▼
┌─────────────────┐
│  activity_main  │
│   (View/UI)     │
└─────────────────┘
```

## Component Overview

### 1. MainActivity (Controller Layer)
**Location**: `app/src/main/java/com/example/diceroller/MainActivity.kt`

**Responsibilities**:
- Initialize UI components
- Handle user input (button clicks)
- Coordinate between Model and View
- Update UI with results

**Key Methods**:
- `onCreate()`: Sets up the activity and button listener
- `rollDice()`: Orchestrates dice rolling and UI updates

### 2. Dice (Model Layer)
**Location**: Defined within `MainActivity.kt`

**Responsibilities**:
- Encapsulate dice rolling logic
- Generate random numbers within specified range
- Maintain dice properties (number of sides)

**Key Features**:
- Configurable number of sides
- Uses Kotlin's built-in `random()` function
- Stateless design for thread safety

### 3. UI Layout (View Layer)
**Location**: `app/src/main/res/layout/activity_main.xml`

**Components**:
- `Button` (id: button2): Triggers dice rolling
- `TextView` (id: textView): Displays dice result
- `ConstraintLayout`: Root container for responsive layout

## Design Principles

### 1. Separation of Concerns
- **UI Logic**: Confined to `MainActivity`
- **Business Logic**: Isolated in `Dice` class
- **Layout**: Separated in XML resources

### 2. Single Responsibility
- `Dice` class: Only responsible for dice rolling
- `MainActivity`: Only handles UI coordination
- Layout: Only defines visual structure

### 3. Encapsulation
- `Dice` properties are private
- Internal implementation details are hidden
- Clean public interfaces

## Data Flow

```
User Interaction Flow:
1. User taps "ROLL" button
2. MainActivity.rollDice() is triggered
3. New Dice(6) instance is created
4. dice.roll() generates random number
5. TextView is updated with result
6. User sees the dice result
```

## Threading Model

### Current Implementation
- **Single-threaded**: All operations run on the UI thread
- **Synchronous**: Dice rolling happens instantly
- **No background processing**: Simple random number generation

### Scalability Considerations
For future enhancements that might require background processing:
- Use `CoroutineScope` for asynchronous operations
- Implement `ViewModel` for state management
- Consider `LiveData` or `StateFlow` for reactive updates

## Memory Management

### Current Footprint
- **Minimal memory usage**: Single activity, simple data structures
- **No memory leaks**: No long-lived references or listeners
- **Efficient object creation**: New `Dice` instance per roll (lightweight)

### Optimization Opportunities
- **Dice instance reuse**: Create once, use multiple times
- **View binding**: Replace `findViewById` calls
- **Resource optimization**: Minimize object allocation

## Testing Architecture

### Current Structure
```
src/
├── androidTest/           # Instrumented tests (UI)
│   └── ExampleInstrumentedTest.kt
├── test/                  # Unit tests (Logic)
│   └── ExampleUnitTest.kt
└── main/                  # Application code
```

### Testing Strategy
- **Unit Tests**: Test `Dice` class logic
- **Integration Tests**: Test MainActivity interactions
- **UI Tests**: Test button clicks and text updates

## File Organization

```
app/src/main/
├── java/com/example/diceroller/
│   └── MainActivity.kt              # Single activity containing all logic
├── res/
│   ├── layout/
│   │   └── activity_main.xml        # Main UI layout
│   ├── values/
│   │   ├── strings.xml              # Text resources
│   │   ├── colors.xml               # Color definitions
│   │   └── themes.xml               # App styling
│   └── drawable/                    # Icons and images
└── AndroidManifest.xml              # App configuration
```

## Configuration Management

### Build Configuration
- **Gradle**: Kotlin DSL for build scripts
- **API Levels**: minSdk 19, targetSdk 33
- **Kotlin**: Version 1.9.0

### Dependencies
- **AndroidX**: Modern Android libraries
- **Material Design**: UI components
- **ConstraintLayout**: Flexible layouts

## Security Considerations

### Current Implementation
- **No network calls**: Offline-only application
- **No data storage**: No persistent data
- **No permissions**: Minimal security surface

### Future Considerations
- Input validation for custom dice configurations
- Secure random number generation for gaming applications
- Data encryption if adding score persistence

## Performance Characteristics

### Strengths
- **Fast startup**: Minimal initialization
- **Responsive UI**: Instant feedback
- **Low resource usage**: Single activity design

### Potential Bottlenecks
- **UI thread blocking**: All operations on main thread
- **Memory allocation**: New Dice instance per roll
- **No caching**: No optimization for repeated operations

## Scalability Path

### Immediate Enhancements
1. **Extract Dice to separate file**
2. **Implement View Binding**
3. **Add proper error handling**

### Medium-term Evolution
1. **MVVM Architecture**: Add ViewModel layer
2. **Repository Pattern**: For future data sources
3. **Dependency Injection**: For better testability

### Long-term Considerations
1. **Multi-module architecture**: Feature-based modules
2. **Clean Architecture**: Domain/Data/Presentation layers
3. **Reactive programming**: RxJava or Coroutines with Flow

## Conclusion

The current architecture is well-suited for the app's simple requirements. The single activity approach keeps complexity low while maintaining clear separation of concerns. As the app grows, the architecture can evolve incrementally toward more sophisticated patterns like MVVM or Clean Architecture.