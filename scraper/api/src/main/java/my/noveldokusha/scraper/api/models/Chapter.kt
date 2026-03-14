package my.noveldokusha.scraper.api.models

/**
 * Chapter information
 */
data class Chapter(
    val title: String,
    val url: String,
    val chapterNumber: Int? = null,
    val releaseDate: String? = null,
    val sourceId: String? = null
)
