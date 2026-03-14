package my.noveldokusha.scraper.provider.royalroad

import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.models.BookDetail
import my.noveldokusha.scraper.api.models.BookResult
import my.noveldokusha.scraper.api.models.Chapter
import my.noveldokusha.scraper.api.utils.createScraperHttpClient
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Royal Road Scraper Extension
 * 
 * Source: https://www.royalroad.com
 * 
 * Implements scraping for Royal Road web novel platform.
 * Supports search, popular novels, latest updates, and chapter content extraction.
 */
class RoyalRoadScraper : Scraper {

    override val id = BuildConfig.EXTENSION_ID
    override val name = BuildConfig.EXTENSION_NAME
    override val baseUrl = BuildConfig.EXTENSION_BASE_URL
    override val language = BuildConfig.EXTENSION_LANGUAGE
    override val version = BuildConfig.EXTENSION_VERSION
    override val hasCloudflare = false

    private val client: OkHttpClient = createScraperHttpClient(
        userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    )

    override suspend fun search(query: String, page: Int): List<BookResult> {
        val url = "$baseUrl/fictions/search?keyword=$query&page=$page"
        return searchFromUrl(url)
    }

    override suspend fun getPopular(page: Int): List<BookResult> {
        val url = "$baseUrl/fictions/popular?page=$page"
        return searchFromUrl(url)
    }

    override suspend fun getLatestUpdates(page: Int): List<BookResult> {
        val url = "$baseUrl/fictions/latest-updates?page=$page"
        return searchFromUrl(url)
    }

    private suspend fun searchFromUrl(url: String): List<BookResult> {
        return try {
            val response = client.newCall(
                okhttp3.Request.Builder()
                    .url(url)
                    .build()
            ).execute()
            
            val document = Jsoup.parse(response.body?.string() ?: "")
            document.select(".media-list .media").map { element ->
                val titleElement = element.selectFirst(".media-heading a")
                val coverElement = element.selectFirst("img[src]")
                val authorElement = element.selectFirst(".media-heading small")
                
                BookResult(
                    title = titleElement?.text() ?: "",
                    url = titleElement?.attr("abs:href") ?: "",
                    coverUrl = coverElement?.attr("abs:src"),
                    author = authorElement?.text()?.trim(),
                    sourceId = id
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getBookDetails(url: String): BookDetail {
        val document = fetchDocument(url)
        
        val title = document.selectFirst(".fic-title h1")?.text() ?: ""
        val author = document.selectFirst(".fic-title h4")?.text()?.trim()
        val description = document.selectFirst(".description")?.text() ?: ""
        val coverUrl = document.selectFirst(".cover-art img[src]")?.attr("abs:src")
        val genres = document.select(".tags a").map { it.text() }
        val status = document.selectFirst(".fic-status")?.text()?.trim()
        
        val chapters = document.select(".chapter-row").map { element ->
            val chapterTitle = element.selectFirst("a")?.text() ?: ""
            val chapterUrl = element.selectFirst("a")?.attr("abs:href") ?: ""
            val releaseDate = element.selectFirst("small")?.text()?.trim()
            
            Chapter(
                title = chapterTitle,
                url = chapterUrl,
                releaseDate = releaseDate,
                sourceId = id
            )
        }
        
        return BookDetail(
            title = title,
            url = url,
            coverUrl = coverUrl,
            author = author,
            status = status,
            genres = genres,
            description = description,
            chapters = chapters,
            sourceId = id
        )
    }

    override suspend fun getChapterContent(url: String): String {
        val document = fetchDocument(url)
        
        // Remove hidden content (CSS display:none)
        val hiddenClasses = extractHiddenClasses(document)
        
        val chapterContent = document.selectFirst(".chapter-content")?.let { content ->
            // Remove scripts, ads, and hidden elements
            content.select("script").remove()
            content.select("a").remove()
            content.select(".ads-title").remove()
            content.select(".hidden").remove()
            
            // Remove hidden classes
            hiddenClasses.forEach { hc ->
                content.select(hc).remove()
            }
            
            // Extract text
            extractChapterText(content)
        } ?: ""
        
        return chapterContent
    }

    /**
     * Extract CSS classes that have display:none
     */
    private fun extractHiddenClasses(document: Document): Set<String> {
        val cssPattern = Regex("(?<class>\\.\\w+)\\s*\\{.*display: none;.*\\}", RegexOption.DOT_MATCHES_ALL)
        return document.select("style")
            .flatMap { style ->
                cssPattern.findAll(style.data())
                    .mapNotNull { match -> match.groups["class"]?.value }
            }
            .toSet()
    }

    /**
     * Extract clean text from chapter content
     */
    private fun extractChapterText(element: org.jsoup.nodes.Element): String {
        val paragraphs = element.select("p")
        return paragraphs.map { it.text().trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n\n")
    }

    /**
     * Helper function to fetch and parse document
     */
    private suspend fun fetchDocument(url: String): Document {
        val response = client.newCall(
            okhttp3.Request.Builder()
                .url(url)
                .build()
        ).execute()
        return Jsoup.parse(response.body?.string() ?: "")
    }

    override suspend fun getGenres(): List<String> {
        // Royal Road doesn't have a simple genres list page
        // This would require scraping the search/filter page
        return emptyList()
    }
}
