package com.example.diceroller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    private var diceColor: Int = Color.WHITE // Default dice color
    private val colorOptions = arrayOf(
        "Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Pink", "Cyan"
    )
    private val colorValues = intArrayOf(
        Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, 
        Color.parseColor("#800080"), Color.parseColor("#FFA500"), 
        Color.parseColor("#FFC0CB"), Color.CYAN
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button2)
        val colorPickerButton: Button = findViewById(R.id.colorPickerButton)

        // Load saved color preference
        loadDiceColor()

        rollButton.setOnClickListener { rollDice() }
        colorPickerButton.setOnClickListener { showColorPickerDialog() }
    }

    /**
     * Show a dialog to let the user choose a dice color.
     */
    private fun showColorPickerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Dice Color")
        builder.setItems(colorOptions) { dialog, which ->
            // Save the selected color
            diceColor = colorValues[which]
            saveDiceColor(diceColor)
            
            // Show a message to the user
            val colorName = colorOptions[which]
            val resultTextView: TextView = findViewById(R.id.textView)
            resultTextView.text = "Dice color changed to $colorName"
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        
        builder.show()
    }

    /**
     * Save the selected dice color to SharedPreferences.
     */
    private fun saveDiceColor(color: Int) {
        val sharedPref = getSharedPreferences("DiceRollerPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("diceColor", color)
            apply()
        }
    }

    /**
     * Load the saved dice color from SharedPreferences.
     */
    private fun loadDiceColor() {
        val sharedPref = getSharedPreferences("DiceRollerPrefs", Context.MODE_PRIVATE)
        diceColor = sharedPref.getInt("diceColor", Color.WHITE)
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll multiple dice
        val dice = Dice(6)
        val numDice = 3 // Roll 3 dice in a turn
        val diceRolls = dice.rollMultiple(numDice)
        val sum = diceRolls.sum()

        // Update the screen with the dice rolls and sum
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = "Rolled: ${diceRolls.joinToString(", ")}\nSum: $sum"
        
        // Apply the selected dice color to the result text
        resultTextView.setTextColor(diceColor)
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
    
    /**
     * Roll multiple dice and return the results as a list.
     * @param numDice The number of dice to roll.
     * @return A list of integers representing the results of each dice roll.
     */
    fun rollMultiple(numDice: Int): List<Int> {
        require(numDice > 0) { "Number of dice must be greater than 0" }
        return List(numDice) { roll() }
    }
    
    /**
     * Roll multiple dice and return the sum of the results.
     * @param numDice The number of dice to roll.
     * @return The sum of all dice rolls.
     */
    fun rollMultipleAndSum(numDice: Int): Int {
        return rollMultiple(numDice).sum()
    }
}