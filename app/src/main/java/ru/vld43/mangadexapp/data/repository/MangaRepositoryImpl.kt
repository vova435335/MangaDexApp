package ru.vld43.mangadexapp.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.dto.manga.toManga
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import ru.vld43.mangadexapp.common.Constants.COVER_ART_URL
import ru.vld43.mangadexapp.domain.models.Manga
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaDexApi: MangaDexApi,
) : MangaRepository {

    private companion object {
        const val IMAGE_SIZE = ".256.jpg"
    }

    override fun getMangaList(): Observable<List<MangaWithCover>> =
        mangaDexApi.getMangaList().map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                mangaDto.toManga()
            } ?: emptyList()
        }
            .flatMapIterable { it }
            .concatMap { manga ->
                mangaDexApi.getCoverArt(manga.coverId)
                    .map {
                        MangaWithCover(
                            manga,
                            createUrl(manga, it.data?.attributes?.fileName ?: "")
                        )
                    }
            }
            .toList()
            .toObservable()

    override fun searchManga(title: String): Observable<List<MangaWithCover>> =
        mangaDexApi.searchManga(title).map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                mangaDto.toManga()
            } ?: emptyList()
        }
            .flatMapIterable { it }
            .concatMap { manga ->
                mangaDexApi.getCoverArt(manga.id)
                    .map {
                        MangaWithCover(
                            manga,
                            createUrl(manga, it.data?.attributes?.fileName ?: "")
                        )
                    }
            }
            .toList()
            .toObservable()


    private fun createUrl(manga: Manga, fileName: String): String =
        "$COVER_ART_URL/${manga.id}/$fileName$IMAGE_SIZE"
}