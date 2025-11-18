package com.miguelmialdea.accessnews.core.accessibility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.miguelmialdea.accessnews.core.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES_NAME
)

/**
 * Manager for accessibility preferences using DataStore
 */
class AccessibilityPreferences(private val context: Context) {

    private val TEXT_SCALE_KEY = floatPreferencesKey(Constants.KEY_TEXT_SCALE)
    private val HIGH_CONTRAST_KEY = booleanPreferencesKey(Constants.KEY_HIGH_CONTRAST)
    private val REDUCE_MOTION_KEY = booleanPreferencesKey(Constants.KEY_REDUCE_MOTION)

    /**
     * Get text scale preference
     */
    val textScale: Flow<Float> = context.dataStore.data.map { preferences ->
        preferences[TEXT_SCALE_KEY] ?: Constants.DEFAULT_TEXT_SCALE
    }

    /**
     * Get high contrast preference
     */
    val isHighContrast: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[HIGH_CONTRAST_KEY] ?: false
    }

    /**
     * Get reduce motion preference
     */
    val reduceMotion: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[REDUCE_MOTION_KEY] ?: false
    }

    /**
     * Update text scale
     */
    suspend fun setTextScale(scale: Float) {
        context.dataStore.edit { preferences ->
            preferences[TEXT_SCALE_KEY] = scale.coerceIn(
                Constants.MIN_TEXT_SCALE,
                Constants.MAX_TEXT_SCALE
            )
        }
    }

    /**
     * Toggle high contrast mode
     */
    suspend fun setHighContrast(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_CONTRAST_KEY] = enabled
        }
    }

    /**
     * Toggle reduce motion
     */
    suspend fun setReduceMotion(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REDUCE_MOTION_KEY] = enabled
        }
    }
}
