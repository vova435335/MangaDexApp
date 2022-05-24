package ru.vld43.mangadexapp.ui.read_manga

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.use_cases.GetChapterPagesUseCase
import ru.vld43.mangadexapp.ui.states.LoadState

class ReadMangaViewModel(
    private val getChapterPagesUseCase: GetChapterPagesUseCase,
) : ViewModel() {

    val chapterPagesState: StateFlow<LoadState<List<String>>>
        get() = mutableChapterPagesState

    private val mutableChapterPagesState =
        MutableStateFlow<LoadState<List<String>>>(LoadState.Loading())

    fun loadChapterPages(mangaId: String) {
        viewModelScope.launch {
            getChapterPagesUseCase(mangaId)
                .collect {
                    when (it) {
                        is Result.Success -> {
                            mutableChapterPagesState.emit(LoadState.Success(it.data))
                        }
                        is Result.Error -> mutableChapterPagesState.emit(LoadState.Error(it.error))
                    }
                }
        }
    }
}
