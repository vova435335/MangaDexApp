package ru.vld43.mangadexapp.domain.use_cases

import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GrtChaptersUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    operator fun invoke(mangaId: String) = repository.getPagingChapters(mangaId)
}
