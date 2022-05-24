package ru.vld43.mangadexapp.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GetChapterPagesUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    operator fun invoke(chapterId: String): Flow<PagingData<String>> =
        mangaRepository.getPagingChapterPages(chapterId)
}
