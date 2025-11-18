package com.miguelmialdea.accessnews.presentation.navigation

/**
 * Sealed class representing all screens in the app
 */
sealed class Screen(val route: String) {
    data object Feeds : Screen("feeds")
    data object Articles : Screen("articles/{feedId}") {
        fun createRoute(feedId: String) = "articles/$feedId"
    }
    data object Reader : Screen("reader/{articleId}") {
        fun createRoute(articleId: String) = "reader/$articleId"
    }
    data object Settings : Screen("settings")
}
