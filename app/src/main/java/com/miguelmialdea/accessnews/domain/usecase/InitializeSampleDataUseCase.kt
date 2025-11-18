package com.miguelmialdea.accessnews.domain.usecase

import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.domain.repository.ArticleRepository
import com.miguelmialdea.accessnews.domain.repository.FeedRepository
import kotlinx.coroutines.flow.first

/**
 * Use case for initializing the app with sample data
 */
class InitializeSampleDataUseCase(
    private val feedRepository: FeedRepository,
    private val articleRepository: ArticleRepository,
    private val getSampleFeedsUseCase: GetSampleFeedsUseCase,
    private val getSampleArticlesUseCase: GetSampleArticlesUseCase
) {

    suspend operator fun invoke(): Resource<Unit> {
        return try {
            // Check if we already have data
            val existingFeeds = feedRepository.getSubscribedFeeds().first()
            if (existingFeeds is Resource.Success && existingFeeds.data.isNotEmpty()) {
                // Already initialized
                return Resource.Success(Unit)
            }

            // Add sample feeds
            val sampleFeeds = getSampleFeedsUseCase()
            sampleFeeds.forEach { feed ->
                feedRepository.subscribeFeed(feed)
            }

            // Add sample articles for each feed
            sampleFeeds.forEach { feed ->
                val articles = getSampleArticlesUseCase(feed.id)
                articles.forEach { article ->
                    articleRepository.addArticle(article)
                }
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to initialize sample data", e)
        }
    }
}
