package my.noveldokusha.scraper.domain

import kotlinx.serialization.Serializable

@Serializable
data class BookResult(
    val title: String,
    val url: String,
    val coverImageUrl: String = "",
    val description: String = "",
)


