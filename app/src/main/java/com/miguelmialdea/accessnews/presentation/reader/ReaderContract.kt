package com.miguelmialdea.accessnews.presentation.reader

import com.miguelmialdea.accessnews.core.mvi.ViewEffect
import com.miguelmialdea.accessnews.core.mvi.ViewIntent
import com.miguelmialdea.accessnews.core.mvi.ViewState
import com.miguelmialdea.accessnews.domain.model.Article

object ReaderContract {

    data class State(
        val isLoading: Boolean = false,
        val article: Article? = null,
        val error: String? = null
    ) : ViewState

    sealed class Intent : ViewIntent {
        data class LoadArticle(val articleId: String) : Intent()
        data object ToggleBookmark : Intent()
    }

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}
