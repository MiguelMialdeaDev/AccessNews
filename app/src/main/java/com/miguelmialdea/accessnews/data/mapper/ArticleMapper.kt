package com.miguelmialdea.accessnews.data.mapper

import com.miguelmialdea.accessnews.data.remote.dto.ArticleDto
import com.miguelmialdea.accessnews.database.ArticleEntity
import com.miguelmialdea.accessnews.domain.model.Article
import java.util.UUID

/**
 * Mapper for converting between Article models
 */

// Entity to Domain
fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        feedId = feedId,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        author = author,
        publishedAt = publishedAt,
        isBookmarked = isBookmarked == 1L,
        isRead = isRead == 1L
    )
}

// Domain to Entity
fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        feedId = feedId,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        author = author,
        publishedAt = publishedAt,
        isBookmarked = if (isBookmarked) 1L else 0L,
        isRead = if (isRead) 1L else 0L
    )
}

// DTO to Entity
fun ArticleDto.toEntity(feedId: String): ArticleEntity {
    return ArticleEntity(
        id = UUID.randomUUID().toString(),
        feedId = feedId,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        author = author,
        publishedAt = publishedAt,
        isBookmarked = 0L,
        isRead = 0L
    )
}

// DTO to Domain
fun ArticleDto.toDomain(feedId: String, id: String = UUID.randomUUID().toString()): Article {
    return Article(
        id = id,
        feedId = feedId,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        author = author,
        publishedAt = publishedAt,
        isBookmarked = false,
        isRead = false
    )
}
