package ru.vld43.mangadexapp.data.network.response.chapter_pages

import com.google.gson.annotations.SerializedName

data class ChapterImages(
    @SerializedName("data") val imageNames: List<String>?,
    @SerializedName("hash") val hash: String?
)
