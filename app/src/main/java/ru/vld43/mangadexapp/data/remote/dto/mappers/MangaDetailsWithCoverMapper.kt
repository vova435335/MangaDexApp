package ru.vld43.mangadexapp.data.remote.dto.mappers

import ru.vld43.mangadexapp.common.Constants
import ru.vld43.mangadexapp.data.remote.dto.cover_art.CoverArtDto
import ru.vld43.mangadexapp.data.remote.dto.manga.MangaDto
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover

private const val NONE = "none"
private const val UNTITLED = "untitled"

const val IMAGE_SIZE = ".512.jpg"

object MangaDetailsWithCoverMapper {

    fun map(mangaDto: MangaDto, mangaCover: CoverArtDto?): MangaDetailsWithCover {
        val id = mangaDto.id ?: ""
        val title = mangaDto.attributes?.run {
            altTitles
                ?.mapNotNull { it.ru }
                ?.firstOrNull()
                ?: title?.en
        } ?: UNTITLED

        val description = mangaDto.attributes?.description?.ru
            ?: mangaDto.attributes?.description?.en
            ?: NONE

        val tags = mangaDto.attributes?.tags
            ?.mapNotNull { it.attributes?.name?.ru ?: it.attributes?.name?.en }
            ?: emptyList()

        val lastChapter = mangaDto.attributes?.lastChapter
        val coverUrl =
            "${Constants.COVER_ART_URL}/$id/${mangaCover?.data?.attributes?.fileName ?: ""}$IMAGE_SIZE"

        return MangaDetailsWithCover(
            id = id,
            title = title,
            description = description,
            tags = tags,
            status = mangaDto.attributes?.status ?: NONE,
            contentRating = mangaDto.attributes?.contentRating ?: NONE,
            lastChapter = lastChapter,
            coverUrl = coverUrl
        )
    }
}
