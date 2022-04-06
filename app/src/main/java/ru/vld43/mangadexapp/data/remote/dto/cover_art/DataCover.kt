package ru.vld43.mangadexapp.data.remote.dto.cover_art

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.dto.Relationship

data class DataCover(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: CoverAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?
)