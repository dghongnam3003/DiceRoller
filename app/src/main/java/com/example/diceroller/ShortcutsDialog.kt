package com.example.diceroller

import android.app.AlertDialog
import android.content.Context

/**
 * Simple shortcuts dialog that lists currently registered shortcuts.
 */
object ShortcutsDialog {
    fun show(context: Context) {
        val shortcuts = ShortcutsRegistry.getAll()
        val items = if (shortcuts.isEmpty()) {
            arrayOf("No shortcuts registered")
        } else {
            shortcuts.map { "${it.combo} — ${it.description}" }.toTypedArray()
        }

        AlertDialog.Builder(context)
            .setTitle("Keyboard Shortcuts")
            .setItems(items, null)
            .setPositiveButton("Close", null)
            .show()
    }
}
