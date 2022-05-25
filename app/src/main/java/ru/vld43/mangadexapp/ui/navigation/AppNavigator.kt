package ru.vld43.mangadexapp.ui.navigation

import androidx.navigation.NavController
import ru.vld43.mangadexapp.ui.chapters.ChaptersFragmentDirections
import ru.vld43.mangadexapp.ui.manga_details.MangaDetailsFragmentDirections
import ru.vld43.mangadexapp.ui.main.MainFragmentDirections
import javax.inject.Inject

class AppNavigator @Inject constructor(
    private val navController: NavController,
) {

    fun navigateToMangaDetails(mangaId: String) {
        val action = MainFragmentDirections.actionMainFragmentToMangaDetailsFragment(mangaId)
        navController.navigate(action)
    }

    fun navigateToChapters(mangaId: String) {
        val action =
            MangaDetailsFragmentDirections.actionMangaDetailsFragmentToChaptersFragment(mangaId)
        navController.navigate(action)
    }

    fun navigateToReadManga(chapterId: String) {
        val action = ChaptersFragmentDirections.actionChaptersFragmentToReadMangaFragment(chapterId)
        navController.navigate(action)
    }
}
