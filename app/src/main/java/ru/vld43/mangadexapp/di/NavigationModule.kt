package ru.vld43.mangadexapp.di

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import javax.inject.Singleton

@Module
class NavigationModule(val navController: NavController) {

    @Singleton
    @Provides
    fun provideContext(): AppNavigator = AppNavigator(navController)
}
