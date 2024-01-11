package ru.vld43.mangadexapp.data.network.response.mappers

import ru.vld43.mangadexapp.common.data.COVER_ART_URL
import ru.vld43.mangadexapp.data.network.response.cover_art.CoverArt
import ru.vld43.mangadexapp.data.network.response.manga.MangaById
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import javax.inject.Inject

private const val NONE = "none"
private const val UNTITLED = "untitled"

const val IMAGE_SIZE = ".512.jpg"

class MangaDetailsWithCoverMapper @Inject constructor() {

    fun map(mangaResponse: MangaById, mangaCover: CoverArt?): MangaDetailsWithCover {
        val id = mangaResponse.manga?.id ?: ""
        val title = mangaResponse.manga?.attributes?.run {
            altTitles
                ?.firstNotNullOfOrNull { it.ru }
                ?: title?.en
        } ?: UNTITLED

        val description = mangaResponse.manga?.attributes?.description?.ru
            ?: mangaResponse.manga?.attributes?.description?.en
            ?: NONE

        val tags = mangaResponse.manga?.attributes?.tags
            ?.mapNotNull { it.attributes?.name?.en }
            ?: emptyList()

        val lastChapter = if(mangaResponse.manga?.attributes?.lastChapter.isNullOrEmpty()) {
            null
        } else {
            mangaResponse.manga?.attributes?.lastChapter
        }

        val coverUrl = createCoverUrl(id, mangaCover?.data?.attributes?.fileName ?: "")

        return MangaDetailsWithCover(
            id = id,
            title = title,
            description = description,
            tags = tags,
            status = mangaResponse.manga?.attributes?.status ?: NONE,
            contentRating = mangaResponse.manga?.attributes?.contentRating ?: NONE,
            lastChapter = lastChapter,
            coverUrl = coverUrl
        )
    }

    private fun createCoverUrl(mangaId: String, coverFileName: String) =
        "${COVER_ART_URL}/$mangaId/$coverFileName$IMAGE_SIZE"
}
