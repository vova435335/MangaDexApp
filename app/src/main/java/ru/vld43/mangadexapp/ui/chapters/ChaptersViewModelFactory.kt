package ru.vld43.mangadexapp.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.domain.use_cases.GrtChaptersUseCase
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import javax.inject.Inject

class ChaptersViewModelFactory @Inject constructor(
    private val getChaptersUseCase: GrtChaptersUseCase,
    private val appNavigator: AppNavigator,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(
            AppNavigator::class.java
        ).newInstance(
            getChaptersUseCase,
            appNavigator
        )
}
