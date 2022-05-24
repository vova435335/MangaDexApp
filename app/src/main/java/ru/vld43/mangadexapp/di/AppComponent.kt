package ru.vld43.mangadexapp.di

import dagger.Component
import ru.vld43.mangadexapp.ui.chapters.ChaptersFragment
import ru.vld43.mangadexapp.ui.details.MangaDetailsFragment
import ru.vld43.mangadexapp.ui.main.MainFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(mangaDetailsFragment: MangaDetailsFragment)

    fun inject(chaptersFragment: ChaptersFragment)
}
