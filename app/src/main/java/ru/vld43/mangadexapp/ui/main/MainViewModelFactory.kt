package ru.vld43.mangadexapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_cases.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_cases.SearchMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(
            GetMangaListUseCase::class.java,
            SearchMangaUseCase::class.java,
            AppNavigator::class.java
        ).newInstance(
            getMangaListUseCase,
            searchMangaUseCase,
            appNavigator
        )
}
