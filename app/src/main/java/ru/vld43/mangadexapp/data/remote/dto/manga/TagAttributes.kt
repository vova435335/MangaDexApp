package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName

data class TagAttributes(
    @SerializedName("name") val name: LocalizedString?,
    @SerializedName("description") val description: LocalizedString?,
    @SerializedName("group") val group: String?,
    @SerializedName("version") val version: Int?
)