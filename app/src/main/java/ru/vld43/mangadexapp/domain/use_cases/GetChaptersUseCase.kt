package ru.vld43.mangadexapp.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.domain.models.Chapter
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GetChaptersUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    operator fun invoke(mangaId: String): Flow<PagingData<Chapter>> =
        mangaRepository.getPagingChapters(mangaId)
}
