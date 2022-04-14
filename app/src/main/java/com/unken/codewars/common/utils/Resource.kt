package com.unken.codewars.common.utils

sealed class Resource<T>(val data: T? = null, val message: UIText? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: UIText?, data: T? = null): Resource<T>(data, message)
}
