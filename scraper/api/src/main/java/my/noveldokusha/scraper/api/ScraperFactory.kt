package my.noveldokusha.scraper.api

/**
 * Factory interface for creating scraper instances.
 * 
 * Each extension must provide an implementation of this interface
 * as [ScraperFactoryImpl] in the root package of the extension module.
 * 
 * Example:
 * ```
 * package my.noveldokusha.scraper.novelupdates
 * 
 * class ScraperFactoryImpl : ScraperFactory {
 *     override fun create(): Scraper = NovelUpdatesScraper()
 * }
 * ```
 */
interface ScraperFactory {
    /**
     * Create a new instance of the scraper
     */
    fun create(): Scraper
}
