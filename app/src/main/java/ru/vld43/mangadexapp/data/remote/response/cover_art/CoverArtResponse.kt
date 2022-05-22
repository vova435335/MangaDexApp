package ru.vld43.mangadexapp.data.remote.response.cover_art

import com.google.gson.annotations.SerializedName

data class CoverArtResponse(
    @SerializedName("result") val result: String? = null,
    @SerializedName("response") val response: String? = null,
    @SerializedName("data") val data: DataCover? = null
)
