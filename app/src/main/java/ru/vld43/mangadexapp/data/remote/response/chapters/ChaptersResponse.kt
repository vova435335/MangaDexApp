package ru.vld43.mangadexapp.data.remote.response.chapters

import com.google.gson.annotations.SerializedName

data class ChaptersResponse(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val data: List<ChapterResponse>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("total") val total: Int?,
)
