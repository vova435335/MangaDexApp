package ru.vld43.mangadexapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val mangaListState: StateFlow<PagingData<MangaWithCover>>
        get() = mutableMangaListState

    private val mutableMangaListState = MutableStateFlow<PagingData<MangaWithCover>>(PagingData.empty())

    fun loadManga() {
        viewModelScope.launch {
            getMangaListUseCase()
                .cachedIn(viewModelScope)
                .collect(mutableMangaListState::emit)
        }
    }

    fun searchManga(title: String) {
        viewModelScope.launch {
            searchMangaUseCase(title)
                .cachedIn(viewModelScope)
                .collect(mutableMangaListState::emit)
        }
    }

    fun openDetails(mangaWithCover: MangaWithCover) {
        appNavigator.navigateToMangaDetails(mangaWithCover)
    }
}