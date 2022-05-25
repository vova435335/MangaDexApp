package ru.vld43.mangadexapp.domain.use_cases

import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GetChapterPagesUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    operator fun invoke(chapterId: String): Flow<Result<List<String>>> =
        mangaRepository.getChapterPages(chapterId)
}
