package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName

data class MangaAttributes(
    @SerializedName("title") val title: LocalizedString?,
    @SerializedName("altTitles") val altTitles: List<LocalizedString>?,
    @SerializedName("description") val description: LocalizedString?,
    @SerializedName("isLocked") val isLocked: Boolean?,
    @SerializedName("originalLanguage") val originalLanguage: String?,
    @SerializedName("lastVolume") val lastVolume: String?,
    @SerializedName("lastChapter") val lastChapter: String?,
    @SerializedName("publicationDemographic") val publicationDemographic: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("contentRating") val contentRating: String?,
    @SerializedName("chapterNumberResetOnNewVolume") val chapterNumberResetOnNewVolume: Boolean?,
    @SerializedName("availableTranslatedLanguages") val availableTranslatedLanguages: List<String>?,
    @SerializedName("tags") val tags: List<Tag>?,
    @SerializedName("state") val state: String?,
    @SerializedName("version") val version: Int?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)
