package ru.vld43.mangadexapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface MangaRepository {

    suspend fun getPagingMangaList(): Flow<PagingData<MangaWithCover>>

    suspend fun searchPagingManga(title: String): Flow<PagingData<MangaWithCover>>
}