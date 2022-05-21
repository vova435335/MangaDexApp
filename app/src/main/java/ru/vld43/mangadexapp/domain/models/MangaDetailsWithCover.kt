package ru.vld43.mangadexapp.domain.models

data class MangaDetailsWithCover(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val status: String,
    val contentRating: String,
    val lastChapter: String?,
    val coverUrl: String
)