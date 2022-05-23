package ru.vld43.mangadexapp.domain.use_cases

import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GetChaptersUseCase @Inject constructor(
    private val mangaRepository: MangaRepository
) {

    operator fun invoke(mangaId: String) = mangaRepository.getPagingChapters(mangaId)
}
