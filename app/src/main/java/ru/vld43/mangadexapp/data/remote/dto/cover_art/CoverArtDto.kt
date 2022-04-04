package ru.vld43.mangadexapp.data.remote.dto.cover_art

import com.google.gson.annotations.SerializedName

data class CoverArtDto(
    @SerializedName("result") val result: String?,
    @SerializedName("response") val response: String?,
    @SerializedName("data") val data: DataCover?
)
