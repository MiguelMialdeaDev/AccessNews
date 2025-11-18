package com.miguelmialdea.accessnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.miguelmialdea.accessnews.core.accessibility.AccessibilityPreferences
import com.miguelmialdea.accessnews.presentation.navigation.NavGraph
import com.miguelmialdea.accessnews.ui.theme.AccessNewsTheme
import org.koin.android.ext.android.inject

/**
 * Main Activity for AccessNews
 * Entry point of the application
 */
class MainActivity : ComponentActivity() {

    private val accessibilityPreferences: AccessibilityPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Collect accessibility preferences
            val textScale by accessibilityPreferences.textScale.collectAsState(initial = 1.0f)
            val highContrast by accessibilityPreferences.isHighContrast.collectAsState(initial = false)

            // Apply theme with accessibility settings
            AccessNewsTheme(
                textScale = textScale,
                highContrast = highContrast
            ) {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}