package com.miguelmialdea.accessnews.domain.repository

import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing articles
 */
interface ArticleRepository {
    /**
     * Get articles from a specific feed
     */
    fun getArticlesByFeed(feedId: String): Flow<Resource<List<Article>>>

    /**
     * Get all articles from all subscribed feeds
     */
    fun getAllArticles(): Flow<Resource<List<Article>>>

    /**
     * Get bookmarked articles
     */
    fun getBookmarkedArticles(): Flow<Resource<List<Article>>>

    /**
     * Get article by ID
     */
    suspend fun getArticleById(articleId: String): Resource<Article>

    /**
     * Bookmark/unbookmark an article
     */
    suspend fun toggleBookmark(articleId: String): Resource<Unit>

    /**
     * Mark article as read/unread
     */
    suspend fun markAsRead(articleId: String, isRead: Boolean): Resource<Unit>

    /**
     * Search articles by query
     */
    suspend fun searchArticles(query: String): Resource<List<Article>>

    /**
     * Refresh articles from remote feeds
     */
    suspend fun refreshArticles(): Resource<Unit>

    /**
     * Add article to database
     */
    suspend fun addArticle(article: Article): Resource<Unit>
}
