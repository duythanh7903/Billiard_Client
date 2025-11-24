package com.datn.bia.a.data.network.factory

import androidx.annotation.Keep

@Keep
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val message: String? = null) :
        ResultWrapper<Nothing>()

    data object NetworkError : ResultWrapper<Nothing>()
}