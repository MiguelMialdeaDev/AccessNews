package com.miguelmialdea.accessnews.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.miguelmialdea.accessnews.database.AccessNewsDatabase
import com.miguelmialdea.accessnews.database.ArticleEntity
import com.miguelmialdea.accessnews.database.FeedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

/**
 * Local data source using SQLDelight
 */
class LocalDataSource(private val database: AccessNewsDatabase) {

    private val feedQueries = database.feedQueries
    private val articleQueries = database.articleQueries

    // Feed operations
    fun getSubscribedFeeds(): Flow<List<FeedEntity>> {
        return feedQueries.getSubscribedFeeds()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun getFeedById(feedId: String): FeedEntity? {
        return feedQueries.getFeedById(feedId).executeAsOneOrNull()
    }

    suspend fun getFeedByUrl(url: String): FeedEntity? {
        return feedQueries.getFeedByUrl(url).executeAsOneOrNull()
    }

    suspend fun insertOrReplaceFeed(feed: FeedEntity) {
        feedQueries.insertOrReplaceFeed(
            id = feed.id,
            title = feed.title,
            description = feed.description,
            url = feed.url,
            imageUrl = feed.imageUrl,
            category = feed.category,
            isSubscribed = feed.isSubscribed,
            lastUpdated = feed.lastUpdated
        )
    }

    suspend fun subscribeFeed(feedId: String) {
        feedQueries.subscribeFeed(feedId)
    }

    suspend fun unsubscribeFeed(feedId: String) {
        feedQueries.unsubscribeFeed(feedId)
    }

    suspend fun deleteFeed(feedId: String) {
        feedQueries.deleteFeed(feedId)
    }

    suspend fun searchFeeds(query: String): List<FeedEntity> {
        return feedQueries.searchFeeds(query, query).executeAsList()
    }

    // Article operations
    fun getAllArticles(): Flow<List<ArticleEntity>> {
        return articleQueries.getAllArticles()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getArticlesByFeed(feedId: String): Flow<List<ArticleEntity>> {
        return articleQueries.getArticlesByFeed(feedId)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getBookmarkedArticles(): Flow<List<ArticleEntity>> {
        return articleQueries.getBookmarkedArticles()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun getArticleById(articleId: String): ArticleEntity? {
        return articleQueries.getArticleById(articleId).executeAsOneOrNull()
    }

    suspend fun getArticleByUrl(url: String): ArticleEntity? {
        return articleQueries.getArticleByUrl(url).executeAsOneOrNull()
    }

    suspend fun insertOrReplaceArticle(article: ArticleEntity) {
        articleQueries.insertOrReplaceArticle(
            id = article.id,
            feedId = article.feedId,
            title = article.title,
            description = article.description,
            content = article.content,
            url = article.url,
            imageUrl = article.imageUrl,
            author = article.author,
            publishedAt = article.publishedAt,
            isBookmarked = article.isBookmarked,
            isRead = article.isRead
        )
    }

    suspend fun toggleBookmark(articleId: String) {
        articleQueries.toggleBookmark(articleId)
    }

    suspend fun markAsRead(articleId: String, isRead: Boolean) {
        articleQueries.markAsRead(if (isRead) 1L else 0L, articleId)
    }

    suspend fun deleteArticle(articleId: String) {
        articleQueries.deleteArticle(articleId)
    }

    suspend fun deleteArticlesByFeed(feedId: String) {
        articleQueries.deleteArticlesByFeed(feedId)
    }

    suspend fun searchArticles(query: String): List<ArticleEntity> {
        return articleQueries.searchArticles(query, query, query).executeAsList()
    }

    fun getUnreadCount(): Flow<Long> {
        return articleQueries.getUnreadCount()
            .asFlow()
            .mapToOne(Dispatchers.IO)
    }
}
