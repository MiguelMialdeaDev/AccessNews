package com.miguelmialdea.accessnews.data.remote

import com.miguelmialdea.accessnews.data.remote.dto.FeedDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Remote data source for fetching RSS/Atom feeds
 */
class RemoteDataSource(
    private val httpClient: HttpClient,
    private val rssParser: RssParser
) {

    /**
     * Fetch and parse feed from URL
     */
    suspend fun fetchFeed(feedUrl: String): FeedDto {
        val response = httpClient.get(feedUrl)
        val xmlContent = response.bodyAsText()
        return rssParser.parseFeed(xmlContent, feedUrl)
    }
}
