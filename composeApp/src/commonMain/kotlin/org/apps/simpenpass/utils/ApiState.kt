package org.apps.simpenpass.utils

enum class ApiState {
    UNAUTHORIZED,
    ERROR_SERVER,
    SUCCESS,
    TOO_MANY_REQUEST,
    TIMEOUT,
    NO_INTERNET,
    UNKNOWN
}