package ru.vld43.mangadexapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: TagAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?
)
