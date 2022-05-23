package ru.vld43.mangadexapp.data.remote.response.mappers

import android.util.Log
import ru.vld43.mangadexapp.data.remote.response.chapters.ChapterResponse
import ru.vld43.mangadexapp.domain.models.Chapter

private const val UNTITLED = "untitled"

object ChapterMapper {

    fun map(chapter: ChapterResponse): Chapter {
        val numberChapter = chapter.chapterAttributes?.chapter ?: ""
        val title = chapter.chapterAttributes?.title ?: UNTITLED

        return Chapter(
            id = chapter.id ?: "",
            title = "$title $numberChapter"
        )
    }
}
