package com.miguelmialdea.accessnews.presentation.reader

import androidx.lifecycle.viewModelScope
import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.core.mvi.BaseViewModel
import com.miguelmialdea.accessnews.domain.repository.ArticleRepository
import kotlinx.coroutines.launch

class ReaderViewModel(
    private val articleRepository: ArticleRepository
) : BaseViewModel<ReaderContract.State, ReaderContract.Intent, ReaderContract.Effect>(
    initialState = ReaderContract.State()
) {

    override fun handleIntent(intent: ReaderContract.Intent) {
        when (intent) {
            is ReaderContract.Intent.LoadArticle -> loadArticle(intent.articleId)
            is ReaderContract.Intent.ToggleBookmark -> toggleBookmark()
        }
    }

    private fun loadArticle(articleId: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            when (val result = articleRepository.getArticleById(articleId)) {
                is Resource.Success -> {
                    setState { copy(isLoading = false, article = result.data, error = null) }
                    articleRepository.markAsRead(articleId, true)
                }
                is Resource.Error -> {
                    setState { copy(isLoading = false, error = result.message) }
                    setEffect(ReaderContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }

    private fun toggleBookmark() {
        val articleId = currentState.article?.id ?: return
        viewModelScope.launch {
            when (val result = articleRepository.toggleBookmark(articleId)) {
                is Resource.Success -> {
                    val message = if (currentState.article?.isBookmarked == true) {
                        "Removed from bookmarks"
                    } else {
                        "Added to bookmarks"
                    }
                    setEffect(ReaderContract.Effect.ShowSuccess(message))

                    // Reload article to get updated bookmark status
                    loadArticle(articleId)
                }
                is Resource.Error -> {
                    setEffect(ReaderContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }
}
