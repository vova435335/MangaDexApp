package ru.vld43.mangadexapp.data.remote.response.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.response.Relationship

data class MangaResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("attributes") val attributes: MangaAttributes? = null,
    @SerializedName("relationships") val relationships: List<Relationship>? = null,
)