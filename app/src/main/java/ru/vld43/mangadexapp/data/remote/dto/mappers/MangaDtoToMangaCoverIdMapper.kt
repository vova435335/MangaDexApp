package ru.vld43.mangadexapp.data.remote.dto.mappers

import ru.vld43.mangadexapp.data.remote.dto.manga.MangaDto

private const val COVER_ART_TYPE = "cover_art"

object MangaDtoToMangaCoverIdMapper {

    fun map(mangaDto: MangaDto): String =
        mangaDto.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
