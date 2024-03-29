package ru.vld43.mangadexapp.data.network.response.manga

import com.google.gson.annotations.SerializedName

data class MangaList(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val mangaList: List<Manga>,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("total") val total: Int?
)
