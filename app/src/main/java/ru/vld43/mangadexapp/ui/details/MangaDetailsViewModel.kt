package ru.vld43.mangadexapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.use_case.GetMangaUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator

class MangaDetailsViewModel(
    private val getMangaUseCase: GetMangaUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val mangaState: StateFlow<MangaDetailsWithCover>
        get() = mutableMangaState

    private val mutableMangaState = MutableStateFlow(
        MangaDetailsWithCover()
    )

    fun loadManga(mangaId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMangaUseCase(mangaId)
                .collect {
                    when (it) {
                        is Result.Success -> mutableMangaState.emit(it.data)
                        is Result.Error -> {  }
                    }
                }
        }
    }
}
