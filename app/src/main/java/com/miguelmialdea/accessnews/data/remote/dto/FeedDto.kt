package com.miguelmialdea.accessnews.data.remote.dto

/**
 * Data Transfer Object for Feed from remote source
 */
data class FeedDto(
    val url: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val articles: List<ArticleDto>
)
