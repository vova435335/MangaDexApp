package ru.vld43.mangadexapp.app

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Log.e("RX", "Global error")
            }
        }
    }
}
