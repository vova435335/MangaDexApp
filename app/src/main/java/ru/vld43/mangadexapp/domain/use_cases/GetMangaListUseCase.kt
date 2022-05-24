package ru.vld43.mangadexapp.domain.use_cases

import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    suspend operator fun invoke() = mangaRepository.getPagingMangaList()
}
