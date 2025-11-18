package com.miguelmialdea.accessnews.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel implementing the MVI pattern.
 *
 * @param STATE The type of ViewState this ViewModel manages
 * @param INTENT The type of ViewIntent this ViewModel handles
 * @param EFFECT The type of ViewEffect this ViewModel can emit
 */
abstract class BaseViewModel<STATE : ViewState, INTENT : ViewIntent, EFFECT : ViewEffect>(
    initialState: STATE
) : ViewModel() {

    // State Flow - represents the current UI state
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    // Effect Channel - for one-time events
    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    // Intent Flow - for user actions
    private val _intent = MutableSharedFlow<INTENT>()
    private val intent: SharedFlow<INTENT> = _intent.asSharedFlow()

    init {
        subscribeToIntents()
    }

    /**
     * Subscribe to intents and handle them
     */
    private fun subscribeToIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    /**
     * Process user intents. Override this method to handle specific intents.
     */
    protected abstract fun handleIntent(intent: INTENT)

    /**
     * Update the current state
     */
    protected fun setState(reducer: STATE.() -> STATE) {
        _state.value = _state.value.reducer()
    }

    /**
     * Send a one-time effect
     */
    protected fun setEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    /**
     * Send an intent to be processed
     */
    fun sendIntent(intent: INTENT) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    /**
     * Get the current state value
     */
    protected val currentState: STATE
        get() = _state.value
}
