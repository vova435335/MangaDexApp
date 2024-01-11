package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.common.data.UrlConstants
import ru.vld43.mangadexapp.data.remote.response.cover_art.CoverArt
import ru.vld43.mangadexapp.data.remote.response.manga.Manga
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import javax.inject.Inject

private const val UNTITLED = "untitled"

class MangaWithCoverMapper @Inject constructor() {

    fun map(manga: Manga, mangaCover: CoverArt?): MangaWithCover {

            val id = manga.id ?: ""
            val title = manga.attributes?.run {
                altTitles
                    ?.firstNotNullOfOrNull { it.ru }
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
