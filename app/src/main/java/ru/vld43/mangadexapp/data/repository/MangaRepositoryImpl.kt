package ru.vld43.mangadexapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.vld43.mangadexapp.common.Constants.COVER_ART_URL
import ru.vld43.mangadexapp.common.data.extansions.map
import ru.vld43.mangadexapp.common.data.extansions.toResult
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.data.paging.MangaListPagerLoader
import ru.vld43.mangadexapp.data.paging.MangaPagingSource
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.dto.manga.toManga
import ru.vld43.mangadexapp.data.remote.dto.mappers.MangaByIdDtoToMangaCoverIdMapper
import ru.vld43.mangadexapp.data.remote.dto.mappers.MangaDetailsWithCoverMapper
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaDexApi: MangaDexApi,
) : MangaRepository {

    private companion object {
        const val IMAGE_SIZE = ".512.jpg"
        const val PAGE_SIZE = 15
    }

    override suspend fun getPagingMangaList(): Flow<PagingData<MangaWithCover>> {
        val loader: MangaListPagerLoader = { pageSize, pageIndex ->
            getMangaList(pageSize, pageIndex)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MangaPagingSource(loader) }
        ).flow
    }

    override suspend fun searchPagingManga(title: String): Flow<PagingData<MangaWithCover>> {
        val loader: MangaListPagerLoader = { pageSize, pageIndex ->
            searchManga(
                title = title,
                pageSize = pageSize,
                pageIndex = pageIndex
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MangaPagingSource(loader) }
        ).flow
    }

    override fun getManga(mangaId: String): Flow<Result<MangaDetailsWithCover>> = flow {
        when (val manga = mangaDexApi.getManga(mangaId).toResult()) {
            is Result.Success -> {
                val mangaCover = mangaDexApi.getCoverArt(
                    MangaByIdDtoToMangaCoverIdMapper.map(manga.data)
                ).body()

                val domainModel = manga.map(ifSuccess = {
                    MangaDetailsWithCoverMapper.map(manga.data, mangaCover)
                })

                emit(domainModel)
            }
            else -> emit(manga.map(ifError = { "Ошибка сети!" }))
        }
    }

    private suspend fun getMangaList(pageSize: Int, pageIndex: Int): List<MangaWithCover> =
        withContext(Dispatchers.IO) {
            val offset = pageSize * pageIndex

            mangaDexApi.getMangaList(pageSize, offset)
                .body()
                ?.mangaList
                ?.map {
                    val manga = it.toManga()
                    val mangaCover = mangaDexApi.getCoverArt(manga.coverId).body()
                    val coverUrl = createUrl(manga.id, mangaCover?.data?.attributes?.fileName ?: "")

                    MangaWithCover(
                        id = manga.id,
                        title = manga.title,
                        coverUrl = coverUrl
                    )
                }
                ?: emptyList()
        }

    private suspend fun searchManga(
        pageSize: Int,
        pageIndex: Int,
        title: String,
    ): List<MangaWithCover> = withContext(Dispatchers.IO) {
        val offset = pageSize * pageIndex

        mangaDexApi.searchManga(title, pageSize, offset)
            .body()
            ?.mangaList
            ?.map {
                val manga = it.toManga()
                val mangaCover = mangaDexApi.getCoverArt(manga.coverId).body()
                val coverUrl = createUrl(manga.id, mangaCover?.data?.attributes?.fileName ?: "")

                MangaWithCover(
                    id = manga.id,
                    title = manga.title,
                    coverUrl = coverUrl
                )
            }
            ?: emptyList()
    }

    private fun createUrl(mangaId: String, fileName: String): String =
        "$COVER_ART_URL/$mangaId/$fileName$IMAGE_SIZE"
}
