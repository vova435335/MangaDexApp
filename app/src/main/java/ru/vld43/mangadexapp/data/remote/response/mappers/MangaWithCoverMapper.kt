package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.common.data.UrlConstants
import ru.vld43.mangadexapp.data.remote.response.cover_art.CoverArtResponse
import ru.vld43.mangadexapp.data.remote.response.manga.MangaResponse
import ru.vld43.mangadexapp.domain.models.MangaWithCover

private const val UNTITLED = "untitled"

object MangaWithCoverMapper {

    fun map(mangaResponse: MangaResponse, mangaCover: CoverArtResponse?): MangaWithCover {

            val id = mangaResponse.id ?: ""
            val title = mangaResponse.attributes?.run {
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
        "${UrlConstants.COVER_ART_URL}/$mangaId/$coverFileName$IMAGE_SIZE"
}
