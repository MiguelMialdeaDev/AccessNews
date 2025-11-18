package com.miguelmialdea.accessnews.core.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.miguelmialdea.accessnews.core.accessibility.AccessibilityUtils

/**
 * Accessible button component with minimum touch target size
 */
@Composable
fun AccessibleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(
                minWidth = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE,
                minHeight = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE
            )
            .then(
                if (contentDescription != null) {
                    Modifier.semantics {
                        this.contentDescription = contentDescription
                        role = Role.Button
                    }
                } else Modifier
            ),
        enabled = enabled,
        contentPadding = PaddingValues(ButtonDefaults.ContentPadding.calculateLeftPadding(androidx.compose.ui.unit.LayoutDirection.Ltr))
    ) {
        content()
    }
}

/**
 * Accessible outlined button
 */
@Composable
fun AccessibleOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
    content: @Composable () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(
                minWidth = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE,
                minHeight = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE
            )
            .then(
                if (contentDescription != null) {
                    Modifier.semantics {
                        this.contentDescription = contentDescription
                        role = Role.Button
                    }
                } else Modifier
            ),
        enabled = enabled
    ) {
        content()
    }
}

/**
 * Accessible text button
 */
@Composable
fun AccessibleTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
    content: @Composable () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(
                minWidth = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE,
                minHeight = AccessibilityUtils.MIN_TOUCH_TARGET_SIZE
            )
            .then(
                if (contentDescription != null) {
                    Modifier.semantics {
                        this.contentDescription = contentDescription
                        role = Role.Button
                    }
                } else Modifier
            ),
        enabled = enabled
    ) {
        content()
    }
}
