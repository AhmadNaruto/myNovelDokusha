# NovelDokusha Scraper Extensions

This directory contains the scraper extensions system for NovelDokusha, allowing modular source implementations similar to Tachiyomi/Mihon extensions.

## Structure

```
scraper/
├── api/                    # Common interface (shared between app and extensions)
│   └── src/main/java/my/noveldokusha/scraper/api/
│       ├── Scraper.kt              # Main scraper interface
│       ├── ScraperFactory.kt       # Factory interface for creating scrapers
│       ├── models/                 # Data models
│       └── utils/                  # Utility functions
├── extension-manager/      # Loads and manages extensions at runtime
└── providers/              # Individual scraper implementations
    ├── template/           # Template for creating new extensions
    └── ...                 # Other extensions
```

## Creating a New Extension

### 1. Copy the Template

```bash
cp -r scraper/providers/template scraper/providers/yoursource
```

### 2. Update build.gradle.kts

Edit `scraper/providers/yoursource/build.gradle.kts`:

```kotlin
android {
    namespace = "my.noveldokusha.scraper.provider.yoursource"
    
    defaultConfig {
        buildConfigField("String", "EXTENSION_ID", "\"yoursource\"")
        buildConfigField("String", "EXTENSION_NAME", "\"Your Source Name\"")
        buildConfigField("String", "EXTENSION_VERSION", "\"1.0.0\"")
        buildConfigField("String", "EXTENSION_BASE_URL", "\"https://yoursite.com\"")
        buildConfigField("String", "EXTENSION_LANGUAGE", "\"en\"")
    }
}
```

### 3. Implement Your Scraper

Edit `Scraper.kt` in your extension directory:

```kotlin
class YourSourceScraper : Scraper {
    override val id = BuildConfig.EXTENSION_ID
    override val name = BuildConfig.EXTENSION_NAME
    override val baseUrl = BuildConfig.EXTENSION_BASE_URL
    override val language = BuildConfig.EXTENSION_LANGUAGE
    override val version = BuildConfig.EXTENSION_VERSION
    
    private val client: OkHttpClient = createScraperHttpClient()
    
    override suspend fun search(query: String, page: Int): List<BookResult> {
        // Implement search logic
    }
    
    override suspend fun getBookDetails(url: String): BookDetail {
        // Implement details logic
    }
    
    override suspend fun getChapterContent(url: String): String {
        // Implement content logic
    }
    
    // ... implement other methods
}
```

### 4. Update AndroidManifest.xml

```xml
<manifest>
    <application>
        <meta-data
            android:name="my.noveldokusha.scraper.extension.id"
            android:value="yoursource" />
        <meta-data
            android:name="my.noveldokusha.scraper.extension.name"
            android:value="Your Source Name" />
    </application>
</manifest>
```

### 5. Build Your Extension

```bash
./gradlew :scraper:providers:yoursource:assembleDebug
```

## Extension API Reference

### Required Methods

| Method | Description |
|--------|-------------|
| `search(query, page)` | Search for novels |
| `getPopular(page)` | Get popular/trending novels |
| `getLatestUpdates(page)` | Get recently updated novels |
| `getBookDetails(url)` | Get novel details and chapters |
| `getChapterContent(url)` | Get chapter content |

### Optional Methods

| Method | Description |
|--------|-------------|
| `getGenres()` | Get available genres |
| `searchWithFilters(...)` | Search with genre/status filters |

### Helper Functions

- `createScraperHttpClient()` - Creates pre-configured OkHttpClient
- `getChapterContentWithRetry()` - Auto-retry failed chapter loads

## Extension Guidelines

1. **Unique ID**: Each extension must have a unique `id`
2. **Version Format**: Use SemVer (e.g., "1.0.0")
3. **Error Handling**: Handle network errors gracefully
4. **Rate Limiting**: Respect sourceer's rate limits
5. **User Agent**: Use the provided `createScraperHttpClient()` for proper UA

## Testing Extensions

### Unit Tests

```kotlin
@Test
fun testSearch() = runTest {
    val scraper = YourSourceScraper()
    val results = scraper.search("test", 1)
    assertTrue(results.isNotEmpty())
}
```

### Integration Tests

Install the extension APK on a device and verify:
- Extension loads in ExtensionManager
- Search returns results
- Chapter content loads correctly

## Publishing Extensions

Extensions can be distributed as separate APK files. Users can install them like regular apps.

### Signing

Sign extensions with the same key as the main app for seamless integration.

### Repository

Create a GitHub repo for extensions:
```
yourusername/noveldokusha-extensions
├── all/
│   └── my.noveldokusha.scraper.provider.yoursource.apk
└── repo.json
```

## Troubleshooting

### Extension Not Loading

1. Check package name starts with `my.noveldokusha.scraper.provider.`
2. Verify `ScraperFactoryImpl` class exists in root package
3. Check logcat for error messages

### ClassCastException

Ensure you're using `compileOnly` for `projects.scraper.api` dependency

### Network Errors

- Use `createScraperHttpClient()` for proper configuration
- Check if source uses Cloudflare (set `hasCloudflare = true`)
- Verify base URL is correct

## Examples

See existing extensions in `scraper/providers/` for reference implementations.

## License

Extensions should follow the same license as NovelDokusha.
