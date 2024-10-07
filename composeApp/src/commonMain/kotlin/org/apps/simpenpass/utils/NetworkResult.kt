package org.apps.simpenpass.utils

sealed class NetworkResult<T> {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val error: String) : NetworkResult<T>()
}
