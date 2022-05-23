package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapters.ChapterResponse
import ru.vld43.mangadexapp.domain.models.Chapter

private const val UNTITLED = "untitled"

object ChapterMapper {

    fun map(chapter: ChapterResponse): Chapter =
        Chapter(
            id = chapter.id ?: "",
            title = chapter.chapterAttributes?.title ?: UNTITLED,
            numberChapter = chapter.chapterAttributes?.chapter ?: ""
        )
}
