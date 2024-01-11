package ru.vld43.mangadexapp.ui.manga_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.ApiError
import ru.vld43.mangadexapp.common.data.ApiException
import ru.vld43.mangadexapp.common.data.ApiSuccess
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.use_case.GetMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import ru.vld43.mangadexapp.ui.states.LoadState

class MangaDetailsViewModel(
    private val getMangaUseCase: GetMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val mangaState: StateFlow<LoadState<MangaDetailsWithCover>>
        get() = _mangaState

    private val _mangaState =
        MutableStateFlow<LoadState<MangaDetailsWithCover>>(LoadState.Loading())

    fun loadManga(mangaId: String) {
        viewModelScope.launch {
            _mangaState.emit(LoadState.Loading())
            _mangaState.emit(
                when (val result = getMangaUseCase(mangaId)) {
                    is ApiSuccess -> LoadState.Success(result.data)
                    is ApiError -> LoadState.Error("${result.code ?: ""} ${result.message ?: ""}")
                    is ApiException -> LoadState.Error("Unknown error")
                }
            )
        }
    }

    fun openChapters(mangaId: String) {
        appNavigator.navigateToChapters(mangaId)
    }
}
