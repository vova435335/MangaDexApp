package ru.vld43.mangadexapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface MangaRepository {

    suspend fun getPagingMangaList(): Flow<PagingData<MangaWithCover>>

    suspend fun searchPagingManga(title: String): Flow<PagingData<MangaWithCover>>

    fun getManga(mangaId: String): Flow<Result<MangaDetailsWithCover>>

    fun getPagingChapters(mangaId: String): Flow<PagingData<Chapter>>

    fun getChapterPages(chapterId: String): Flow<Result<List<String>>>
}
