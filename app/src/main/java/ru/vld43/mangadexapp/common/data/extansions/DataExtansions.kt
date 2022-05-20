package ru.vld43.mangadexapp.common.data.extansions

import retrofit2.Response
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.common.data.models.Result.*

fun <T> Response<T>.toResult(): Result<T?> =
    when {
        isSuccessful -> Success(body())
        else -> Error()
    }

fun <T, R> Result<T>.map(
    ifSuccess: ((T) -> R)? = null,
    ifError: (() -> String)? = null
): Result<R?> = when (this) {
    is Success -> Success(ifSuccess?.invoke(data))
    is Error -> Error(ifError?.invoke())
}

