package my.noveldokusha.scraper.api.models

import kotlinx.serialization.Serializable

/**
 * Book/novel search result item
 */
data class BookResult(
    val title: String,
    val url: String,
    val coverUrl: String? = null,
    val author: String? = null,
    val status: String? = null,
    val genres: List<String> = emptyList(),
    val rating: Float? = null,
    val description: String? = null,
    val latestChapter: String? = null,
    val latestChapterUrl: String? = null,
    val sourceId: String? = null
)
