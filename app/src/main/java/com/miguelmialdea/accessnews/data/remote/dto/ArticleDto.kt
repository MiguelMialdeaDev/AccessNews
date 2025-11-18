package com.miguelmialdea.accessnews.data.remote.dto

/**
 * Data Transfer Object for Article from remote source
 */
data class ArticleDto(
    val feedUrl: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val imageUrl: String?,
    val author: String?,
    val publishedAt: Long
)
