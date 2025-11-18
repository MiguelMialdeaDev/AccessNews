package com.miguelmialdea.accessnews.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.miguelmialdea.accessnews.core.common.Constants
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SettingsContract.Effect.ShowMessage -> {
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
                        text = "Settings",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Accessibility Section
            Text(
                text = "Accessibility",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .semantics { heading() }
            )

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            // Text Scale
            Text(
                text = "Text Size: ${(state.textScale * 100).toInt()}%",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { heading() }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = state.textScale,
                onValueChange = { scale ->
                    viewModel.sendIntent(SettingsContract.Intent.SetTextScale(scale))
                },
                valueRange = Constants.MIN_TEXT_SCALE..Constants.MAX_TEXT_SCALE,
                steps = 11,
                modifier = Modifier.semantics {
                    contentDescription = "Adjust text size. Current value: ${(state.textScale * 100).toInt()} percent"
                }
            )

            Text(
                text = "Range: ${(Constants.MIN_TEXT_SCALE * 100).toInt()}% - ${(Constants.MAX_TEXT_SCALE * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // High Contrast
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "High Contrast",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Increase contrast for better visibility",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = state.highContrast,
                    onCheckedChange = { enabled ->
                        viewModel.sendIntent(SettingsContract.Intent.SetHighContrast(enabled))
                    },
                    modifier = Modifier.semantics {
                        contentDescription = if (state.highContrast) {
                            "High contrast enabled. Toggle to disable"
                        } else {
                            "High contrast disabled. Toggle to enable"
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reduce Motion
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Reduce Motion",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Minimize animations and transitions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = state.reduceMotion,
                    onCheckedChange = { enabled ->
                        viewModel.sendIntent(SettingsContract.Intent.SetReduceMotion(enabled))
                    },
                    modifier = Modifier.semantics {
                        contentDescription = if (state.reduceMotion) {
                            "Reduce motion enabled. Toggle to disable"
                        } else {
                            "Reduce motion disabled. Toggle to enable"
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // About Section
            Text(
                text = "About",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .semantics { heading() }
            )

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "AccessNews",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "An accessible RSS reader built with modern Android technologies",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
