package ru.vld43.mangadexapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.vld43.mangadexapp.common.data.models.Result
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.domain.repository.IMangaRepository
import javax.inject.Inject

class GetMangaUseCase @Inject constructor(
    private val mangaRepository: IMangaRepository,
) {

    operator fun invoke(mangaId: String): Flow<Result<MangaDetailsWithCover>> =
        mangaRepository.getManga(mangaId)
}
