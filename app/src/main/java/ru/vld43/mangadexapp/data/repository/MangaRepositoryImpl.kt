package ru.vld43.mangadexapp.data.repository

import android.util.Log
import io.reactivex.Single
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.dto.toManga
import ru.vld43.mangadexapp.domain.models.Manga
import ru.vld43.mangadexapp.domain.repository.MangaRepository

class MangaRepositoryImpl(private val mangaDexApi: MangaDexApi) : MangaRepository {

    override fun getMangaList(): Single<List<Manga>> =
        mangaDexApi.getMangaList().map { mangaListDto ->
            mangaListDto.mangaList?.map { mangaDto ->
                Log.d("TAG", "getMangaList: ")
                mangaDto.toManga()
            }
        }
}