package ru.vld43.mangadexapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaWithCover(
    val manga: Manga,
    val coverUrl: String
) : Parcelable
