package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.dto.Relationship

data class MangaDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("attributes") val attributes: MangaAttributes? = null,
    @SerializedName("relationships") val relationships: List<Relationship>? = null,
)