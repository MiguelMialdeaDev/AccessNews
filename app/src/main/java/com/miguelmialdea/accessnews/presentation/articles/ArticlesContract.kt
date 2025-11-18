package com.miguelmialdea.accessnews.presentation.articles

import com.miguelmialdea.accessnews.core.mvi.ViewEffect
import com.miguelmialdea.accessnews.core.mvi.ViewIntent
import com.miguelmialdea.accessnews.core.mvi.ViewState
import com.miguelmialdea.accessnews.domain.model.Article

object ArticlesContract {

    data class State(
        val isLoading: Boolean = false,
        val articles: List<Article> = emptyList(),
        val error: String? = null,
        val feedTitle: String = ""
    ) : ViewState

    sealed class Intent : ViewIntent {
        data class LoadArticles(val feedId: String) : Intent()
        data object RefreshArticles : Intent()
        data class ToggleBookmark(val articleId: String) : Intent()
        data class MarkAsRead(val articleId: String) : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}
