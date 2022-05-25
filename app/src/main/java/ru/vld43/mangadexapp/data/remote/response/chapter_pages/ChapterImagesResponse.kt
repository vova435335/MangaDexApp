package ru.vld43.mangadexapp.data.remote.response.chapter_pages

import com.google.gson.annotations.SerializedName

data class ChapterImagesResponse(
    @SerializedName("data") val imageNames: List<String>?,
    @SerializedName("hash") val hash: String?
)
