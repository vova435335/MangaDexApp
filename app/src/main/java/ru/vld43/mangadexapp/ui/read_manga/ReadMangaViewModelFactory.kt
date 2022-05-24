package ru.vld43.mangadexapp.ui.read_manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_cases.GetChapterPagesUseCase
import javax.inject.Inject

class ReadMangaViewModelFactory @Inject constructor(
    private val getChapterPagesUseCase: GetChapterPagesUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(
            GetChapterPagesUseCase::class.java
        ).newInstance(
            getChapterPagesUseCase
        )
}
