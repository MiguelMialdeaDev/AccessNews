package com.miguelmialdea.accessnews.presentation.settings

import com.miguelmialdea.accessnews.core.mvi.ViewEffect
import com.miguelmialdea.accessnews.core.mvi.ViewIntent
import com.miguelmialdea.accessnews.core.mvi.ViewState

object SettingsContract {

    data class State(
        val textScale: Float = 1.0f,
        val highContrast: Boolean = false,
        val reduceMotion: Boolean = false
    ) : ViewState

    sealed class Intent : ViewIntent {
        data object LoadSettings : Intent()
        data class SetTextScale(val scale: Float) : Intent()
        data class SetHighContrast(val enabled: Boolean) : Intent()
        data class SetReduceMotion(val enabled: Boolean) : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowMessage(val message: String) : Effect()
    }
}
