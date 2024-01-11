package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapters.Chapters
import ru.vld43.mangadexapp.domain.models.Chapter

private const val CHAPTER = "Глава"

object ChaptersMapper {

    fun map(chapters: Chapters): List<Chapter> {
        val chapters = chapters.data

        return chapters?.map {
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
