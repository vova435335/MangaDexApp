package ru.vld43.mangadexapp.domain.models

data class MangaDetails(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val coverId: String = "",
    val tags: List<String> = emptyList(),
    val status: String = "",
    val contentRating: String = "",
    val lastChapter: String = "",
)