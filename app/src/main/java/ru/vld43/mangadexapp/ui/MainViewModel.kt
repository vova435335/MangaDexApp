package ru.vld43.mangadexapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposables
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase
) : ViewModel() {

    private val mangaListMutable = MutableLiveData<MangaStateData>()

    val mangaList: LiveData<MangaStateData>
        get() = mangaListMutable

    private var mangaListDisposable = Disposables.disposed()

    init {
        loadManga()
    }

    override fun onCleared() {
        super.onCleared()
        mangaListDisposable.dispose()
    }

    fun loadManga() {
        mangaListDisposable.dispose()
        mangaListDisposable = getMangaListUseCase()
            .applySchedulers()
            .doOnSubscribe {
                mangaListMutable.value = MangaStateData.Loading
            }
            .doOnError {
                mangaListMutable.value = MangaStateData.Error(it.message.toString())
            }
            .subscribe { mangaList ->
                mangaListMutable.value = MangaStateData.Success(mangaList)
            }
    }
}