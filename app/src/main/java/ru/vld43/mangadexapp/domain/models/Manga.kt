package ru.vld43.mangadexapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manga(
    val id: String,
    val title: String,
    val coverId: String,
) : Parcelable