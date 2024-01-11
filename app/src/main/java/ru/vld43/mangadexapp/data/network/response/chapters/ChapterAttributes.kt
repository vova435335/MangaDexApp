package ru.vld43.mangadexapp.data.network.response.chapters

import com.google.gson.annotations.SerializedName

data class ChapterAttributes(
    @SerializedName("title") val title: String?,
    @SerializedName("volume") val volume: String?,
    @SerializedName("chapter") val chapter: String?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("translatedLanguage") val translatedLanguage: String?,
    @SerializedName("uploader") val uploader: String?,
    @SerializedName("externalUrl") val externalUrl: String?,
    @SerializedName("version") val version: Int?,
)