package ru.vld43.mangadexapp.ui.read_manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.domain.use_cases.GetChapterPagesUseCase

class ReadMangaViewModel(
    private val getChapterPagesUseCase: GetChapterPagesUseCase,
) : ViewModel() {

    val chapterPagesState: StateFlow<PagingData<String>>
        get() = mutableChapterPagesState

    private val mutableChapterPagesState = MutableStateFlow<PagingData<String>>(PagingData.empty())

    fun loadChapterPages(mangaId: String) {
        viewModelScope.launch {
            getChapterPagesUseCase(mangaId)
                .cachedIn(viewModelScope)
                .collect(mutableChapterPagesState::emit)
        }
    }
}
