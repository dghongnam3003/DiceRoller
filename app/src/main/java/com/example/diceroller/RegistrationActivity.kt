package com.example.diceroller

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * This activity allows users to register for the Dice Roller app.
 * Features a modern, user-friendly registration interface with Material Design components.
 */
class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registerButton: MaterialButton = findViewById(R.id.registerButton)
        val loginButton: MaterialButton = findViewById(R.id.loginButton)
        val usernameEditText: TextInputEditText = findViewById(R.id.usernameEditText)
        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val usernameLayout: TextInputLayout = findViewById(R.id.usernameEditText).parent.parent as TextInputLayout
        val emailLayout: TextInputLayout = findViewById(R.id.emailEditText).parent.parent as TextInputLayout
        val passwordLayout: TextInputLayout = findViewById(R.id.passwordEditText).parent.parent as TextInputLayout
        val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)

        // Add animation to buttons
        val fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        registerButton.startAnimation(fadeInAnimation)
        loginButton.startAnimation(fadeInAnimation)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (validateRegistration(username, email, password, usernameLayout, emailLayout, passwordLayout)) {
                // In a real app, you would save this to a database or backend
                // For this example, we'll just show a success message
                Toast.makeText(this, "Registration successful! Welcome to Dice Roller!", Toast.LENGTH_LONG).show()

                // Navigate back to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginButton.setOnClickListener {
            // Navigate to login screen (could be MainActivity or a dedicated LoginActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Add focus change listeners for better UX
        usernameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                usernameLayout.boxStrokeColor = getColor(R.color.primary_dark)
            } else {
                usernameLayout.boxStrokeColor = getColor(R.color.cyan_blue)
            }
        }

        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                emailLayout.boxStrokeColor = getColor(R.color.primary_dark)
            } else {
                emailLayout.boxStrokeColor = getColor(R.color.cyan_blue)
            }
        }

        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                passwordLayout.boxStrokeColor = getColor(R.color.primary_dark)
            } else {
                passwordLayout.boxStrokeColor = getColor(R.color.cyan_blue)
            }
        }
    }

    /**
     * Validate the registration information with improved error handling.
     * @param username The username entered by the user.
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     * @param usernameLayout The TextInputLayout for username to show errors.
     * @param emailLayout The TextInputLayout for email to show errors.
     * @param passwordLayout The TextInputLayout for password to show errors.
     * @return True if all fields are valid, false otherwise.
     */
    private fun validateRegistration(
        username: String,
        email: String,
        password: String,
        usernameLayout: TextInputLayout,
        emailLayout: TextInputLayout,
        passwordLayout: TextInputLayout
    ): Boolean {
        var isValid = true

        // Clear previous errors
        usernameLayout.error = null
        emailLayout.error = null
        passwordLayout.error = null

        if (username.isEmpty()) {
            usernameLayout.error = "Username is required"
            isValid = false
        } else if (username.length < 3) {
            usernameLayout.error = "Username must be at least 3 characters"
            isValid = false
        }

        if (email.isEmpty()) {
            emailLayout.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Please enter a valid email address"
            isValid = false
        }

        if (password.isEmpty()) {
            passwordLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordLayout.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }
}