package ru.vld43.mangadexapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface IMangaRepository {

    suspend fun getMangaList(pageIndex: Int, pageSize: Int): List<MangaWithCover>

    suspend fun searchManga(pageSize: Int, pageIndex: Int, title: String): List<MangaWithCover>

    fun getManga(mangaId: String): Flow<Result<MangaDetailsWithCover>>

    suspend fun getChapters(pageIndex: Int, pageSize: Int, mangaId: String): List<Chapter>

    fun getChapterPages(chapterId: String): Flow<Result<List<String>>>
}
