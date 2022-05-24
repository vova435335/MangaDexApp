package ru.vld43.mangadexapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

typealias ChapterPagesPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<String>

class ChapterPagesPagingSource(
    private val loader: ChapterPagesPageLoader,
) : PagingSource<Int, String>() {

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val pageIndex = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val pages = loader(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = pages,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (pages.size < pageSize) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
