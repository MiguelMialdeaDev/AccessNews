package com.miguelmialdea.accessnews.presentation.settings

import androidx.lifecycle.viewModelScope
import com.miguelmialdea.accessnews.core.accessibility.AccessibilityPreferences
import com.miguelmialdea.accessnews.core.mvi.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val accessibilityPreferences: AccessibilityPreferences
) : BaseViewModel<SettingsContract.State, SettingsContract.Intent, SettingsContract.Effect>(
    initialState = SettingsContract.State()
) {

    init {
        loadSettings()
    }

    override fun handleIntent(intent: SettingsContract.Intent) {
        when (intent) {
            is SettingsContract.Intent.LoadSettings -> loadSettings()
            is SettingsContract.Intent.SetTextScale -> setTextScale(intent.scale)
            is SettingsContract.Intent.SetHighContrast -> setHighContrast(intent.enabled)
            is SettingsContract.Intent.SetReduceMotion -> setReduceMotion(intent.enabled)
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            accessibilityPreferences.textScale.collect { scale ->
                setState { copy(textScale = scale) }
            }
        }

        viewModelScope.launch {
            accessibilityPreferences.isHighContrast.collect { enabled ->
                setState { copy(highContrast = enabled) }
            }
        }

        viewModelScope.launch {
            accessibilityPreferences.reduceMotion.collect { enabled ->
                setState { copy(reduceMotion = enabled) }
            }
        }
    }

    private fun setTextScale(scale: Float) {
        viewModelScope.launch {
            accessibilityPreferences.setTextScale(scale)
            setEffect(SettingsContract.Effect.ShowMessage("Text scale updated"))
        }
    }

    private fun setHighContrast(enabled: Boolean) {
        viewModelScope.launch {
            accessibilityPreferences.setHighContrast(enabled)
            setEffect(SettingsContract.Effect.ShowMessage("High contrast ${if (enabled) "enabled" else "disabled"}"))
        }
    }

    private fun setReduceMotion(enabled: Boolean) {
        viewModelScope.launch {
            accessibilityPreferences.setReduceMotion(enabled)
            setEffect(SettingsContract.Effect.ShowMessage("Reduce motion ${if (enabled) "enabled" else "disabled"}"))
        }
    }
}
