package ru.vld43.mangadexapp.data.network.response.mappers

import ru.vld43.mangadexapp.data.network.response.manga.MangaById
import javax.inject.Inject

private const val COVER_ART_TYPE = "cover_art"

class MangaByIdToMangaCoverIdMapper @Inject constructor() {

    fun map(mangaResponse: MangaById): String =
        mangaResponse.manga?.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
