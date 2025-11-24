package com.datn.bia.a.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object Idle: UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}