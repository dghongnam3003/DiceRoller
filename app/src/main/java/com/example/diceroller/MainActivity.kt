package com.example.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen, and also provides user registration functionality.
 */
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button2)
        val registerButton: Button = findViewById(R.id.registerButton)

        rollButton.setOnClickListener { rollDice() }
        registerButton.setOnClickListener { registerUser() }
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
    }

    /**
     * Register a new user with the provided credentials.
     */
    private fun registerUser() {
        val usernameEditText: TextInputEditText = findViewById(R.id.usernameEditText)
        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val registrationStatus: TextView = findViewById(R.id.registrationStatus)

        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            registrationStatus.text = "Please fill in all fields"
            return
        }

        if (!isValidEmail(email)) {
            registrationStatus.text = "Please enter a valid email"
            return
        }

        if (password.length < 6) {
            registrationStatus.text = "Password must be at least 6 characters"
            return
        }

        // Create user object
        val newUser = User(username, email, password)

        // In a real app, you would save this to a database or backend service
        // For this example, we'll just show a success message
        registrationStatus.text = "Registration successful! Welcome, $username"
        registrationStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))

        // Clear form
        usernameEditText.text?.clear()
        emailEditText.text?.clear()
        passwordEditText.text?.clear()

        // Show toast notification
        Toast.makeText(this, "User registered: $username", Toast.LENGTH_SHORT).show()
    }

    /**
     * Validate email format using a simple regex pattern.
     */
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return emailPattern.matches(email)
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}

/**
 * Data class to represent a user.
 */
data class User(val username: String, val email: String, val password: String)