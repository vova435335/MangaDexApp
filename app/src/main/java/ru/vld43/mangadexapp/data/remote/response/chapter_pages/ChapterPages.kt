package ru.vld43.mangadexapp.data.remote.response.chapter_pages

import com.google.gson.annotations.SerializedName

data class ChapterPages(
    @SerializedName("baseUrl") val baseUrl: String?,
    @SerializedName("chapter") val chapter: ChapterImages?,
    @SerializedName("result") val result: String?,
)
