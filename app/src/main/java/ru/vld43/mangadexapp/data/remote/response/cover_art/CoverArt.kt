package ru.vld43.mangadexapp.data.remote.response.cover_art

import com.google.gson.annotations.SerializedName

data class CoverArt(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val data: DataCover?,
)
