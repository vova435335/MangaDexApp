package ru.vld43.mangadexapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.vld43.mangadexapp.ui.navigation.AppNavigator

class MainViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun openDetails() {
        appNavigator.navigateToMangaDetails()
    }
}