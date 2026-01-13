package com.example.diceroller

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    private var currentDiceColor: Int = Color.BLACK

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button2)
        rollButton.setOnClickListener { rollDice() }

        // Set up color selection buttons
        setupColorButtons()

        // Set up registration button
        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener { 
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Set up the color selection buttons and their click listeners
     */
    private fun setupColorButtons() {
        val redButton: Button = findViewById(R.id.redButton)
        val blueButton: Button = findViewById(R.id.blueButton)
        val greenButton: Button = findViewById(R.id.greenButton)
        val yellowButton: Button = findViewById(R.id.yellowButton)
        val purpleButton: Button = findViewById(R.id.purpleButton)
        val orangeButton: Button = findViewById(R.id.orangeButton)

        redButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_red)) }
        blueButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_blue)) }
        greenButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_green)) }
        yellowButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_yellow)) }
        purpleButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_purple)) }
        orangeButton.setOnClickListener { changeDiceColor(getColor(R.color.dice_orange)) }
    }

    /**
     * Change the dice color
     */
    private fun changeDiceColor(color: Int) {
        currentDiceColor = color
        // Update the dice display with the new color
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.setTextColor(color)
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll it
        val dice = Dice(6)
        val diceRoll = dice.roll()

        // Update the screen with the dice roll
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = diceRoll.toString()
        resultTextView.setTextColor(currentDiceColor)
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}