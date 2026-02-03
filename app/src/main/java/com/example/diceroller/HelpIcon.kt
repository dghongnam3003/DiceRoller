package com.example.diceroller

import android.content.Context
import android.util.AttributeSet
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat

/**
 * HelpIcon is a small, accessible help button that shows a tooltip on hover/focus
 * and displays a Toast on click (for touch devices). The help text can be set via
 * the custom XML attribute "app:helpText" or programmatically via setHelpText().
 */
class HelpIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.imageButtonStyle
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    private var helpText: CharSequence? = null

    init {
        // Apply a default help icon drawable and tint
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_help))
        background = null
        isFocusable = true
        isClickable = true
        val defaultDesc = context.getString(R.string.help_icon_description)
        contentDescription = defaultDesc

        // Read custom attribute if provided
        attrs?.let {
            val a = context.theme.obtainStyledAttributes(it, R.styleable.HelpIcon, 0, 0)
            try {
                val text = a.getString(R.styleable.HelpIcon_helpText)
                text?.let { setHelpText(it) }
            } finally {
                a.recycle()
            }
        }

        // Show the help text as a Toast on click (good for touch devices)
        setOnClickListener {
            helpText?.let { text ->
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                // Announce for accessibility services
                sendAccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            }
        }
    }

    fun setHelpText(text: CharSequence) {
        helpText = text
        // Show tooltip on hover/focus for supported platforms
        TooltipCompat.setTooltipText(this, helpText)
        // Also update contentDescription so screen readers can access the help text
        contentDescription = helpText
    }
}
