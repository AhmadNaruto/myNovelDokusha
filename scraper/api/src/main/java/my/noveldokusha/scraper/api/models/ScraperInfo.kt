package my.noveldokusha.scraper.api.models

import kotlinx.serialization.Serializable

/**
 * Scraper metadata/info
 */
data class ScraperInfo(
    val id: String,
    val name: String,
    val baseUrl: String,
    val language: String,
    val version: String,
    val hasCloudflare: Boolean = false
)
