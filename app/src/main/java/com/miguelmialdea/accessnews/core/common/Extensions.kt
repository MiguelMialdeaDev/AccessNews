package com.miguelmialdea.accessnews.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Extension functions for common operations
 */

/**
 * Convert a Flow to a Flow of Resource
 */
fun <T> Flow<T>.asResource(): Flow<Resource<T>> = this
    .map<T, Resource<T>> { Resource.Success(it) }
    .onStart { emit(Resource.Loading) }
    .catch { emit(Resource.Error(it.message ?: "Unknown error", it)) }

/**
 * Format timestamp to readable date
 */
fun Long.toFormattedDate(): String {
    val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}

/**
 * Format timestamp to readable time
 */
fun Long.toFormattedTime(): String {
    val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}

/**
 * Truncate string to max length
 */
fun String.truncate(maxLength: Int): String {
    return if (this.length > maxLength) {
        "${this.substring(0, maxLength)}..."
    } else {
        this
    }
}
