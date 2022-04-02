package ru.vld43.mangadexapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.models.Manga
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase
) : ViewModel() {

    private val mangaListStateMutable = MutableLiveData<List<Manga>>()
    val mangaListState: LiveData<List<Manga>> = mangaListStateMutable

    init {
        loadManga()
    }

    private fun loadManga() =
        getMangaListUseCase()
            .applySchedulers()
            .subscribe({
                mangaListStateMutable.value = it
            }, {
                //ToDo
            })


}