package com.example.diceroller

/**
 * Data class representing a user in the application.
 * 
 * @property username The user's username
 * @property email The user's email address
 * @property password The user's password (in a real app, this should be hashed)
 */
data class User(
    val username: String,
    val email: String,
    val password: String
) {
    /**
     * Validate the user information.
     * 
     * @return Pair<Boolean, String> where Boolean indicates if validation passed,
     *         and String contains error message if validation failed.
     */
    fun validate(): Pair<Boolean, String> {
        if (username.isBlank()) {
            return Pair(false, "Username cannot be empty")
        }
        
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Pair(false, "Please enter a valid email address")
        }
        
        if (password.isBlank() || password.length < 6) {
            return Pair(false, "Password must be at least 6 characters")
        }
        
        return Pair(true, "Validation successful")
    }
}

/**
 * Simple in-memory user database for demonstration purposes.
 * In a real app, this would be replaced with a proper database or backend service.
 */
object UserDatabase {
    private val users = mutableListOf<User>()
    
    /**
     * Register a new user.
     * 
     * @param user The user to register
     * @return Pair<Boolean, String> where Boolean indicates if registration was successful,
     *         and String contains error message if registration failed.
     */
    fun registerUser(user: User): Pair<Boolean, String> {
        // Check if username or email already exists
        val usernameExists = users.any { it.username == user.username }
        val emailExists = users.any { it.email == user.email }
        
        if (usernameExists) {
            return Pair(false, "Username already exists")
        }
        
        if (emailExists) {
            return Pair(false, "Email already registered")
        }
        
        // Validate user
        val (isValid, message) = user.validate()
        if (!isValid) {
            return Pair(false, message)
        }
        
        // Add user to database
        users.add(user)
        return Pair(true, "Registration successful")
    }
    
    /**
     * Login a user.
     * 
     * @param username The username
     * @param password The password
     * @return Pair<Boolean, String> where Boolean indicates if login was successful,
     *         and String contains error message if login failed.
     */
    fun loginUser(username: String, password: String): Pair<Boolean, String> {
        val user = users.find { it.username == username && it.password == password }
        return if (user != null) {
            Pair(true, "Login successful")
        } else {
            Pair(false, "Invalid username or password")
        }
    }
    
    /**
     * Get all registered users (for debugging purposes).
     * 
     * @return List of all registered users
     */
    fun getAllUsers(): List<User> {
        return users.toList()
    }
    
    /**
     * Clear all users (for testing purposes).
     */
    fun clearAllUsers() {
        users.clear()
    }
}
