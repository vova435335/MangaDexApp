package ru.vld43.mangadexapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.vld43.mangadexapp.common.data.ApiResult
import ru.vld43.mangadexapp.common.data.ApiSuccess
import ru.vld43.mangadexapp.common.data.handleApi
import ru.vld43.mangadexapp.common.data.map
import ru.vld43.mangadexapp.data.network.MangaDexApi
import ru.vld43.mangadexapp.data.network.response.mappers.ChapterPagesMapper
import ru.vld43.mangadexapp.data.network.response.mappers.ChaptersMapper
import ru.vld43.mangadexapp.data.network.response.mappers.MangaByIdToMangaCoverIdMapper
import ru.vld43.mangadexapp.data.network.response.mappers.MangaDetailsWithCoverMapper
import ru.vld43.mangadexapp.data.network.response.mappers.MangaToMangaCoverIdMapper
import ru.vld43.mangadexapp.data.network.response.mappers.MangaWithCoverMapper
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

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

    override suspend fun getManga(mangaId: String): ApiResult<MangaDetailsWithCover> =
        withContext(Dispatchers.IO) {
            handleApi { mangaDexApi.getManga(mangaId) }
                .map { manga ->
                    val mangaCover = handleApi {
                        mangaDexApi.getCoverArt(
                            mangaByIdToMangaCoverIdMapper.map(manga)
                        )
                    }

                    if (mangaCover is ApiSuccess) {
                        mangaDetailsWithCoverMapper.map(
                            mangaResponse = manga,
                            mangaCover = mangaCover.data
                        )
                    } else {
                        mangaDetailsWithCoverMapper.map(
                            mangaResponse = manga,
                            mangaCover = null
                        )
                    }
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

    override suspend fun getChapterPages(chapterId: String): ApiResult<List<String>> =
        withContext(Dispatchers.IO) {
            handleApi { mangaDexApi.getChapterPages(chapterId) }
                .map(chapterPagesMapper::map)
        }
}
