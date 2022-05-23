package ru.vld43.mangadexapp.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.use_cases.GetChaptersUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator

class ChaptersViewModel(
    private val getChaptersUseCase: GetChaptersUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val chaptersState: StateFlow<PagingData<Chapter>>
        get() = mutableChaptersState

    private val mutableChaptersState = MutableStateFlow<PagingData<Chapter>>(PagingData.empty())

    fun loadChapters(mangaId: String) {
        viewModelScope.launch {
            getChaptersUseCase(mangaId)
                .cachedIn(viewModelScope)
                .collect(mutableChaptersState::emit)
        }
    }
}
