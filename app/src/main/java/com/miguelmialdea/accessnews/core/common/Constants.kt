package com.miguelmialdea.accessnews.core.common

/**
 * Application-wide constants
 */
object Constants {
    // Database
    const val DATABASE_NAME = "access_news_database"

    // Network
    const val NETWORK_TIMEOUT = 30_000L
    const val CONNECT_TIMEOUT = 15_000L

    // Accessibility
    const val MIN_TOUCH_TARGET_SIZE_DP = 48
    const val DEFAULT_TEXT_SCALE = 1.0f
    const val MAX_TEXT_SCALE = 2.0f
    const val MIN_TEXT_SCALE = 0.8f

    // UI
    const val SHIMMER_DURATION = 1000
    const val DEBOUNCE_TIME = 300L

    // DataStore Keys
    const val PREFERENCES_NAME = "access_news_preferences"
    const val KEY_TEXT_SCALE = "text_scale"
    const val KEY_HIGH_CONTRAST = "high_contrast"
    const val KEY_REDUCE_MOTION = "reduce_motion"
    const val KEY_THEME_MODE = "theme_mode"
}
