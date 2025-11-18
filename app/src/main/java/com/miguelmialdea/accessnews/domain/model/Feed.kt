package com.miguelmialdea.accessnews.domain.model

/**
 * Domain model representing an RSS/Atom feed
 */
data class Feed(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String? = null,
    val category: FeedCategory = FeedCategory.GENERAL,
    val isSubscribed: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Categories for organizing feeds
 */
enum class FeedCategory {
    TECHNOLOGY,
    BUSINESS,
    SPORTS,
    ENTERTAINMENT,
    SCIENCE,
    HEALTH,
    GENERAL
}
