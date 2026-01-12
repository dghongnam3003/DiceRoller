# Flow Description

## Overview

This document describes the high-level flow of the Dice Roller application. The application allows users to simulate rolling a dice and view the result on the screen.

## Application Flow

### 1. **Initialization**
- The `MainActivity` is launched when the application starts.
- The activity's `onCreate` method is called, which sets up the user interface and initializes the dice rolling functionality.

### 2. **User Interaction**
- The user interacts with the application by clicking the "Roll" button.
- The button click triggers the `rollDice` method in the `MainActivity` class.

### 3. **Dice Rolling Logic**
- The `rollDice` method creates a new `Dice` object with 6 sides.
- The `roll` method of the `Dice` class is called to generate a random number between 1 and 6.

### 4. **Result Display**
- The result of the dice roll is displayed on the screen by updating the `TextView` with the rolled number.

## Key Components

### `MainActivity`
- **Purpose**: Manages the user interface and handles user interactions.
- **Methods**:
  - `onCreate`: Initializes the activity and sets up the UI.
  - `rollDice`: Handles the dice rolling logic and updates the UI with the result.

### `Dice`
- **Purpose**: Represents a dice with a specified number of sides and provides functionality to roll the dice.
- **Methods**:
  - `roll`: Generates a random number between 1 and the number of sides on the dice.

## Summary

The Dice Roller application follows a simple and intuitive flow:
1. The user launches the application.
2. The user clicks the "Roll" button.
3. The application generates a random number between 1 and 6.
4. The result is displayed on the screen.

This flow ensures a seamless and engaging user experience for simulating dice rolls.
