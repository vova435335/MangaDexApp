package ru.vld43.mangadexapp.data.remote.dto.mappers

import ru.vld43.mangadexapp.common.Constants
import ru.vld43.mangadexapp.data.remote.dto.cover_art.CoverArtDto
import ru.vld43.mangadexapp.data.remote.dto.manga.MangaByIdDto
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover

private const val NONE = "none"
private const val UNTITLED = "untitled"

const val IMAGE_SIZE = ".512.jpg"

object MangaDetailsWithCoverMapper {

    fun map(mangaDto: MangaByIdDto, mangaCover: CoverArtDto?): MangaDetailsWithCover {
        val id = mangaDto.manga?.id ?: ""
        val title = mangaDto.manga?.attributes?.run {
            altTitles
                ?.mapNotNull { it.ru }
                ?.firstOrNull()
                ?: title?.en
        } ?: UNTITLED

        val description = mangaDto.manga?.attributes?.description?.ru
            ?: mangaDto.manga?.attributes?.description?.en
            ?: NONE

        val tags = mangaDto.manga?.attributes?.tags
            ?.mapNotNull { it.attributes?.name?.ru ?: it.attributes?.name?.en }
            ?: emptyList()

        val lastChapter = mangaDto.manga?.attributes?.lastChapter
        val coverUrl = createCoverUrl(id, mangaCover?.data?.attributes?.fileName ?: "")

        return MangaDetailsWithCover(
            id = id,
            title = title,
            description = description,
            tags = tags,
            status = mangaDto.manga?.attributes?.status ?: NONE,
            contentRating = mangaDto.manga?.attributes?.contentRating ?: NONE,
            lastChapter = lastChapter,
            coverUrl = coverUrl
        )
    }

    private fun createCoverUrl(mangaId: String, coverFileName: String) =
        "${Constants.COVER_ART_URL}/$mangaId/$coverFileName$IMAGE_SIZE"
}
