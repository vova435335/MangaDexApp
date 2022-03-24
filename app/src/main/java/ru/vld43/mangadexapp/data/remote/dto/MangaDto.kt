package ru.vld43.mangadexapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MangaDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: MangaAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?
)
