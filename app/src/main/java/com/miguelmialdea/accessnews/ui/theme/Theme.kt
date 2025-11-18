package com.miguelmialdea.accessnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

// Standard Color Schemes
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    error = Error,
    onError = OnError
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark
)

// High Contrast Color Schemes
private val HighContrastLightColorScheme = lightColorScheme(
    primary = HighContrastPrimary,
    onPrimary = HighContrastOnPrimary,
    background = HighContrastBackground,
    onBackground = HighContrastOnBackground,
    surface = HighContrastSurface,
    onSurface = HighContrastOnSurface,
    error = Error,
    onError = OnError
)

private val HighContrastDarkColorScheme = darkColorScheme(
    primary = HighContrastPrimaryDark,
    onPrimary = HighContrastOnPrimaryDark,
    background = HighContrastBackgroundDark,
    onBackground = HighContrastOnBackgroundDark,
    surface = HighContrastSurfaceDark,
    onSurface = HighContrastOnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark
)

/**
 * Local composition for text scale
 */
val LocalTextScale = compositionLocalOf { 1.0f }

@Composable
fun AccessNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = false,
    textScale: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrast && darkTheme -> HighContrastDarkColorScheme
        highContrast -> HighContrastLightColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Scale typography based on accessibility settings
    val scaledTypography = Typography.copy(
        displayLarge = Typography.displayLarge.copy(fontSize = (Typography.displayLarge.fontSize.value * textScale).sp),
        displayMedium = Typography.displayMedium.copy(fontSize = (Typography.displayMedium.fontSize.value * textScale).sp),
        displaySmall = Typography.displaySmall.copy(fontSize = (Typography.displaySmall.fontSize.value * textScale).sp),
        headlineLarge = Typography.headlineLarge.copy(fontSize = (Typography.headlineLarge.fontSize.value * textScale).sp),
        headlineMedium = Typography.headlineMedium.copy(fontSize = (Typography.headlineMedium.fontSize.value * textScale).sp),
        headlineSmall = Typography.headlineSmall.copy(fontSize = (Typography.headlineSmall.fontSize.value * textScale).sp),
        titleLarge = Typography.titleLarge.copy(fontSize = (Typography.titleLarge.fontSize.value * textScale).sp),
        titleMedium = Typography.titleMedium.copy(fontSize = (Typography.titleMedium.fontSize.value * textScale).sp),
        titleSmall = Typography.titleSmall.copy(fontSize = (Typography.titleSmall.fontSize.value * textScale).sp),
        bodyLarge = Typography.bodyLarge.copy(fontSize = (Typography.bodyLarge.fontSize.value * textScale).sp),
        bodyMedium = Typography.bodyMedium.copy(fontSize = (Typography.bodyMedium.fontSize.value * textScale).sp),
        bodySmall = Typography.bodySmall.copy(fontSize = (Typography.bodySmall.fontSize.value * textScale).sp),
        labelLarge = Typography.labelLarge.copy(fontSize = (Typography.labelLarge.fontSize.value * textScale).sp),
        labelMedium = Typography.labelMedium.copy(fontSize = (Typography.labelMedium.fontSize.value * textScale).sp),
        labelSmall = Typography.labelSmall.copy(fontSize = (Typography.labelSmall.fontSize.value * textScale).sp)
    )

    CompositionLocalProvider(LocalTextScale provides textScale) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = scaledTypography,
            content = content
        )
    }
}