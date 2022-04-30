package ru.vld43.mangadexapp.ui.navigation

import androidx.navigation.NavController
import ru.vld43.mangadexapp.ui.main.MainFragmentDirections
import javax.inject.Inject

class AppNavigator @Inject constructor(
    private val navController: NavController,
) {

    fun navigateToMangaDetails() {
        val action = MainFragmentDirections.actionMainFragmentToMangaDetailsFragment()
        navController.navigate(action)
    }
}