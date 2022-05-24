package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapters.ChapterResponse
import ru.vld43.mangadexapp.domain.models.Chapter

private const val UNTITLED = "Без названия"
private const val CHAPTER = "Глава"

object ChapterMapper {

    fun map(chapter: ChapterResponse): Chapter {
        val numberChapter = chapter.chapterAttributes?.chapter ?: ""
        val title = chapter.chapterAttributes?.title ?: UNTITLED

        return Chapter(
            id = chapter.id ?: "",
            title = "$CHAPTER $numberChapter: $title"
        )
    }
}
