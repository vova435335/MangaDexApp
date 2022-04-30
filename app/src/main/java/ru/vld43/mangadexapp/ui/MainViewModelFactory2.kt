package ru.vld43.mangadexapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_case.SearchMangaUseCase
import javax.inject.Inject

class MainViewModelFactory2 @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(
            GetMangaListUseCase::class.java,
            SearchMangaUseCase::class.java)
            .newInstance(
                getMangaListUseCase,
                searchMangaUseCase
            )

}