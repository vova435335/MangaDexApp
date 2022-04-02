package ru.vld43.mangadexapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(GetMangaListUseCase::class.java)
            .newInstance(getMangaListUseCase)

}