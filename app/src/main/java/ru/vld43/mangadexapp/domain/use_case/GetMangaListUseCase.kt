package ru.vld43.mangadexapp.domain.use_case

import ru.vld43.mangadexapp.domain.repository.MangaRepository

class GetMangaListUseCase(private val mangaRepository: MangaRepository) {

    fun execute() = mangaRepository.getMangaList()
}