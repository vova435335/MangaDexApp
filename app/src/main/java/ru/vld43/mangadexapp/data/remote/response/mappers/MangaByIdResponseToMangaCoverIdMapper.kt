package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.manga.MangaByIdResponse

private const val COVER_ART_TYPE = "cover_art"

object MangaByIdDtoToMangaCoverIdMapper {

    fun map(mangaResponse: MangaByIdResponse): String =
        mangaResponse.manga?.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
