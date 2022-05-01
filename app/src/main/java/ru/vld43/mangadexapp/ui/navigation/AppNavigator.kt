package ru.vld43.mangadexapp.ui.navigation

import androidx.navigation.NavController
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.ui.main.MainFragmentDirections
import javax.inject.Inject

class AppNavigator @Inject constructor(
    private val navController: NavController,
) {

    fun navigateToMangaDetails(mangaWithCover: MangaWithCover) {
        val action = MainFragmentDirections.actionMainFragmentToMangaDetailsFragment(mangaWithCover)
        navController.navigate(action)
    }
}