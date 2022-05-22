package ru.vld43.mangadexapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.di.AppComponent
import ru.vld43.mangadexapp.di.AppModule
import ru.vld43.mangadexapp.di.DaggerAppComponent
import ru.vld43.mangadexapp.di.NavigationModule

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .navigationModule(NavigationModule(navController = navController))
            .build()
    }
}
