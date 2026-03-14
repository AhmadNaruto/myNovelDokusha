package my.noveldokusha.scraper.sources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.contentOrNull
import my.noveldokusha.core.LanguageCode
import my.noveldokusha.core.PagedList
import my.noveldokusha.core.Response
import my.noveldokusha.network.NetworkClient
import my.noveldokusha.network.add
import my.noveldokusha.network.toDocument
import my.noveldokusha.network.toUrlBuilderSafe
import my.noveldokusha.network.tryConnect
import my.noveldokusha.scraper.R
import my.noveldokusha.scraper.SourceInterface
import my.noveldokusha.scraper.TextExtractor
import my.noveldokusha.scraper.domain.BookResult
import my.noveldokusha.scraper.domain.ChapterResult
import java.io.StringReader

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

// Cloudfare blocked
class Saikai(
    private val networkClient: NetworkClient
) : SourceInterface.Catalog {
    override val id = "seikai"
    override val nameStrId = R.string.source_name_saikai
    override val baseUrl = "https://saikaiscan.com.br/"
    override val catalogUrl = "https://saikaiscan.com.br/series"
    override val language = LanguageCode.PORTUGUESE

    override suspend fun getBookCoverImageUrl(
        bookUrl: String
    ): Response<String?> = withContext(Dispatchers.Default) {
        tryConnect {
            networkClient.get(bookUrl).toDocument()
                .selectFirst(".story-header img[src]")
                ?.attr("src")
        }
    }

    override suspend fun getBookDescription(
        bookUrl: String
    ): Response<String?> = withContext(Dispatchers.Default) {
        tryConnect {
            networkClient.get(bookUrl).toDocument()
                .selectFirst("#synopsis-content")
                ?.let { TextExtractor.get(it) }
        }
    }

    override suspend fun getChapterList(
        bookUrl: String
    ): Response<List<ChapterResult>> = withContext(Dispatchers.Default) {
        tryConnect {
            val url = bookUrl
                .toUrlBuilderSafe()
                .add("tab", "capitulos")
                .toString()

            val chaptersDoc = networkClient.get(url).toDocument()
            val firstChapterData = chaptersDoc
                .selectFirst("ul.__chapters li")!!

            val fullChapterUrl = firstChapterData.selectFirst("a")?.attr("href")!!
            val chapterTitle: String = firstChapterData.selectFirst(".__chapters--title")!!.text()
            val (bookpath, dispUrl, chapterUrl) = Regex("""^.*/(.+)/(\d+)/(.*)$""").find(
                fullChapterUrl
            )!!.destructured
            val firstChapter = ChapterResult(
                url = chapterUrl,
                title = chapterTitle
            )

            val initialVal = dispUrl.toInt()

            val preList = let {
                val script =
                    chaptersDoc.select("script").find { it.data().startsWith("window.__NUXT__") }!!
                val head = "{return "
                val start = "{layout:"
                start + script.data().split(head + start).last()
            }.let { input ->
                json.parseToJsonElement(StringReader(input).readText()).jsonObject
            }["data"]
                ?.jsonArray?.getOrNull(0)
                ?.jsonObject?.get("story")
                ?.jsonObject?.get("data")
                ?.jsonObject?.get("separators")
                ?.jsonArray?.flatMap { volume ->
                    volume.jsonObject["releases"]
                        ?.jsonArray?.map { it.jsonObject }
                        ?.mapNotNull { jsonElement ->
                            val chapterNum = jsonElement["chapter"]?.jsonPrimitive?.contentOrNull?.toIntOrNull()
                                ?: return@mapNotNull null
                            val slug = jsonElement["slug"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
                            val title = jsonElement["title"]?.jsonPrimitive?.contentOrNull ?: ""
                            ChapterResult(
                                url = slug,
                                title = title
                            )
                        } ?: emptyList()
                } ?: emptyList()

            (listOf(firstChapter) + preList).mapIndexed { index: Int, chapter: ChapterResult ->
                ChapterResult(
                    title = chapter.title,
                    url = "https://saikaiscan.com.br/ler/series/$bookpath/${initialVal + index}/${chapter.url}"
                )
            }
        }
    }

    override suspend fun getCatalogList(
        index: Int
    ): Response<PagedList<BookResult>> = withContext(Dispatchers.Default) {
        getPageBooks(index = index)
    }

    override suspend fun getCatalogSearch(
        index: Int,
        input: String
    ): Response<PagedList<BookResult>> = withContext(Dispatchers.Default) {
        if (input.isBlank()) {
            return@withContext Response.Success(PagedList.createEmpty(index = index))
        }
        getPageBooks(index = index, input = input)
    }

    private suspend fun getPageBooks(
        index: Int,
        input: String = ""
    ): Response<PagedList<BookResult>> = withContext(Dispatchers.Default) {
        tryConnect {
            val page = index + 1
            val url = """https://api.saikai.com.br/api/stories"""
                .toUrlBuilderSafe()
                .add(
                    "format" to "1",
                    "q" to input,
                    "status" to "null",
                    "genres" to "",
                    "country" to "null",
                    "sortProperty" to "title",
                    "sortDirection" to "asc",
                    "page" to "$page",
                    "per_page" to "12",
                    "relationships" to "language,type,format",
                )
                .toString()

            val jsonElement = networkClient.get(url).body.string()
                .let { json.parseToJsonElement(it) }

            jsonElement.jsonObject["data"]
                ?.jsonArray
                ?.map { it.jsonObject }
                ?.map { obj ->
                    BookResult(
                        title = obj["title"]?.jsonPrimitive?.contentOrNull ?: "",
                        url = "https://saikaiscan.com.br/series/${obj["slug"]?.jsonPrimitive?.contentOrNull ?: ""}",
                        coverImageUrl = "https://s3-alpha.saikai.com.br/${obj["image"]?.jsonPrimitive?.contentOrNull ?: ""}"
                    )
                }?.let {
                    PagedList(
                        list = it,
                        index = index,
                        isLastPage = false
                    )
                } ?: PagedList.createEmpty(index = index)
        }
    }
}
