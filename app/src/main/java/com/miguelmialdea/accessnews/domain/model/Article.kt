package com.miguelmialdea.accessnews.domain.model

/**
 * Domain model representing a news article
 */
data class Article(
    val id: String,
    val feedId: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val imageUrl: String? = null,
    val author: String? = null,
    val publishedAt: Long,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false
)
