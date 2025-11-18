package com.miguelmialdea.accessnews.presentation.feeds

import com.miguelmialdea.accessnews.core.mvi.ViewEffect
import com.miguelmialdea.accessnews.core.mvi.ViewIntent
import com.miguelmialdea.accessnews.core.mvi.ViewState
import com.miguelmialdea.accessnews.domain.model.Feed

/**
 * MVI Contract for Feeds screen
 */
object FeedsContract {

    /**
     * View State
     */
    data class State(
        val isLoading: Boolean = false,
        val feeds: List<Feed> = emptyList(),
        val error: String? = null
    ) : ViewState

    /**
     * View Intents (User Actions)
     */
    sealed class Intent : ViewIntent {
        data object LoadFeeds : Intent()
        data object RefreshFeeds : Intent()
        data class SubscribeFeed(val feed: Feed) : Intent()
        data class UnsubscribeFeed(val feedId: String) : Intent()
        data class SearchFeeds(val query: String) : Intent()
    }

    /**
     * View Effects (One-time events)
     */
    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
        data class NavigateToArticles(val feedId: String) : Effect()
    }
}
