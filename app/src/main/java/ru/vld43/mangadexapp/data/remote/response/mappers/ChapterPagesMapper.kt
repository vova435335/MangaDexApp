package ru.vld43.mangadexapp.data.remote.response.mappers

import ru.vld43.mangadexapp.data.remote.response.chapter_pages.ChapterPagesResponse

private const val QUALITY_MODE = "data"

object ChapterPagesMapper {

    fun map(chapterPagesResponse: ChapterPagesResponse): List<String> {
        val baseUrl = chapterPagesResponse.baseUrl ?: ""
        val hash = chapterPagesResponse.chapter?.hash ?: ""
        val chapterPages = chapterPagesResponse.chapter?.imageNames?.map {
            "$baseUrl/$QUALITY_MODE/$hash/$it"
        } ?: emptyList()

        return chapterPages
    }
}
