package ru.vld43.mangadexapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.dto.manga.toManga
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import ru.vld43.mangadexapp.common.Constants.COVER_ART_URL
import ru.vld43.mangadexapp.data.paging.MangaListPagerLoader
import ru.vld43.mangadexapp.data.paging.MangaPagingSource
import ru.vld43.mangadexapp.domain.models.Manga
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaDexApi: MangaDexApi,
) : MangaRepository {

    private companion object {
        const val IMAGE_SIZE = ".256.jpg"
        const val PAGE_SIZE = 15
    }

    override fun getPagingMangaList(): Observable<PagingData<MangaWithCover>> {
        val loader: MangaListPagerLoader = { pageSize, pageIndex ->
            getMangaList(pageSize, pageIndex)
                .subscribeOn(Schedulers.io())
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MangaPagingSource(loader) }
        ).observable
    }

    override fun searchPagingManga(title: String): Observable<PagingData<MangaWithCover>> {
        val loader: MangaListPagerLoader = { pageSize, pageIndex ->
            searchManga(
                title = title,
                pageSize = pageSize,
                pageIndex = pageIndex
            )
                .subscribeOn(Schedulers.io())
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MangaPagingSource(loader) }
        ).observable
    }

    private fun getMangaList(pageSize: Int, pageIndex: Int): Single<List<MangaWithCover>> {
        val offset = pageSize * pageIndex

        return mangaDexApi.getMangaList(pageSize, offset).map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                mangaDto.toManga()
            } ?: emptyList()
        }
            .toObservable()
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
    }

    private fun searchManga(
        pageSize: Int,
        pageIndex: Int,
        title: String,
    ): Single<List<MangaWithCover>> {
        val offset = pageIndex * pageSize

        return mangaDexApi.searchManga(title, pageSize, offset).map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                mangaDto.toManga()
            } ?: emptyList()
        }
            .toObservable()
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
    }

    private fun createUrl(manga: Manga, fileName: String): String =
        "$COVER_ART_URL/${manga.id}/$fileName$IMAGE_SIZE"
}