package ru.vld43.mangadexapp.data.network.response.chapters

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.network.response.Relationship

data class Chapter(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val chapterAttributes: ChapterAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?,
)
