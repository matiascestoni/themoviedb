package com.themoviedb.common

sealed class Response<T> {
    class Success<T>(val result: T, val isOffline: Boolean = false) : Response<T>()
    class Error<T>(val message: String) : Response<T>()
}
