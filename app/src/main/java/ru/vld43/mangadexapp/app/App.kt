package ru.vld43.mangadexapp.app

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import ru.vld43.mangadexapp.di.AppComponent
import ru.vld43.mangadexapp.di.AppModule
import ru.vld43.mangadexapp.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Log.e("RX", "Global error")
            }
        }

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .build()
    }
}