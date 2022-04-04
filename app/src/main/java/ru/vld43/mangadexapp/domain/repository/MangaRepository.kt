package ru.vld43.mangadexapp.domain.repository

import io.reactivex.Single
import ru.vld43.mangadexapp.domain.models.MangaWithCover

interface MangaRepository {

    fun getMangaList(): Single<List<MangaWithCover>>
}