package ru.vld43.mangadexapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.vld43.mangadexapp.domain.models.Chapter

typealias ChaptersPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Chapter>

class ChaptersPagingSource(
    private val loader: ChaptersPageLoader,
) : PagingSource<Int, Chapter>() {

    override fun getRefreshKey(state: PagingState<Int, Chapter>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chapter> {
        val pageIndex = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val chapters = loader(pageIndex, pageSize)

            return LoadResult.Page(
                data = chapters,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if(chapters.size < pageSize) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
