package com.miguelmialdea.accessnews.presentation.feeds

import androidx.lifecycle.viewModelScope
import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.core.mvi.BaseViewModel
import com.miguelmialdea.accessnews.domain.repository.FeedRepository
import com.miguelmialdea.accessnews.domain.usecase.InitializeSampleDataUseCase
import kotlinx.coroutines.launch

/**
 * ViewModel for Feeds screen using MVI pattern
 */
class FeedsViewModel(
    private val feedRepository: FeedRepository,
    private val initializeSampleDataUseCase: InitializeSampleDataUseCase
) : BaseViewModel<FeedsContract.State, FeedsContract.Intent, FeedsContract.Effect>(
    initialState = FeedsContract.State()
) {

    init {
        initializeSampleData()
        loadFeeds()
    }

    private fun initializeSampleData() {
        viewModelScope.launch {
            initializeSampleDataUseCase()
        }
    }

    override fun handleIntent(intent: FeedsContract.Intent) {
        when (intent) {
            is FeedsContract.Intent.LoadFeeds -> loadFeeds()
            is FeedsContract.Intent.RefreshFeeds -> refreshFeeds()
            is FeedsContract.Intent.SubscribeFeed -> subscribeFeed(intent.feed)
            is FeedsContract.Intent.UnsubscribeFeed -> unsubscribeFeed(intent.feedId)
            is FeedsContract.Intent.SearchFeeds -> searchFeeds(intent.query)
        }
    }

    private fun loadFeeds() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            feedRepository.getSubscribedFeeds().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        setState { copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        setState { copy(isLoading = false, feeds = resource.data, error = null) }
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = resource.message) }
                        setEffect(FeedsContract.Effect.ShowError(resource.message))
                    }
                }
            }
        }
    }

    private fun refreshFeeds() {
        loadFeeds()
    }

    private fun subscribeFeed(feed: com.miguelmialdea.accessnews.domain.model.Feed) {
        viewModelScope.launch {
            when (val result = feedRepository.subscribeFeed(feed)) {
                is Resource.Success -> {
                    setEffect(FeedsContract.Effect.ShowSuccess("Subscribed to ${feed.title}"))
                    loadFeeds()
                }
                is Resource.Error -> {
                    setEffect(FeedsContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }

    private fun unsubscribeFeed(feedId: String) {
        viewModelScope.launch {
            when (val result = feedRepository.unsubscribeFeed(feedId)) {
                is Resource.Success -> {
                    setEffect(FeedsContract.Effect.ShowSuccess("Unsubscribed from feed"))
                    loadFeeds()
                }
                is Resource.Error -> {
                    setEffect(FeedsContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }

    private fun searchFeeds(query: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val result = feedRepository.searchFeeds(query)) {
                is Resource.Success -> {
                    setState { copy(isLoading = false, feeds = result.data, error = null) }
                }
                is Resource.Error -> {
                    setState { copy(isLoading = false, error = result.message) }
                    setEffect(FeedsContract.Effect.ShowError(result.message))
                }
                else -> {}
            }
        }
    }
}
