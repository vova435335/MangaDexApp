package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapter_pages.ChapterPages

private const val QUALITY_MODE = "data"

object ChapterPagesMapper {

    fun map(chapterPages: ChapterPages): List<String> {
        val baseUrl = chapterPages.baseUrl ?: ""
        val hash = chapterPages.chapter?.hash ?: ""
        val chapterPages = chapterPages.chapter?.imageNames?.map {
            "$baseUrl/$QUALITY_MODE/$hash/$it"
        } ?: emptyList()

        return chapterPages
    }
}
