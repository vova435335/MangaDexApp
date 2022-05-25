package ru.vld43.mangadexapp.ui.manga_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_cases.GetMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import javax.inject.Inject

class MangaDetailsViewModelFactory @Inject constructor(
    private val getMangaUseCase: GetMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(
            GetMangaUseCase::class.java,
            AppNavigator::class.java
        ).newInstance(
            getMangaUseCase,
            appNavigator
        )
}
