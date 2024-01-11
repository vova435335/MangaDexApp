package ru.vld43.mangadexapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.data.paging.MangaPagingSource
import ru.vld43.mangadexapp.domain.common.use_case.MANGA_PAGE_SIZE
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    operator fun invoke(): Flow<PagingData<MangaWithCover>> =
        Pager(
            config = PagingConfig(
                pageSize = MANGA_PAGE_SIZE,
                initialLoadSize = MANGA_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaPagingSource(
                    loader = { pageIndex, pageSize ->
                        mangaRepository.getMangaList(pageIndex, pageSize)
                    }
                )
            }
        ).flow
}

