package ru.vld43.mangadexapp.data.repository

import io.reactivex.Single
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.dto.manga.toManga
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaDexApi: MangaDexApi
) : MangaRepository {

    override fun getMangaList(): Single<List<MangaWithCover>> =
        mangaDexApi.getMangaList().map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                mangaDto.toManga()
            }
        }.toObservable()
            .flatMapIterable { it }
            .flatMapSingle { manga ->
                mangaDexApi.getCoverArt(manga.coverId)
                    .map {
                        MangaWithCover(manga, it.data?.attributes?.fileName ?: "")
                    }
            }
            .toList()
}