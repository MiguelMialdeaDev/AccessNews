package com.miguelmialdea.accessnews.presentation.articles

import androidx.lifecycle.viewModelScope
import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.core.mvi.BaseViewModel
import com.miguelmialdea.accessnews.domain.repository.ArticleRepository
import com.miguelmialdea.accessnews.domain.repository.FeedRepository
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val articleRepository: ArticleRepository,
    private val feedRepository: FeedRepository
) : BaseViewModel<ArticlesContract.State, ArticlesContract.Intent, ArticlesContract.Effect>(
    initialState = ArticlesContract.State()
) {

    override fun handleIntent(intent: ArticlesContract.Intent) {
        when (intent) {
            is ArticlesContract.Intent.LoadArticles -> loadArticles(intent.feedId)
            is ArticlesContract.Intent.RefreshArticles -> refreshArticles()
            is ArticlesContract.Intent.ToggleBookmark -> toggleBookmark(intent.articleId)
            is ArticlesContract.Intent.MarkAsRead -> markAsRead(intent.articleId)
        }
    }

    private fun loadArticles(feedId: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            // Load feed title
            when (val feedResult = feedRepository.getFeedById(feedId)) {
                is Resource.Success -> {
                    setState { copy(feedTitle = feedResult.data.title) }
                }
                else -> {}
            }

            // Load articles
            articleRepository.getArticlesByFeed(feedId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        setState { copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        setState { copy(isLoading = false, articles = resource.data, error = null) }
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = resource.message) }
                        setEffect(ArticlesContract.Effect.ShowError(resource.message))
                    }
                }
            }
        }
    }

    private fun refreshArticles() {
        viewModelScope.launch {
            when (val result = articleRepository.refreshArticles()) {
                is Resource.Error -> {
                    setEffect(ArticlesContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }

    private fun toggleBookmark(articleId: String) {
        viewModelScope.launch {
            when (val result = articleRepository.toggleBookmark(articleId)) {
                is Resource.Success -> {
                    setEffect(ArticlesContract.Effect.ShowSuccess("Bookmark toggled"))
                }
                is Resource.Error -> {
                    setEffect(ArticlesContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }

    private fun markAsRead(articleId: String) {
        viewModelScope.launch {
            articleRepository.markAsRead(articleId, true)
        }
    }
}
