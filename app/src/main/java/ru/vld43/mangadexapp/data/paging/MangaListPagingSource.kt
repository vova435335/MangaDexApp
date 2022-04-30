package ru.vld43.mangadexapp.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import javax.inject.Inject

typealias MangaListPagerLoader = (pageSize: Int, pageIndex: Int) -> Single<List<MangaWithCover>>

class MangaPagingSource @Inject constructor(
    private val loader: MangaListPagerLoader,
    private val pageSize: Int,
) : RxPagingSource<Int, MangaWithCover>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MangaWithCover>> {
        val pageIndex = params.key ?: 0

        return loader(params.loadSize, pageIndex).map {
            LoadResult.Page(
                    data = it,
                    prevKey = if (pageIndex == 0) null else pageIndex - 1,
                    nextKey = if (it.size == params.loadSize)
                        pageIndex + (params.loadSize / pageSize) else null
                )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MangaWithCover>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}