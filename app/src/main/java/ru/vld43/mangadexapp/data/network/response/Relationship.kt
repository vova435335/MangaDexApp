package ru.vld43.mangadexapp.data.network.response

import com.google.gson.annotations.SerializedName

data class Relationship(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("related") val related: String?
)