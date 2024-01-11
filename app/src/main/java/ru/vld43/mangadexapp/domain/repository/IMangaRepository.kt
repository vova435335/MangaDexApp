package ru.vld43.mangadexapp.domain.repository

import ru.vld43.mangadexapp.common.data.ApiResult
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface IMangaRepository {

    suspend fun getMangaList(
        pageIndex: Int,
        pageSize: Int
    ): ApiResult<List<MangaWithCover>>

    suspend fun searchManga(
        pageSize: Int,
        pageIndex: Int,
        title: String
    ): ApiResult<List<MangaWithCover>>

    suspend fun getManga(
        mangaId: String
    ): ApiResult<MangaDetailsWithCover>

    suspend fun getChapters(
        pageIndex: Int,
        pageSize: Int,
        mangaId: String
    ): ApiResult<List<Chapter>>

    suspend fun getChapterPages(
        chapterId: String
    ): ApiResult<List<String>>
}
