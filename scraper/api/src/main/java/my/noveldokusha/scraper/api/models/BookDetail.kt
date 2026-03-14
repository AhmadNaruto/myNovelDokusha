package my.noveldokusha.scraper.api.models

/**
 * Complete book/novel details
 */
data class BookDetail(
    val title: String,
    val url: String,
    val coverUrl: String? = null,
    val author: String? = null,
    val artist: String? = null,
    val status: String? = null,
    val genres: List<String> = emptyList(),
    val rating: Float? = null,
    val description: String,
    val alternativeTitles: List<String> = emptyList(),
    val year: String? = null,
    val chapters: List<Chapter>,
    val sourceId: String? = null
)
