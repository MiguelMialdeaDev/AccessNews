package com.miguelmialdea.accessnews.core.common

/**
 * A generic wrapper class around data that represents the state of a resource.
 * Used for handling loading, success, and error states.
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()

    /**
     * Check if this resource is in a loading state
     */
    fun isLoading(): Boolean = this is Loading

    /**
     * Check if this resource is in a success state
     */
    fun isSuccess(): Boolean = this is Success

    /**
     * Check if this resource is in an error state
     */
    fun isError(): Boolean = this is Error

    /**
     * Get the data if this resource is successful, null otherwise
     */
    fun getOrNull(): T? = if (this is Success) data else null
}
