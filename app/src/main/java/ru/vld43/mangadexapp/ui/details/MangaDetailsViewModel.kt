package ru.vld43.mangadexapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.use_case.GetMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import ru.vld43.mangadexapp.ui.states.LoadMangaState

class MangaDetailsViewModel(
    private val getMangaUseCase: GetMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val mangaState: StateFlow<LoadMangaState>
        get() = mutableMangaState

    private val mutableMangaState = MutableStateFlow<LoadMangaState>(LoadMangaState.Loading)

    fun loadManga(mangaId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMangaUseCase(mangaId)
                .collect {
                    when (it) {
                        is Result.Success -> mutableMangaState.emit(LoadMangaState.Success(it.data))
                        is Result.Error -> mutableMangaState.emit(LoadMangaState.Error(it.error))
                    }
                }
        }
    }
}
