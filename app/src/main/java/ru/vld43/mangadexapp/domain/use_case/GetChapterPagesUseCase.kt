package ru.vld43.mangadexapp.domain.use_case

import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class GetChapterPagesUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    suspend operator fun invoke(chapterId: String): Result<List<String>> =
        mangaRepository.getChapterPages(chapterId)
}
