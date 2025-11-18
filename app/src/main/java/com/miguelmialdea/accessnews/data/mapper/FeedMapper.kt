package com.miguelmialdea.accessnews.data.mapper

import com.miguelmialdea.accessnews.data.remote.dto.FeedDto
import com.miguelmialdea.accessnews.database.FeedEntity
import com.miguelmialdea.accessnews.domain.model.Feed
import com.miguelmialdea.accessnews.domain.model.FeedCategory
import java.util.UUID

/**
 * Mapper for converting between Feed models
 */

// Entity to Domain
fun FeedEntity.toDomain(): Feed {
    return Feed(
        id = id,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        category = FeedCategory.valueOf(category),
        isSubscribed = isSubscribed == 1L,
        lastUpdated = lastUpdated
    )
}

// Domain to Entity
fun Feed.toEntity(): FeedEntity {
    return FeedEntity(
        id = id,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        category = category.name,
        isSubscribed = if (isSubscribed) 1L else 0L,
        lastUpdated = lastUpdated
    )
}

// DTO to Entity
fun FeedDto.toEntity(category: FeedCategory = FeedCategory.GENERAL, isSubscribed: Boolean = false): FeedEntity {
    return FeedEntity(
        id = UUID.randomUUID().toString(),
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        category = category.name,
        isSubscribed = if (isSubscribed) 1L else 0L,
        lastUpdated = System.currentTimeMillis()
    )
}

// DTO to Domain
fun FeedDto.toDomain(id: String = UUID.randomUUID().toString(), category: FeedCategory = FeedCategory.GENERAL): Feed {
    return Feed(
        id = id,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        category = category,
        isSubscribed = false,
        lastUpdated = System.currentTimeMillis()
    )
}
