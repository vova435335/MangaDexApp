package ru.vld43.mangadexapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.use_cases.GetMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import ru.vld43.mangadexapp.ui.states.LoadState

class MangaDetailsViewModel(
    private val getMangaUseCase: GetMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val mangaState: StateFlow<LoadState<MangaDetailsWithCover>>
        get() = mutableMangaState

    private val mutableMangaState =
        MutableStateFlow<LoadState<MangaDetailsWithCover>>(LoadState.Loading())

    fun loadManga(mangaId: String) {
        viewModelScope.launch {
            getMangaUseCase(mangaId)
                .collect {
                    when (it) {
                        is Result.Success -> mutableMangaState.emit(LoadState.Success(it.data))
                        is Result.Error -> mutableMangaState.emit(LoadState.Error(it.error))
                    }
                }
        }
    }

    fun openChapters(mangaId: String) {
        appNavigator.navigateToChapters(mangaId)
    }
}
