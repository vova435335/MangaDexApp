package ru.vld43.mangadexapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.data.paging.ChaptersPagingSource
import ru.vld43.mangadexapp.common.domain.use_case.CHAPTERS_PAGE_SIZE
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class GetChaptersUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    operator fun invoke(mangaId: String): Flow<PagingData<Chapter>> =
        Pager(
            config = PagingConfig(
                pageSize = CHAPTERS_PAGE_SIZE,
                initialLoadSize = CHAPTERS_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ChaptersPagingSource(
                    loader = { pageIndex, pageSize ->
                        mangaRepository.getChapters(pageIndex, pageSize, mangaId)
                    }
                )
            }
        ).flow
}
