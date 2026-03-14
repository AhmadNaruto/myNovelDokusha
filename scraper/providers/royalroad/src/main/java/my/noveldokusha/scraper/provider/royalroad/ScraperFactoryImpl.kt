package my.noveldokusha.scraper.provider.royalroad

import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.ScraperFactory

/**
 * Factory implementation for Royal Road scraper.
 * 
 * This class is required for the ExtensionManager to load your extension.
 * The class MUST be named `ScraperFactoryImpl` and be in the root package of your extension.
 */
class ScraperFactoryImpl : ScraperFactory {
    override fun create(): Scraper = RoyalRoadScraper()
}
