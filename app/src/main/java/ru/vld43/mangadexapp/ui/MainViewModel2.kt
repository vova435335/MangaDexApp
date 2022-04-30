package ru.vld43.mangadexapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import io.reactivex.disposables.CompositeDisposable
import ru.vld43.mangadexapp.common.extensions.applySchedulers
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase
import ru.vld43.mangadexapp.domain.use_case.SearchMangaUseCase

class MainViewModel2(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
) : ViewModel() {

    val mangaList: LiveData<PagingData<MangaWithCover>>
        get() = mangaListMutable

    private val mangaListMutable = MutableLiveData<PagingData<MangaWithCover>>()

    private var disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun loadManga() {
        disposables.add(getMangaListUseCase()
            .applySchedulers()
            .subscribe { mangaList ->
                mangaListMutable.postValue(mangaList)
            })
    }

    fun searchManga(title: String) {
        disposables.add(searchMangaUseCase(title)
            .applySchedulers()
            .subscribe { mangaList ->
                if (title.isEmpty()) {
                    loadManga()
                } else {
                    mangaListMutable.postValue(mangaList)
                }
            })
    }
}