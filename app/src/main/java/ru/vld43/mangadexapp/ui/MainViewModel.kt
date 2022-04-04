package ru.vld43.mangadexapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase
) : ViewModel() {

    private val mangaListStateMutable = MutableLiveData<List<MangaWithCover>>()
    val mangaListState: LiveData<List<MangaWithCover>> = mangaListStateMutable

    init {
        loadManga()
    }

    private fun loadManga() =
        getMangaListUseCase()
            .applySchedulers()
            .subscribe({ mangaList ->
                mangaListStateMutable.value = mangaList
            }, {
                Log.d("MainViewModel", "loadManga: ${it.message}")
            })
}