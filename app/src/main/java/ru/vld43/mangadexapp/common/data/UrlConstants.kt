package ru.vld43.mangadexapp.common.data

object UrlConstants {

    const val BASE_URL = "https://api.mangadex.org"
    const val COVER_ART_URL = "https://uploads.mangadex.org/covers"

    const val GET_MANGA_LIST = "/manga"
    const val GET_MANGA = "/manga/{id}"

    const val QUERY_LIMIT_KEY = "limit"
    const val QUERY_OFFSET_KEY = "offset"

    const val GET_COVER_ART = "/cover/{id}"

    const val SEARCH_MANGA = "/manga"
    const val QUERY_SEARCH_KEY_PARAMETER = "title"

    const val GET_CHAPTERS = "/chapter"
    const val QUERY_CHAPTERS_MANGA_ID_KEY = "manga"
    const val QUERY_CHAPTERS_LANGUAGE_KEY = "translatedLanguage[]"

    const val GET_CHAPTER_PAGES = "/at-home/server/{id}"
}
