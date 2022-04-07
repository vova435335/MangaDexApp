package ru.vld43.mangadexapp.domain.repository

import io.reactivex.Observable
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface MangaRepository {

    fun getMangaList(): Observable<List<MangaWithCover>>

    fun searchManga(title: String): Observable<List<MangaWithCover>>
}