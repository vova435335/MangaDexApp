package ru.vld43.mangadexapp.domain.use_case

import ru.vld43.mangadexapp.domain.repository.MangaRepository
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val mangaRepository: MangaRepository,
) {

    operator fun invoke(title: String) = mangaRepository.searchManga(title)
}