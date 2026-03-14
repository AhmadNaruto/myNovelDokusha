package my.noveldokusha.scraper.api

import my.noveldokusha.scraper.api.models.BookDetail
import my.noveldokusha.scraper.api.models.BookResult
import my.noveldokusha.scraper.api.models.Chapter
import my.noveldokusha.scraper.api.models.ScraperInfo

/**
 * Base interface for all novel scrapers/extensions.
 * 
 * Each scraper implementation must provide these methods to be compatible with NovelDokusha.
 * 
 * @property id Unique identifier for this scraper (e.g., "novelupdates", "royalroad")
 * @property name Display name shown to users
 * @property baseUrl Base URL of the source website
 * @property language Language code (e.g., "en", "id", "zh")
 * @property version Extension version in SemVer format
 * @property hasCloudflare Whether this source uses Cloudflare protection
 */
interface Scraper {
    val id: String
    val name: String
    val baseUrl: String
    val language: String
    val version: String
    val hasCloudflare: Boolean get() = false
    
    /**
     * Get scraper info/metadata
     */
    val info: ScraperInfo
        get() = ScraperInfo(
            id = id,
            name = name,
            baseUrl = baseUrl,
            language = language,
            version = version,
            hasCloudflare = hasCloudflare
        )
    
    /**
     * Search for novels by query
     * @param query Search query string
     * @param page Page number for pagination (starting from 1)
     * @return List of search results
     */
    suspend fun search(query: String, page: Int = 1): List<BookResult>
    
    /**
     * Get popular/trending novels
     * @param page Page number for pagination
     * @return List of popular novels
     */
    suspend fun getPopular(page: Int = 1): List<BookResult>
    
    /**
     * Get latest updates
     * @param page Page number for pagination
     * @return List of recently updated novels
     */
    suspend fun getLatestUpdates(page: Int = 1): List<BookResult>
    
    /**
     * Get novel details including chapters list
     * @param url URL to the novel page
     * @return Novel details with chapters
     */
    suspend fun getBookDetails(url: String): BookDetail
    
    /**
     * Get chapter content
     * @param url URL to the chapter page
     * @return Chapter content as HTML or plain text
     */
    suspend fun getChapterContent(url: String): String
    
    /**
     * Get chapter content with auto-retry for failed requests
     * @param url URL to the chapter page
     * @param maxRetries Maximum number of retry attempts
     * @return Chapter content
     */
    suspend fun getChapterContentWithRetry(url: String, maxRetries: Int = 3): String {
        var lastException: Exception? = null
        
        repeat(maxRetries) { attempt ->
            try {
                return getChapterContent(url)
            } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries - 1) {
                    kotlinx.coroutines.delay(1000L * (attempt + 1))
                }
            }
        }
        
        throw lastException ?: Exception("Unknown error")
    }
    
    /**
     * Get genres available on this source
     * @return List of genre names
     */
    suspend fun getGenres(): List<String> = emptyList()
    
    /**
     * Search with filters/genres
     * @param query Search query
     * @param genres List of genre filters
     * @param status Filter by status (ongoing, completed, etc.)
     * @param page Page number
     * @return Filtered search results
     */
    suspend fun searchWithFilters(
        query: String = "",
        genres: List<String> = emptyList(),
        status: String? = null,
        page: Int = 1
    ): List<BookResult> {
        // Default implementation falls back to basic search
        return search(query, page)
    }
}
