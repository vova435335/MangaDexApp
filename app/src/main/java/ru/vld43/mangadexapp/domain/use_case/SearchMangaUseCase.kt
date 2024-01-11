package ru.vld43.mangadexapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.data.paging.MangaPagingSource
import ru.vld43.mangadexapp.common.domain.use_case.MANGA_PAGE_SIZE
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    operator fun invoke(title: String): Flow<PagingData<MangaWithCover>> =
        Pager(
            config = PagingConfig(
                pageSize = MANGA_PAGE_SIZE,
                initialLoadSize = MANGA_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaPagingSource(
                    loader = { pageIndex, pageSize ->
                        mangaRepository.searchManga(
                            title = title,
                            pageIndex = pageIndex,
                            pageSize = pageSize
                        )
                    }
                )
            }
        ).flow
}
