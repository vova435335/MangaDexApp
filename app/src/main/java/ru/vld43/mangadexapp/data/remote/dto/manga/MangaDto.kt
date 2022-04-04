package ru.vld43.mangadexapp.data.remote.dto.manga

import com.google.gson.annotations.SerializedName
import ru.vld43.mangadexapp.data.remote.dto.Relationship
import ru.vld43.mangadexapp.domain.models.Manga

data class MangaDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("attributes") val attributes: MangaAttributes?,
    @SerializedName("relationships") val relationships: List<Relationship>?
)

fun MangaDto.toManga(): Manga =
    Manga(
        id = id ?: "",
        title = attributes?.title?.ru ?: attributes?.title?.en ?: "",
        description = attributes?.description?.ru ?: attributes?.description?.en ?: "",
        coverId = relationships?.first {
            it.type == "cover_art"
        }?.id ?: ""
    )
