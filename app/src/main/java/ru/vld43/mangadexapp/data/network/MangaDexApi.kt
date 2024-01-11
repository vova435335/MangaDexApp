package ru.vld43.mangadexapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vld43.mangadexapp.data.network.response.chapter_pages.ChapterPages
import ru.vld43.mangadexapp.data.network.response.chapters.Chapters
import ru.vld43.mangadexapp.data.network.response.cover_art.CoverArt
import ru.vld43.mangadexapp.data.network.response.manga.MangaById
import ru.vld43.mangadexapp.data.network.response.manga.MangaList

const val QUERY_LIMIT_KEY = "limit"
const val QUERY_OFFSET_KEY = "offset"

const val QUERY_SEARCH_KEY_PARAMETER = "title"

const val QUERY_CHAPTERS_MANGA_ID_KEY = "manga"
const val QUERY_CHAPTERS_LANGUAGE_KEY = "translatedLanguage[]"

interface MangaDexApi {

    @GET("/manga")
    suspend fun getMangaList(
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query("availableTranslatedLanguage[]") language: String = "ru",
    ): Response<MangaList>

    @GET("/manga/{id}")
    suspend fun getManga(
        @Path("id") id: String,
    ): Response<MangaById>

    @GET("/cover/{id}")
    suspend fun getCoverArt(
        @Path("id") id: String,
    ): Response<CoverArt>

    @GET("/manga")
    suspend fun searchManga(
        @Query(QUERY_SEARCH_KEY_PARAMETER) title: String,
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query("availableTranslatedLanguage[]") language: String = "ru",
    ): Response<MangaList>

    @GET("/chapter")
    suspend fun getChapters(
        @Query(QUERY_CHAPTERS_MANGA_ID_KEY) mangaId: String,
        @Query(QUERY_LIMIT_KEY) limit: Int,
        @Query(QUERY_OFFSET_KEY) offset: Int,
        @Query(QUERY_CHAPTERS_LANGUAGE_KEY) language: String = "ru",
    ): Response<Chapters>

    @GET("/at-home/server/{id}")
    suspend fun getChapterPages(
        @Path("id") id: String,
    ): Response<ChapterPages>
}
