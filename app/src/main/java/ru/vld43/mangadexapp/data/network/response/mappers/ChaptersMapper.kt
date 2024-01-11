package ru.vld43.mangadexapp.data.network.response.mappers

import ru.vld43.mangadexapp.data.network.response.chapters.Chapters
import ru.vld43.mangadexapp.domain.models.Chapter
import javax.inject.Inject

private const val CHAPTER = "Глава"

class ChaptersMapper @Inject constructor() {

    fun map(chapters: Chapters): List<Chapter> {
        val result = chapters.data

        return result?.map {
            val numberChapter = it.chapterAttributes?.chapter ?: ""
            val title = it.chapterAttributes?.title ?: ""

             Chapter(
                id = it.id ?: "",
                title = if (title == "") {
                    "$CHAPTER $numberChapter"
                } else {
                    "$CHAPTER $numberChapter: $title"
                }
            )
        } ?: emptyList()
    }
}
