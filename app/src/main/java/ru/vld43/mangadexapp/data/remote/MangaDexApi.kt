package ru.vld43.mangadexapp.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vld43.mangadexapp.common.Constants.GET_COVER_ART
import ru.vld43.mangadexapp.common.Constants.GET_MANGA_LIST
import ru.vld43.mangadexapp.common.Constants.QUERY_LIMIT_KEY_PARAMETER
import ru.vld43.mangadexapp.data.remote.dto.cover_art.CoverArtDto
import ru.vld43.mangadexapp.data.remote.dto.manga.MangaListDto

interface MangaDexApi {

    @GET(GET_MANGA_LIST)
    fun getMangaList(
        @Query(QUERY_LIMIT_KEY_PARAMETER) query: Int = 100
    ): Single<MangaListDto>

    @GET(GET_COVER_ART)
    fun getCoverArt(
        @Path("id") id: String
    ): Single<CoverArtDto>

}