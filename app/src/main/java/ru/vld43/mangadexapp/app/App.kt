package ru.vld43.mangadexapp.app

import android.app.Application
import ru.vld43.mangadexapp.di.AppComponent
import ru.vld43.mangadexapp.di.AppModule
import ru.vld43.mangadexapp.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .build()
    }
}