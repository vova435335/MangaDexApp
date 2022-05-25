package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.common.data.UrlConstants
import ru.vld43.mangadexapp.data.remote.response.cover_art.CoverArtResponse
import ru.vld43.mangadexapp.data.remote.response.manga.MangaByIdResponse
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover

private const val NONE = "none"
private const val UNTITLED = "untitled"

const val IMAGE_SIZE = ".512.jpg"

object MangaDetailsWithCoverMapper {

    fun map(mangaResponse: MangaByIdResponse, mangaCover: CoverArtResponse?): MangaDetailsWithCover {
        val id = mangaResponse.manga?.id ?: ""
        val title = mangaResponse.manga?.attributes?.title?.en ?: UNTITLED
        val description = mangaResponse.manga?.attributes?.description?.en ?: NONE
        val tags = mangaResponse.manga?.attributes?.tags
            ?.mapNotNull { it.attributes?.name?.ru ?: it.attributes?.name?.en }
            ?: emptyList()

        val lastChapter = mangaResponse.manga?.attributes?.lastChapter
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
        "${UrlConstants.COVER_ART_URL}/$mangaId/$coverFileName$IMAGE_SIZE"
}
