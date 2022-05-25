package ru.vld43.mangadexapp.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.domain.models.MangaWithCover
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    operator fun invoke(title: String): Flow<PagingData<MangaWithCover>> =
        mangaRepository.searchPagingManga(title)
}