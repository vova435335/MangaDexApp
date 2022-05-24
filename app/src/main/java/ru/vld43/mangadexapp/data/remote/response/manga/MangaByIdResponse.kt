package ru.vld43.mangadexapp.data.remote.response.manga

import com.google.gson.annotations.SerializedName

data class MangaByIdResponse(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val manga: MangaResponse?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("total") val total: Int?,
)