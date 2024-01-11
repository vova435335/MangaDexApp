package ru.vld43.mangadexapp.domain.use_case

import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class GetMangaUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    suspend operator fun invoke(mangaId: String): Result<MangaDetailsWithCover> =
        mangaRepository.getManga(mangaId)
}
