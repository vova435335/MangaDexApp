package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName

data class LocalizedString(
    @SerializedName("en") val en: String?,
    @SerializedName("ru") val ru: String?,
    @SerializedName("ja") val ja: String?,
    @SerializedName("uk") val uk: String?,
    @SerializedName("zh-hk") val zhHk: String?,
    @SerializedName("zh-ro") val zhRo: String?
)
