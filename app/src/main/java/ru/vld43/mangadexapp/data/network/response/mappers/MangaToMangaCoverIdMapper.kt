package ru.vld43.mangadexapp.data.network.response.mappers

import ru.vld43.mangadexapp.data.network.response.manga.Manga
import javax.inject.Inject

private const val COVER_ART_TYPE = "cover_art"

class MangaToMangaCoverIdMapper @Inject constructor() {

    fun map(manga: Manga): String =
        manga.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
