package ru.vld43.mangadexapp.di

import dagger.Binds
import dagger.Module
import ru.vld43.mangadexapp.data.repository.MangaRepositoryImpl
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Singleton

@Module
interface DataBindModule {

    @Singleton
    @Binds
    fun bindMangaRepository(
        mangaRepositoryImpl: MangaRepositoryImpl
    ): MangaRepository

}