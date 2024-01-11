package ru.vld43.mangadexapp.ui.read_manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.ApiError
import ru.vld43.mangadexapp.common.data.ApiException
import ru.vld43.mangadexapp.common.data.ApiSuccess
import ru.vld43.mangadexapp.domain.use_case.GetChapterPagesUseCase
import ru.vld43.mangadexapp.ui.states.LoadState

class ReadMangaViewModel(
    private val getChapterPagesUseCase: GetChapterPagesUseCase,
) : ViewModel() {

    val chapterPagesState: StateFlow<LoadState<List<String>>>
        get() = _chapterPagesState

    private val _chapterPagesState =
        MutableStateFlow<LoadState<List<String>>>(LoadState.Loading())

    fun loadChapterPages(mangaId: String) {
        viewModelScope.launch {
            _chapterPagesState.emit(LoadState.Loading())
            _chapterPagesState.emit(
                when (val result = getChapterPagesUseCase(mangaId)) {
                    is ApiSuccess -> LoadState.Success(result.data)
                    is ApiError -> LoadState.Error("${result.code ?: ""} ${result.message ?: ""}")
                    is ApiException -> LoadState.Error("Unknown error")
                }
            )
        }
    }
}
