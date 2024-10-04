package org.apps.simpenpass.utils

data class NetworkResult<out T>(val state: ApiState, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(ApiState.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): NetworkResult<T> {
            return NetworkResult(ApiState.ERROR_SERVER, data, msg)
        }

        fun <T> unauthorized(msg: String, data: T?): NetworkResult<T> {
            return NetworkResult(ApiState.UNAUTHORIZED, data, msg)
        }

        fun <T> timeout(msg: String, data: T?): NetworkResult<T> {
            return NetworkResult(ApiState.TIMEOUT, data, msg)
        }

        fun <T> noInternet(msg: String, data: T?): NetworkResult<T> {
            return NetworkResult(ApiState.NO_INTERNET, data, msg)
        }

    }
}
