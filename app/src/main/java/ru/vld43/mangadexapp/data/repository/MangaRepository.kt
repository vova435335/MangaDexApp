package ru.vld43.mangadexapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.vld43.mangadexapp.common.data.extansions.map
import ru.vld43.mangadexapp.common.data.extansions.toResult
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.response.mappers.ChapterPagesMapper
import ru.vld43.mangadexapp.data.remote.response.mappers.ChaptersMapper
import ru.vld43.mangadexapp.data.remote.response.mappers.MangaByIdToMangaCoverIdMapper
import ru.vld43.mangadexapp.data.remote.response.mappers.MangaDetailsWithCoverMapper
import ru.vld43.mangadexapp.data.remote.response.mappers.MangaToMangaCoverIdMapper
import ru.vld43.mangadexapp.data.remote.response.mappers.MangaWithCoverMapper
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import java.io.IOException
import javax.inject.Inject

private const val UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred"
private const val INTERNET_CONNECTION_ERROR_MESSAGE =
    "Couldn't reach server. Check your internet connection."

class MangaRepository @Inject constructor(
    private val mangaDexApi: MangaDexApi,
    private val chapterPagesMapper: ChapterPagesMapper,
    private val chaptersMapper: ChaptersMapper,
    private val mangaByIdToMangaCoverIdMapper: MangaByIdToMangaCoverIdMapper,
    private val mangaDetailsWithCoverMapper: MangaDetailsWithCoverMapper,
    private val mangaToMangaCoverIdMapper: MangaToMangaCoverIdMapper,
    private val mangaWithCoverMapper: MangaWithCoverMapper
) : IMangaRepository {
    override suspend fun getMangaList(pageIndex: Int, pageSize: Int): List<MangaWithCover> =
        withContext(Dispatchers.IO) {
            val offset = pageSize * pageIndex

            mangaDexApi.getMangaList(pageSize, offset)
                .body()
                ?.mangaList
                ?.map {
                    val mangaCover = mangaDexApi.getCoverArt(
                        mangaToMangaCoverIdMapper.map(it)
                    ).body()

                    mangaWithCoverMapper.map(it, mangaCover)
                } ?: emptyList()
        }

    override suspend fun searchManga(
        pageSize: Int,
        pageIndex: Int,
        title: String
    ): List<MangaWithCover> = withContext(Dispatchers.IO) {
        val offset = pageSize * pageIndex

        delay(300L)

        mangaDexApi.searchManga(title, pageSize, offset)
            .body()
            ?.mangaList
            ?.map {
                val mangaCover = mangaDexApi.getCoverArt(
                    mangaToMangaCoverIdMapper.map(it)
                ).body()

                mangaWithCoverMapper.map(it, mangaCover)
            } ?: emptyList()
    }

    override suspend fun getManga(mangaId: String): Result<MangaDetailsWithCover> =
        withContext(Dispatchers.IO) {
            try {
                when (val manga = mangaDexApi.getManga(mangaId).toResult()) {
                    is Result.Success -> {
                        val mangaCover = mangaDexApi.getCoverArt(
                            mangaByIdToMangaCoverIdMapper.map(manga.data)
                        ).body()

                        val domainModel = manga.map(ifSuccess = {
                            mangaDetailsWithCoverMapper.map(manga.data, mangaCover)
                        })

                        domainModel
                    }

                    else -> manga.map(ifError = { UNEXPECTED_ERROR_MESSAGE })
                }
            } catch (e: HttpException) {
                Result.Error(e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE)
            } catch (e: IOException) {
                Result.Error(INTERNET_CONNECTION_ERROR_MESSAGE)
            }
        }

    override suspend fun getChapters(
        pageIndex: Int,
        pageSize: Int,
        mangaId: String
    ): List<Chapter> = withContext(Dispatchers.IO) {
        val offset = pageSize * pageIndex

        val chaptersResponse = mangaDexApi.getChapters(mangaId, pageSize, offset).body()
        if (chaptersResponse != null) {
            chaptersMapper.map(chaptersResponse)
        } else {
            emptyList()
        }
    }

    override suspend fun getChapterPages(chapterId: String): Result<List<String>> =
        withContext(Dispatchers.IO) {
            try {
                when (val chapterPages = mangaDexApi.getChapterPages(chapterId).toResult()) {
                    is Result.Success -> {
                        val domainModel =
                            chapterPages.map(ifSuccess = { chapterPagesMapper.map(it) })
                        domainModel
                    }

                    else -> {
                        chapterPages.map(ifError = { UNEXPECTED_ERROR_MESSAGE })
                    }
                }
            } catch (e: HttpException) {
                Result.Error(e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE)
            } catch (e: IOException) {
                Result.Error(INTERNET_CONNECTION_ERROR_MESSAGE)
            }
        }
}
