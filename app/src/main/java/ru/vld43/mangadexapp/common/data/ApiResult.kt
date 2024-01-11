package ru.vld43.mangadexapp.common.data

sealed interface ApiResult<T>

class ApiSuccess<T>(val data: T) : ApiResult<T>

class ApiError<T>(val code: Int?, val message: String?) : ApiResult<T>

class ApiException<T>(val e: Throwable) : ApiResult<T>
