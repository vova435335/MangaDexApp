package ru.vld43.mangadexapp.data.network.response.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.network.response.Relationship

data class Tag(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: TagAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?
)
