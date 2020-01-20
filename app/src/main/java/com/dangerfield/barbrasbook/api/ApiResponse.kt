package com.dangerfield.barbrasbook.api

/**
 * Used to wrap retrofit responses for network bound resource to delineate status
 */
sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Empty<T> : ApiResponse<T>(null)
    class Error<T>(data: T? = null, message: String) : ApiResponse<T>(data, message)
}