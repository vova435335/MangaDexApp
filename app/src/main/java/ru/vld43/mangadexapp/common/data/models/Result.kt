package ru.vld43.mangadexapp.common.data.models

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: String? = null) : Result<T>()
}
