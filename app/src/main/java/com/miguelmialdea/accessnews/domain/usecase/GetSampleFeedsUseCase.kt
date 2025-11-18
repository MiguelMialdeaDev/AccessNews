package com.miguelmialdea.accessnews.domain.usecase

import com.miguelmialdea.accessnews.domain.model.Feed
import com.miguelmialdea.accessnews.domain.model.FeedCategory
import java.util.UUID

/**
 * Use case for getting sample feeds to populate the app
 */
class GetSampleFeedsUseCase {

    operator fun invoke(): List<Feed> {
        return listOf(
            Feed(
                id = UUID.randomUUID().toString(),
                title = "TechCrunch",
                description = "Latest technology news and information on startups",
                url = "https://techcrunch.com/feed/",
                imageUrl = null,
                category = FeedCategory.TECHNOLOGY,
                isSubscribed = true,
                lastUpdated = System.currentTimeMillis()
            ),
            Feed(
                id = UUID.randomUUID().toString(),
                title = "The Verge",
                description = "Technology, science, art, and culture news",
                url = "https://www.theverge.com/rss/index.xml",
                imageUrl = null,
                category = FeedCategory.TECHNOLOGY,
                isSubscribed = true,
                lastUpdated = System.currentTimeMillis()
            ),
            Feed(
                id = UUID.randomUUID().toString(),
                title = "Android Authority",
                description = "News, reviews, and how-tos for Android enthusiasts",
                url = "https://www.androidauthority.com/feed/",
                imageUrl = null,
                category = FeedCategory.TECHNOLOGY,
                isSubscribed = true,
                lastUpdated = System.currentTimeMillis()
            ),
            Feed(
                id = UUID.randomUUID().toString(),
                title = "BBC News",
                description = "Latest news from around the world",
                url = "http://feeds.bbci.co.uk/news/rss.xml",
                imageUrl = null,
                category = FeedCategory.GENERAL,
                isSubscribed = true,
                lastUpdated = System.currentTimeMillis()
            ),
            Feed(
                id = UUID.randomUUID().toString(),
                title = "Scientific American",
                description = "Science news, articles and features",
                url = "http://rss.sciam.com/ScientificAmerican-Global",
                imageUrl = null,
                category = FeedCategory.SCIENCE,
                isSubscribed = true,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }
}
