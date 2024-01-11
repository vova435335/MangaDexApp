package ru.vld43.mangadexapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vld43.mangadexapp.common.data.UrlConstants.GET_CHAPTERS
import ru.vld43.mangadexapp.common.data.UrlConstants.GET_CHAPTER_PAGES
import ru.vld43.mangadexapp.common.data.UrlConstants.GET_COVER_ART
import ru.vld43.mangadexapp.common.data.UrlConstants.GET_MANGA
import ru.vld43.mangadexapp.common.data.UrlConstants.GET_MANGA_LIST
import ru.vld43.mangadexapp.common.data.UrlConstants.QUERY_CHAPTERS_LANGUAGE_KEY
import ru.vld43.mangadexapp.common.data.UrlConstants.QUERY_LIMIT_KEY
import ru.vld43.mangadexapp.common.data.UrlConstants.QUERY_CHAPTERS_MANGA_ID_KEY
import ru.vld43.mangadexapp.common.data.UrlConstants.QUERY_OFFSET_KEY
import ru.vld43.mangadexapp.common.data.UrlConstants.QUERY_SEARCH_KEY_PARAMETER
import ru.vld43.mangadexapp.common.data.UrlConstants.SEARCH_MANGA
import ru.vld43.mangadexapp.data.network.response.chapter_pages.ChapterPages
import ru.vld43.mangadexapp.data.network.response.chapters.Chapters
import ru.vld43.mangadexapp.data.network.response.cover_art.CoverArt
import ru.vld43.mangadexapp.data.network.response.manga.MangaById
import ru.vld43.mangadexapp.data.network.response.manga.MangaList

interface MangaDexApi {

    @GET(GET_MANGA_LIST)
    suspend fun getMangaList(
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query("availableTranslatedLanguage[]") language: String = "ru",
    ): Response<MangaList>

    @GET(GET_MANGA)
    suspend fun getManga(
        @Path("id") id: String,
    ): Response<MangaById>

    @GET(GET_COVER_ART)
    suspend fun getCoverArt(
        @Path("id") id: String,
    ): Response<CoverArt>

    @GET(SEARCH_MANGA)
    suspend fun searchManga(
        @Query(QUERY_SEARCH_KEY_PARAMETER) title: String,
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query("availableTranslatedLanguage[]") language: String = "ru",
    ): Response<MangaList>

    @GET(GET_CHAPTERS)
    suspend fun getChapters(
        @Query(QUERY_CHAPTERS_MANGA_ID_KEY) mangaId: String,
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query(QUERY_CHAPTERS_LANGUAGE_KEY) language: String = "ru",
    ): Response<Chapters>

    @GET(GET_CHAPTER_PAGES)
    suspend fun getChapterPages(
        @Path("id") id: String,
    ): Response<ChapterPages>
}
