package ru.vld43.mangadexapp.di

import dagger.Binds
import dagger.Module
import ru.vld43.mangadexapp.data.repository.MangaRepository
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Singleton

@Module
interface DataBindModule {

    @Singleton
    @Binds
    fun bindMangaRepository(
        mangaRepository: MangaRepository
    ): IMangaRepository
}