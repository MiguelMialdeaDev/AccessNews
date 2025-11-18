package com.miguelmialdea.accessnews.data.repository

import com.miguelmialdea.accessnews.core.common.Resource
import com.miguelmialdea.accessnews.data.local.LocalDataSource
import com.miguelmialdea.accessnews.data.mapper.toDomain
import com.miguelmialdea.accessnews.data.mapper.toEntity
import com.miguelmialdea.accessnews.data.remote.RemoteDataSource
import com.miguelmialdea.accessnews.domain.model.Feed
import com.miguelmialdea.accessnews.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of FeedRepository
 */
class FeedRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : FeedRepository {

    override fun getSubscribedFeeds(): Flow<Resource<List<Feed>>> {
        return try {
            localDataSource.getSubscribedFeeds()
                .map { entities ->
                    Resource.Success(entities.map { it.toDomain() })
                }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flowOf(Resource.Error(e.message ?: "Unknown error", e))
        }
    }

    override suspend fun getFeedById(feedId: String): Resource<Feed> {
        return try {
            val entity = localDataSource.getFeedById(feedId)
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Feed not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun subscribeFeed(feed: Feed): Resource<Unit> {
        return try {
            localDataSource.insertOrReplaceFeed(feed.copy(isSubscribed = true).toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun unsubscribeFeed(feedId: String): Resource<Unit> {
        return try {
            localDataSource.unsubscribeFeed(feedId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun searchFeeds(query: String): Resource<List<Feed>> {
        return try {
            val entities = localDataSource.searchFeeds(query)
            Resource.Success(entities.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun updateFeed(feed: Feed): Resource<Unit> {
        return try {
            localDataSource.insertOrReplaceFeed(feed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error", e)
        }
    }
}
