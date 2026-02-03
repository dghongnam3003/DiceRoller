package com.example.diceroller

import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Simple shortcuts registry for registering keyboard shortcuts and invoking handlers.
 * This is intentionally lightweight and dependency-free to work with physical keyboards
 * attached to Android devices or emulators.
 */
object ShortcutsRegistry {
    private val TAG = "ShortcutsRegistry"

    data class Shortcut(
        val combo: String,
        val description: String,
        val handler: () -> Unit,
        val allowInInputs: Boolean = false
    )

    // Thread-safe list to allow registration/unregistration from different threads
    private val shortcuts = CopyOnWriteArrayList<Shortcut>()

    fun register(shortcut: Shortcut) {
        // Avoid duplicates (same combo and description)
        if (shortcuts.any { it.combo.equals(shortcut.combo, true) && it.description == shortcut.description }) {
            Log.d(TAG, "Shortcut already registered: ${shortcut.combo}")
            return
        }
        shortcuts.add(shortcut)
        Log.d(TAG, "Registered shortcut: ${shortcut.combo} -> ${shortcut.description}")
    }

    fun unregister(shortcut: Shortcut) {
        shortcuts.remove(shortcut)
        Log.d(TAG, "Unregistered shortcut: ${shortcut.combo}")
    }

    fun getAll(): List<Shortcut> = shortcuts.toList()

    /**
     * Attempt to handle the given character input. Returns true if a shortcut matched and was handled.
     */
    fun handleCharInput(ch: Char, isInInput: Boolean = false): Boolean {
        val key = ch.lowercaseChar()
        // Try to find a matching shortcut by comparing the first character of combo
        // or the entire combo if it's a string like "r" or "/".
        val strKey = key.toString()
        val match = shortcuts.firstOrNull {
            it.combo.equals(strKey, true) && (it.allowInInputs || !isInInput)
        }
        return if (match != null) {
            try {
                match.handler.invoke()
            } catch (e: Exception) {
                Log.e(TAG, "Error while executing shortcut handler for ${match.combo}: ${e.message}")
            }
            true
        } else {
            false
        }
    }
}
