package com.miguelmialdea.accessnews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.miguelmialdea.accessnews.presentation.articles.ArticlesScreen
import com.miguelmialdea.accessnews.presentation.feeds.FeedsScreen
import com.miguelmialdea.accessnews.presentation.reader.ReaderScreen
import com.miguelmialdea.accessnews.presentation.settings.SettingsScreen

/**
 * Navigation graph for the app
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Feeds.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Feeds screen
        composable(Screen.Feeds.route) {
            FeedsScreen(
                onFeedClick = { feedId ->
                    navController.navigate(Screen.Articles.createRoute(feedId))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // Articles screen
        composable(
            route = Screen.Articles.route,
            arguments = listOf(
                navArgument("feedId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val feedId = backStackEntry.arguments?.getString("feedId") ?: return@composable
            ArticlesScreen(
                feedId = feedId,
                onArticleClick = { articleId ->
                    navController.navigate(Screen.Reader.createRoute(articleId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Reader screen
        composable(
            route = Screen.Reader.route,
            arguments = listOf(
                navArgument("articleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: return@composable
            ReaderScreen(
                articleId = articleId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Settings screen
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
