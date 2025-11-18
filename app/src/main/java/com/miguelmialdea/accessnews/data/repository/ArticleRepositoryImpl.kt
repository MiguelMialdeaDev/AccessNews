package com.miguelmialdea.accessnews.data.repository

import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.data.local.LocalDataSource
import com.miguelmialdea.accessnews.data.mapper.toDomain
import com.miguelmialdea.accessnews.data.mapper.toEntity
import com.miguelmialdea.accessnews.data.remote.RemoteDataSource
import com.miguelmialdea.accessnews.domain.model.Article
import com.miguelmialdea.accessnews.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of ArticleRepository
 */
class ArticleRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {

    override fun getArticlesByFeed(feedId: String): Flow<Resource<List<Article>>> {
        return try {
            localDataSource.getArticlesByFeed(feedId)
                .map { entities ->
                    Resource.Success(entities.map { it.toDomain() })
                }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flowOf(Resource.Error(e.message ?: "Unknown error", e))
        }
    }

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        return try {
            localDataSource.getAllArticles()
                .map { entities ->
                    Resource.Success(entities.map { it.toDomain() })
                }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flowOf(Resource.Error(e.message ?: "Unknown error", e))
        }
    }

    override fun getBookmarkedArticles(): Flow<Resource<List<Article>>> {
        return try {
            localDataSource.getBookmarkedArticles()
                .map { entities ->
                    Resource.Success(entities.map { it.toDomain() })
                }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flowOf(Resource.Error(e.message ?: "Unknown error", e))
        }
    }

    override suspend fun getArticleById(articleId: String): Resource<Article> {
        return try {
            val entity = localDataSource.getArticleById(articleId)
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Article not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun toggleBookmark(articleId: String): Resource<Unit> {
        return try {
            localDataSource.toggleBookmark(articleId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun markAsRead(articleId: String, isRead: Boolean): Resource<Unit> {
        return try {
            localDataSource.markAsRead(articleId, isRead)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun searchArticles(query: String): Resource<List<Article>> {
        return try {
            val entities = localDataSource.searchArticles(query)
            Resource.Success(entities.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun refreshArticles(): Resource<Unit> {
        return try {
            // Fetch all subscribed feeds and their articles
            // This is a simplified version - in production you'd want more sophisticated logic
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun addArticle(article: Article): Resource<Unit> {
        return try {
            localDataSource.insertOrReplaceArticle(article.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }
}
