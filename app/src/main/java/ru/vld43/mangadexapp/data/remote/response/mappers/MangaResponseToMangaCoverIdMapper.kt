package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.manga.MangaResponse

private const val COVER_ART_TYPE = "cover_art"

object MangaDtoToMangaCoverIdMapper {

    fun map(mangaResponse: MangaResponse): String =
        mangaResponse.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
