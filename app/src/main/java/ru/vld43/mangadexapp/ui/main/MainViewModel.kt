package ru.vld43.mangadexapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_case.SearchMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val mangaList: LiveData<PagingData<MangaWithCover>>
        get() = mangaListMutable

    private val mangaListMutable = MutableLiveData<PagingData<MangaWithCover>>()

    fun loadManga() {
        viewModelScope.launch {
            getMangaListUseCase()
                .cachedIn(viewModelScope)
                .collect { mangaListMutable.postValue(it) }
        }
    }

    fun searchManga(title: String) {
        viewModelScope.launch {
            searchMangaUseCase(title)
                .cachedIn(viewModelScope)
                .collect { mangaListMutable.postValue(it) }
        }
    }

    fun openDetails(mangaWithCover: MangaWithCover) {
        appNavigator.navigateToMangaDetails(mangaWithCover)
    }
}