package com.miguelmialdea.accessnews.presentation.reader

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.miguelmialdea.accessnews.core.common.toFormattedDate
import com.miguelmialdea.accessnews.core.designsystem.LoadingIndicator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    articleId: String,
    onBackClick: () -> Unit,
    viewModel: ReaderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(articleId) {
        viewModel.sendIntent(ReaderContract.Intent.LoadArticle(articleId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ReaderContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is ReaderContract.Effect.ShowSuccess -> {
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
                        text = "Article",
                        modifier = Modifier.semantics { heading() }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Go back")
                    }
                },
                actions = {
                    state.article?.let { article ->
                        IconButton(
                            onClick = { viewModel.sendIntent(ReaderContract.Intent.ToggleBookmark) }
                        ) {
                            Icon(
                                imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (article.isBookmarked) "Remove bookmark" else "Add bookmark",
                                tint = if (article.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingIndicator(description = "Loading article")
            }
            state.article != null -> {
                val article = state.article!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {
                    // Title
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.semantics { heading() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Metadata
                    Row {
                        if (article.author != null) {
                            Text(
                                text = "By ${article.author}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(" • ")
                        }
                        Text(
                            text = article.publishedAt.toFormattedDate(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Content
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5f
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Source link
                    Text(
                        text = "Source: ${article.url}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(
                        text = "Article not found",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
