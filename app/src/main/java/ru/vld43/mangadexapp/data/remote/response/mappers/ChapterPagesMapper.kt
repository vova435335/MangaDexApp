package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapter_pages.ChapterPages
import javax.inject.Inject

private const val QUALITY_MODE = "data"

class ChapterPagesMapper @Inject constructor() {

    fun map(chapterPages: ChapterPages): List<String> {
        val baseUrl = chapterPages.baseUrl ?: ""
        val hash = chapterPages.chapter?.hash ?: ""
        val result = chapterPages.chapter?.imageNames?.map {
            "$baseUrl/$QUALITY_MODE/$hash/$it"
        } ?: emptyList()

        return result
    }
}
