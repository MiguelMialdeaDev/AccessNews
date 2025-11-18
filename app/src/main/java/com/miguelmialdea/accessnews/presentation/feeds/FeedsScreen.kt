package com.miguelmialdea.accessnews.presentation.feeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.miguelmialdea.accessnews.core.common.toFormattedDate
import com.miguelmialdea.accessnews.core.designsystem.EmptyState
import com.miguelmialdea.accessnews.core.designsystem.LoadingIndicator
import com.miguelmialdea.accessnews.domain.model.Feed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

/**
 * Feeds screen - Shows list of subscribed RSS feeds
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedsScreen(
    onFeedClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: FeedsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect effects
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is FeedsContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is FeedsContract.Effect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is FeedsContract.Effect.NavigateToArticles -> {
                    onFeedClick(effect.feedId)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Feeds",
                        modifier = Modifier.semantics { heading() }
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier.semantics {
                            contentDescription = "Open settings"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add feed dialog */ },
                modifier = Modifier.semantics {
                    contentDescription = "Add new feed"
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add feed"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        FeedsContent(
            state = state,
            onFeedClick = onFeedClick,
            onRefresh = { viewModel.sendIntent(FeedsContract.Intent.RefreshFeeds) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun FeedsContent(
    state: FeedsContract.State,
    onFeedClick: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading && state.feeds.isEmpty() -> {
            LoadingIndicator(description = "Loading feeds")
        }
        state.feeds.isEmpty() -> {
            EmptyState(
                message = "No feeds subscribed yet.\nTap the + button to add your first feed!",
                icon = Icons.Default.RssFeed
            )
        }
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.feeds,
                    key = { it.id }
                ) { feed ->
                    FeedItem(
                        feed = feed,
                        onClick = { onFeedClick(feed.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedItem(
    feed: Feed,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) {
                contentDescription = "Feed: ${feed.title}. ${feed.description}. Last updated: ${feed.lastUpdated.toFormattedDate()}"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.RssFeed,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = feed.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.semantics { heading() }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = feed.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Last updated: ${feed.lastUpdated.toFormattedDate()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
