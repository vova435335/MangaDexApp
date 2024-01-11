package ru.vld43.mangadexapp.data.network.response.chapters

import com.google.gson.annotations.SerializedName

data class Chapters(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val data: List<Chapter>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("total") val total: Int?,
)
