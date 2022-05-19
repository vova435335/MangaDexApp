package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName

data class MangaByIdDto(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val manga: MangaDto?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("total") val total: Int?,
)