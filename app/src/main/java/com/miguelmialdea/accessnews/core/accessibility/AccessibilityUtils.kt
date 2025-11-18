package com.miguelmialdea.accessnews.core.accessibility

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Accessibility utilities and constants
 */
object AccessibilityUtils {
    // Minimum touch target size for accessibility (WCAG 2.1)
    val MIN_TOUCH_TARGET_SIZE: Dp = 48.dp

    /**
     * Add content description for screen readers
     */
    fun Modifier.accessibleDescription(description: String): Modifier {
        return this.semantics {
            contentDescription = description
        }
    }
}

/**
 * Semantic roles for accessibility
 */
enum class AccessibilityRole {
    BUTTON,
    LINK,
    HEADING,
    IMAGE,
    LIST,
    LIST_ITEM
}

/**
 * Accessibility announcement for screen readers
 */
data class AccessibilityAnnouncement(
    val message: String,
    val isPolite: Boolean = true
)
