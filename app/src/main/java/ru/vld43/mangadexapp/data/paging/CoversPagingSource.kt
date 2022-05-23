package ru.vld43.mangadexapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.vld43.mangadexapp.domain.models.Chapter
import java.lang.Exception

typealias ChapterPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Chapter>

class CoversPagingSource(
    private val loader: ChapterPageLoader,
) : PagingSource<Int, Chapter>() {

    override fun getRefreshKey(state: PagingState<Int, Chapter>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chapter> {
        val pageIndex = params.key ?: 0

        return try {
            val chapters = loader.invoke(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = chapters,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (chapters.size == params.loadSize) {
                    pageIndex + 1
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
