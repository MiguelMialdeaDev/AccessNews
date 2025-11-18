package com.miguelmialdea.accessnews.domain.repository

import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.domain.model.Feed
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing RSS/Atom feeds
 */
interface FeedRepository {
    /**
     * Get all subscribed feeds
     */
    fun getSubscribedFeeds(): Flow<Resource<List<Feed>>>

    /**
     * Get feed by ID
     */
    suspend fun getFeedById(feedId: String): Resource<Feed>

    /**
     * Subscribe to a feed
     */
    suspend fun subscribeFeed(feed: Feed): Resource<Unit>

    /**
     * Unsubscribe from a feed
     */
    suspend fun unsubscribeFeed(feedId: String): Resource<Unit>

    /**
     * Search for feeds by query
     */
    suspend fun searchFeeds(query: String): Resource<List<Feed>>

    /**
     * Update feed information
     */
    suspend fun updateFeed(feed: Feed): Resource<Unit>
}
