package ru.vld43.mangadexapp.data.remote.response.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.response.Relationship

data class MangaResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: MangaAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?,
)