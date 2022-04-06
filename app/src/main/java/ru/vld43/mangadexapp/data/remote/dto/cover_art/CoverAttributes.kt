package ru.vld43.mangadexapp.data.remote.dto.cover_art

import com.google.gson.annotations.SerializedName

data class CoverAttributes(
    @SerializedName("volume") val volume: String?,
    @SerializedName("fileName") val fileName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("locale") val locale: String?,
    @SerializedName("version") val version: Int?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)
