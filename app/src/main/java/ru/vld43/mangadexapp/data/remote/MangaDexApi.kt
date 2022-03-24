package ru.vld43.mangadexapp.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import ru.vld43.mangadexapp.common.Constants.GET_MANGA_LIST
import ru.vld43.mangadexapp.data.remote.dto.MangaListDto

interface MangaDexApi {

    @GET(GET_MANGA_LIST)
    fun getMangaList(): Single<MangaListDto>

}