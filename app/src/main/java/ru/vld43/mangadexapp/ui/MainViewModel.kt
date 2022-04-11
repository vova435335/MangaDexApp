package ru.vld43.mangadexapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_case.SearchMangaUseCase

class MainViewModel(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
) : ViewModel() {

    private val mangaListMutable = MutableLiveData<MangaStateData>()

    val mangaList: LiveData<MangaStateData>
        get() = mangaListMutable

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
                mangaListMutable.postValue(MangaStateData.Success(mangaList))
            })
    }

}