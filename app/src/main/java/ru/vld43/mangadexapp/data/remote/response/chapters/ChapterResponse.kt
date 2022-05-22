package ru.vld43.mangadexapp.data.remote.response.chapters

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.response.Relationship

data class ChapterResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val chapterAttributes: ChapterAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?,
)
