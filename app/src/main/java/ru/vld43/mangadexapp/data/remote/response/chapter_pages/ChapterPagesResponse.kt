package ru.vld43.mangadexapp.data.remote.response.chapter_pages

import com.google.gson.annotations.SerializedName

data class ChapterPagesResponse(
    @SerializedName("baseUrl") val baseUrl: String?,
    @SerializedName("chapter") val chapter: ChapterImagesResponse?,
    @SerializedName("result") val result: String?,
)
