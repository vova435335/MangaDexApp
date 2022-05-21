package ru.vld43.mangadexapp.data.remote.dto.mappers

import ru.vld43.mangadexapp.data.remote.dto.manga.MangaByIdDto

private const val COVER_ART_TYPE = "cover_art"

object MangaByIdDtoToMangaCoverIdMapper {

    fun map(mangaDto: MangaByIdDto): String =
        mangaDto.manga?.relationships?.first { it.type == COVER_ART_TYPE }?.id ?: ""
}
