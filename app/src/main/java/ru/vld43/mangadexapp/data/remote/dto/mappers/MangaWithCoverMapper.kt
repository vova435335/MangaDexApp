package ru.vld43.mangadexapp.data.remote.dto.mappers

import ru.vld43.mangadexapp.common.Constants
import ru.vld43.mangadexapp.data.remote.dto.cover_art.CoverArtDto
import ru.vld43.mangadexapp.data.remote.dto.manga.MangaDto
import ru.vld43.mangadexapp.domain.models.MangaWithCover

private const val UNTITLED = "untitled"

object MangaWithCoverMapper {

    fun map(mangaDto: MangaDto, mangaCover: CoverArtDto?): MangaWithCover {

            val id = mangaDto.id ?: ""
            val title = mangaDto.attributes?.run {
                altTitles
                    ?.mapNotNull { it.ru }
                    ?.firstOrNull()
                    ?: title?.en
            } ?: UNTITLED
            val coverUrl = createCoverUrl(
                id,
                mangaCover?.data?.attributes?.fileName ?: ""
            )

            return MangaWithCover(
                id = id,
                title = title,
                coverUrl = coverUrl
            )
    }

    private fun createCoverUrl(mangaId: String, coverFileName: String) =
        "${Constants.COVER_ART_URL}/$mangaId/$coverFileName$IMAGE_SIZE"
}
