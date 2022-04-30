package ru.vld43.mangadexapp.domain.repository

import androidx.paging.PagingData
import io.reactivex.Observable
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface MangaRepository {

    fun getPagingMangaList(): Observable<PagingData<MangaWithCover>>

    fun searchPagingManga(title: String): Observable<PagingData<MangaWithCover>>
}