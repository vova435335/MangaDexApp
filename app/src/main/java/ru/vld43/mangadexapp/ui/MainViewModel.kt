package ru.vld43.mangadexapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_case.SearchMangaUseCase

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
) : ViewModel() {

    val mangaList: LiveData<MangaStateData>
        get() = mangaListMutable

    private val mangaListMutable = MutableLiveData<MangaStateData>()

    private var disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun loadManga() {
        disposables.add(getMangaListUseCase()
            .applySchedulers()
            .doOnSubscribe {
                mangaListMutable.postValue(MangaStateData.Loading)
            }
            .doOnError {
                mangaListMutable.postValue(MangaStateData.Error("${it.message}"))
            }
            .subscribe { mangaList ->
                mangaListMutable.postValue(MangaStateData.Success(mangaList))
            })
    }

    fun searchManga(title: String) {
        disposables.add(searchMangaUseCase(title)
            .applySchedulers()
            .doOnSubscribe {
                mangaListMutable.postValue(MangaStateData.Loading)
            }
            .doOnError {
                mangaListMutable.postValue(MangaStateData.Error("${it.message}"))
            }
            .subscribe { mangaList ->
                if (title.isEmpty()) {
                    loadManga()
                } else {
                    mangaListMutable.postValue(MangaStateData.Success(mangaList))
                }
            })
    }
}