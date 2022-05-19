package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.dto.Relationship
import ru.vld43.mangadexapp.domain.models.Manga
import ru.vld43.mangadexapp.domain.models.MangaDetails

private const val NONE = "none"
private const val UNTITLED = "untitled"

data class MangaDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: MangaAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?,
)

fun MangaDto.toManga(): Manga =
    Manga(
        id = id ?: "",
        title = attributes?.altTitles
            ?.mapNotNull { it.ru }
            ?.firstOrNull()
            ?: attributes?.title?.en
            ?: UNTITLED,
        coverId = relationships?.first { it.type == "cover_art" }?.id ?: ""
    )

fun MangaDto.toMangaDetails(): MangaDetails =
    MangaDetails(
        id = id ?: "",
        title =  attributes?.altTitles
            ?.mapNotNull { it.ru }
            ?.firstOrNull()
            ?: attributes?.title?.en
            ?: UNTITLED,
        coverId = relationships?.first { it.type == "cover_art" }?.id ?: "",
        description = attributes?.description?.ru ?: attributes?.description?.en ?: NONE,
        tags = attributes?.tags
            ?.mapNotNull { it.attributes?.name?.ru ?: it.attributes?.name?.en }
            ?: emptyList(),
        status = attributes?.status ?: NONE,
        contentRating = attributes?.contentRating ?: NONE,
        lastChapter = attributes?.lastChapter ?: "-"
    )
