package ru.vld43.mangadexapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Relationship(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("related") val related: String?
)