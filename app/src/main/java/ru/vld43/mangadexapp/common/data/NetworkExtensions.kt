package ru.vld43.mangadexapp.common.data

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> handleApi(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiSuccess(body)
        } else {
            ApiError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        ApiError(code = e.code(), message = e.message)
    } catch (e: IOException) {
        ApiError(code = null, message = "No internet connection")
    } catch (e: Throwable) {
        ApiException(e)
    }
}

suspend fun <T, R> ApiResult<T>.map(
    execute: suspend (data: T) -> R
): ApiResult<R> {
    return when (this) {
        is ApiSuccess -> ApiSuccess(execute(data))
        is ApiError -> ApiError(code = code, message = message)
        is ApiException -> ApiException(e)
    }
}
