package ru.vld43.mangadexapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.vld43.mangadexapp.common.data.extansions.map
import ru.vld43.mangadexapp.common.data.extansions.toResult
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.data.paging.ChaptersPageLoader
import ru.vld43.mangadexapp.data.paging.ChaptersPagingSource
import ru.vld43.mangadexapp.data.paging.MangaListPagerLoader
import ru.vld43.mangadexapp.data.paging.MangaPagingSource
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.response.mappers.*
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import java.io.IOException
import javax.inject.Inject

private const val MANGA_PAGE_SIZE = 15
private const val CHAPTERS_PAGE_SIZE = 15

private const val UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred"
private const val INTERNET_CONNECTION_ERROR_MESSAGE =
    "Couldn't reach server. Check your internet connection."

class MangaRepositoryImpl @Inject constructor(
    private val mangaDexApi: MangaDexApi,
) : MangaRepository {

    override suspend fun getPagingMangaList(): Flow<PagingData<MangaWithCover>> {
        val loader: MangaListPagerLoader = { pageSize, pageIndex ->
            getMangaList(pageSize, pageIndex)
        }

        return Pager(
            config = PagingConfig(
                pageSize = MANGA_PAGE_SIZE,
                initialLoadSize = MANGA_PAGE_SIZE,
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
                pageSize = MANGA_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MangaPagingSource(loader) }
        ).flow
    }

    override fun getManga(mangaId: String): Flow<Result<MangaDetailsWithCover>> = flow {
        try {
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
                else -> emit(manga.map(ifError = { UNEXPECTED_ERROR_MESSAGE }))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(INTERNET_CONNECTION_ERROR_MESSAGE))
        }
    }

    override fun getPagingChapters(mangaId: String): Flow<PagingData<Chapter>> {
        val loader: ChaptersPageLoader = { pageSize, pageIndex ->
            getChapters(pageSize, pageIndex, mangaId)
        }

        return Pager(
            config = PagingConfig(
                pageSize = CHAPTERS_PAGE_SIZE,
                initialLoadSize = CHAPTERS_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { ChaptersPagingSource(loader) }
        ).flow
    }

    private suspend fun getMangaList(pageSize: Int, pageIndex: Int): List<MangaWithCover> =
        withContext(Dispatchers.IO) {
            val offset = pageSize * pageIndex

            mangaDexApi.getMangaList(pageIndex, offset)
                .body()
                ?.mangaList
                ?.map {
                    val mangaCover = mangaDexApi.getCoverArt(
                        MangaDtoToMangaCoverIdMapper.map(it)
                    ).body()

                    MangaWithCoverMapper.map(it, mangaCover)
                } ?: emptyList()
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
                val mangaCover = mangaDexApi.getCoverArt(
                    MangaDtoToMangaCoverIdMapper.map(it)
                ).body()

                MangaWithCoverMapper.map(it, mangaCover)
            } ?: emptyList()
    }

    private suspend fun getChapters(
        pageSize: Int,
        pageIndex: Int,
        mangaId: String,
    ): List<Chapter> = withContext(Dispatchers.IO) {
        val offset = pageSize * pageIndex
        Log.d("TAG", "getChapters: $pageIndex, $offset")

        mangaDexApi.getChapters(mangaId, pageIndex, offset)
            .body()
            ?.data
            ?.map {
                ChapterMapper.map(it)
            } ?: emptyList()
    }
}
