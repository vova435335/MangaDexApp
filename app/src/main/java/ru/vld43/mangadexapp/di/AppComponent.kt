package ru.vld43.mangadexapp.di

import dagger.Component
import ru.vld43.mangadexapp.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}