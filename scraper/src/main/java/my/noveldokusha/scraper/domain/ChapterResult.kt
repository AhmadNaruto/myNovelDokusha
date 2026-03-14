package my.noveldokusha.scraper.domain

/**
 * Chapter result from scraper
 * @param title Chapter title
 * @param url Chapter URL
 */
data class ChapterResult(
    val title: String,
    val url: String
)