package com.miguelmialdea.accessnews.presentation.articles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.miguelmialdea.accessnews.core.common.toFormattedDate
import com.miguelmialdea.accessnews.core.common.truncate
import com.miguelmialdea.accessnews.core.designsystem.EmptyState
import com.miguelmialdea.accessnews.core.designsystem.LoadingIndicator
import com.miguelmialdea.accessnews.domain.model.Article
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    feedId: String,
    onArticleClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: ArticlesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(feedId) {
        viewModel.sendIntent(ArticlesContract.Intent.LoadArticles(feedId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ArticlesContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is ArticlesContract.Effect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.feedTitle.ifEmpty { "Articles" },
                        modifier = Modifier.semantics { heading() }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Go back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            state.isLoading && state.articles.isEmpty() -> {
                LoadingIndicator(description = "Loading articles")
            }
            state.articles.isEmpty() -> {
                EmptyState(
                    message = "No articles available.\nPull to refresh!",
                    icon = Icons.Default.Article
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.articles,
                        key = { it.id }
                    ) { article ->
                        ArticleItem(
                            article = article,
                            onClick = {
                                viewModel.sendIntent(ArticlesContract.Intent.MarkAsRead(article.id))
                                onArticleClick(article.id)
                            },
                            onBookmarkClick = {
                                viewModel.sendIntent(ArticlesContract.Intent.ToggleBookmark(article.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) {
                contentDescription = "Article: ${article.title}. Published on ${article.publishedAt.toFormattedDate()}"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.semantics { heading() }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = article.description.truncate(150),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        if (article.author != null) {
                            Text(
                                text = article.author,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(" • ")
                        }
                        Text(
                            text = article.publishedAt.toFormattedDate(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (article.isBookmarked) "Remove bookmark" else "Add bookmark",
                        tint = if (article.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
