package com.dangerfield.barbrasbook.api

/**
 * Used to feed response from repository layer to view as {Success || Loading || Error}
 * These states can carry any present data with it while letting the view know data fetching status
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null, var refreshing: Boolean = false) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
}