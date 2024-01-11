package ru.vld43.mangadexapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.vld43.mangadexapp.common.data.ApiError
import ru.vld43.mangadexapp.common.data.ApiException
import ru.vld43.mangadexapp.common.data.ApiResult
import ru.vld43.mangadexapp.common.data.ApiSuccess
import ru.vld43.mangadexapp.domain.models.MangaWithCover

class MangaPagingSource(
    private val loader: suspend (pageIndex: Int, pageSize: Int) -> ApiResult<List<MangaWithCover>>,
) : PagingSource<Int, MangaWithCover>() {

    override fun getRefreshKey(state: PagingState<Int, MangaWithCover>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaWithCover> {
        val pageIndex = params.key ?: 0
        val pageSize = params.loadSize

        return when (val mangaList = loader(pageIndex, pageSize)) {
            is ApiSuccess -> {
                LoadResult.Page(
                    data = mangaList.data,
                    prevKey = if (pageIndex == 0) null else pageIndex - 1,
                    nextKey = if (mangaList.data.size < pageSize) null else pageIndex + 1
                )
            }

            is ApiError -> {
                // todo обработать по кодам оштбки
                LoadResult.Error(RuntimeException())
            }

            is ApiException -> {
                LoadResult.Error(mangaList.e)
            }
        }
    }
}
