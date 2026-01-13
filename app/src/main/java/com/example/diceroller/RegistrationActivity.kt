package com.example.diceroller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows users to register for the dice roller app.
 */
class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener { registerUser() }

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener { 
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Register the user with the provided information
     */
    private fun registerUser() {
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Basic validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // In a real app, you would save this to a database or backend service
        // For this demo, we'll just show a success message
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

        // Store user info (simplified for demo)
        val sharedPreferences = getSharedPreferences("DiceRollerPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("email", email)
            apply()
        }

        // Return to main activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}