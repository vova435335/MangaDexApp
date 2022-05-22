package ru.vld43.mangadexapp.ui.states

sealed class LoadMangaState {

    object Loading : LoadMangaState()

    data class Success<T>(val data: T) : LoadMangaState()

    data class Error(val message: String) : LoadMangaState()
}
