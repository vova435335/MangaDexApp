package ru.vld43.mangadexapp.ui

import androidx.paging.PagingData
import ru.vld43.mangadexapp.domain.models.MangaWithCover

sealed class MangaStateData {

    object Loading : MangaStateData()

    class Success(val data: PagingData<MangaWithCover>) : MangaStateData()

    class Error(val message: String) : MangaStateData()


}
