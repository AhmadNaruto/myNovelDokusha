# 📖 Panduan Lengkap Membuat Extension NovelDokusha

Panduan langkah demi langkah untuk membuat scraper extension baru seperti Tachiyomi/Mihon.

---

## 📋 Prerequisites

Sebelum memulai, pastikan Anda memiliki:

- ✅ Android Studio / IntelliJ IDEA
- ✅ JDK 17+
- ✅ NovelDokusha project sudah di-clone
- ✅ Branch `feature/modular-scraper-extensions` sudah di-checkout

---

## 🚀 Langkah 1: Persiapan Module Extension

### 1.1 Copy Template Extension

```bash
# Dari root project
cd scraper/providers

# Copy template (pilih salah satu)
cp -r template myextension
# ATAU copy dari RoyalRoad (lebih lengkap)
cp -r royalroad myextension
```

### 1.2 Rename Package Directory

```bash
cd myextension/src/main/java/my/noveldokusha/scraper/provider
mv template myextension  # Jika copy dari template
# ATAU
mv royalroad myextension  # Jika copy dari RoyalRoad
```

---

## ⚙️ Langkah 2: Konfigurasi Build

### 2.1 Edit `build.gradle.kts`

Buka file `scraper/providers/myextension/build.gradle.kts`:

```kotlin
plugins {
    id("noveldokusha.android.library")
}

android {
    namespace = "my.noveldokusha.scraper.provider.myextension"  // ← UBAH
    
    defaultConfig {
        // ← UBAH SEMUA FIELD INI
        buildConfigField("String", "EXTENSION_ID", "\"myextension\"")
        buildConfigField("String", "EXTENSION_NAME", "\"My Extension\"")
        buildConfigField("String", "EXTENSION_VERSION", "\"1.0.0\"")
        buildConfigField("String", "EXTENSION_BASE_URL", "\"https://mysite.com\"")
        buildConfigField("String", "EXTENSION_LANGUAGE", "\"id\"")  // "id", "en", dll
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    compileOnly(projects.scraper.api)
    implementation(libs.jsoup)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
}
```

### 2.2 Edit `AndroidManifest.xml`

Buka file `scraper/providers/myextension/src/main/AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <application>
        <!-- UBAH nilai android:value sesuai extension Anda -->
        <meta-data
            android:name="my.noveldokusha.scraper.extension.id"
            android:value="myextension" />
        <meta-data
            android:name="my.noveldokusha.scraper.extension.name"
            android:value="My Extension" />
        <meta-data
            android:name="my.noveldokusha.scraper.extension.version"
            android:value="1.0.0" />
    </application>
</manifest>
```

### 2.3 Update `settings.gradle.kts`

Di root project, buka `settings.gradle.kts` dan tambahkan:

```kotlin
include(":scraper:providers:myextension")
```

---

## 💻 Langkah 3: Implementasi Scraper

### 3.1 Rename Class Files

```bash
cd scraper/providers/myextension/src/main/java/my/noveldokusha/scraper/provider/myextension

# Rename file (jika dari template)
mv TemplateScraper.kt MyExtensionScraper.kt
# ATAU jika dari RoyalRoad
mv RoyalRoadScraper.kt MyExtensionScraper.kt
```

### 3.2 Edit Package Declaration

Buka `MyExtensionScraper.kt` dan update package:

```kotlin
package my.noveldokusha.scraper.provider.myextension  // ← UBAH

import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.models.*
import my.noveldokusha.scraper.api.utils.createScraperHttpClient
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
```

### 3.3 Implementasi Scraper Lengkap

```kotlin
class MyExtensionScraper : Scraper {

    override val id = BuildConfig.EXTENSION_ID
    override val name = BuildConfig.EXTENSION_NAME
    override val baseUrl = BuildConfig.EXTENSION_BASE_URL
    override val language = BuildConfig.EXTENSION_LANGUAGE
    override val version = BuildConfig.EXTENSION_VERSION
    override val hasCloudflare = false  // Set true jika site pakai Cloudflare

    private val client: OkHttpClient = createScraperHttpClient()

    // ───────────────────────────────────────────────────────
    // 1. SEARCH - Implementasi pencarian novel
    // ───────────────────────────────────────────────────────
    override suspend fun search(query: String, page: Int): List<BookResult> {
        val url = "$baseUrl/search?q=$query&page=$page"
        
        val response = client.newCall(
            okhttp3.Request.Builder().url(url).build()
        ).execute()
        
        val document = Jsoup.parse(response.body?.string() ?: "")
        
        // SESUAIKAN CSS SELECTOR DENGAN WEBSITE TARGET
        return document.select(".novel-item").map { element ->
            BookResult(
                title = element.select(".title").text(),
                url = element.select("a").attr("abs:href"),
                coverUrl = element.select("img").attr("abs:src"),
                author = element.select(".author").text(),
                sourceId = id
            )
        }
    }

    // ───────────────────────────────────────────────────────
    // 2. POPULAR - Novel populer
    // ───────────────────────────────────────────────────────
    override suspend fun getPopular(page: Int): List<BookResult> {
        val url = "$baseUrl/popular?page=$page"
        return searchFromUrl(url)  // Gunakan helper function
    }

    // ───────────────────────────────────────────────────────
    // 3. LATEST UPDATES - Novel terbaru update
    // ───────────────────────────────────────────────────────
    override suspend fun getLatestUpdates(page: Int): List<BookResult> {
        val url = "$baseUrl/latest?page=$page"
        return searchFromUrl(url)
    }

    // Helper function
    private suspend fun searchFromUrl(url: String): List<BookResult> {
        val response = client.newCall(
            okhttp3.Request.Builder().url(url).build()
        ).execute()
        
        val document = Jsoup.parse(response.body?.string() ?: "")
        
        return document.select(".novel-list .novel").map { element ->
            BookResult(
                title = element.select(".novel-title a").text(),
                url = element.select(".novel-title a").attr("abs:href"),
                coverUrl = element.select("img").attr("abs:src"),
                latestChapter = element.select(".latest-chapter").text(),
                sourceId = id
            )
        }
    }

    // ───────────────────────────────────────────────────────
    // 4. BOOK DETAILS - Detail novel + list chapters
    // ───────────────────────────────────────────────────────
    override suspend fun getBookDetails(url: String): BookDetail {
        val document = fetchDocument(url)
        
        val title = document.selectFirst(".book-title")?.text() ?: ""
        val author = document.selectFirst(".book-author")?.text()
        val coverUrl = document.selectFirst(".book-cover img")?.attr("abs:src")
        val description = document.selectFirst(".book-description")?.text() ?: ""
        val genres = document.select(".book-genres a").map { it.text() }
        val status = document.selectFirst(".book-status")?.text()
        
        // Parse chapter list
        val chapters = document.select(".chapter-list li").map { element ->
            Chapter(
                title = element.select("a").text(),
                url = element.select("a").attr("abs:href"),
                releaseDate = element.select(".date").text(),
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

    // ───────────────────────────────────────────────────────
    // 5. CHAPTER CONTENT - Isi chapter
    // ───────────────────────────────────────────────────────
    override suspend fun getChapterContent(url: String): String {
        val document = fetchDocument(url)
        
        val chapterContent = document.selectFirst(".chapter-content")?.let { content ->
            // Hapus elemen yang tidak diinginkan
            content.select("script").remove()
            content.select("style").remove()
            content.select(".ads").remove()
            content.select(".advertisement").remove()
            
            // Extract paragraph text
            content.select("p").joinToString("\n\n") { it.text().trim() }
                .filter { it.isNotBlank() }
        } ?: ""
        
        return chapterContent
    }

    // ───────────────────────────────────────────────────────
    // 6. GENRES (Optional) - Daftar genre
    // ───────────────────────────────────────────────────────
    override suspend fun getGenres(): List<String> {
        // Optional: Implement jika source punya halaman genre list
        return emptyList()
    }

    // ───────────────────────────────────────────────────────
    // HELPER FUNCTIONS
    // ───────────────────────────────────────────────────────
    
    /**
     * Helper untuk fetch dan parse document
     */
    private suspend fun fetchDocument(url: String): Document {
        val response = client.newCall(
            okhttp3.Request.Builder().url(url).build()
        ).execute()
        return Jsoup.parse(response.body?.string() ?: "")
    }
}
```

### 3.4 Update Factory Class

Buka `ScraperFactoryImpl.kt`:

```kotlin
package my.noveldokusha.scraper.provider.myextension  // ← UBAH

import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.ScraperFactory

class ScraperFactoryImpl : ScraperFactory {
    override fun create(): Scraper = MyExtensionScraper()  // ← UBAH
}
```

---

## 🔍 Langkah 4: Analisis Website Target

### 4.1 Inspect Element Website

1. Buka website target di browser
2. Klik kanan → Inspect Element
3. Catat CSS selector untuk:
   - Search results (`.novel-item`, `.search-result`, dll)
   - Novel title (`.title a`, `.novel-name`, dll)
   - Chapter list (`.chapter-list li`, `.chapter-item`, dll)
   - Chapter content (`.chapter-content`, `.text-content`, dll)

### 4.2 Test CSS Selector di Browser Console

```javascript
// Test di browser console
document.querySelectorAll('.novel-item').length
document.querySelector('.chapter-content').innerText
```

### 4.3 Handle Dynamic Content

Jika website menggunakan JavaScript:

```kotlin
// Option 1: Gunakan API tersembunyi (jika ada)
val apiUrl = "$baseUrl/api/novels?page=$page"

// Option 2: Tunggu beberapa saat
kotlinx.coroutines.delay(1000)

// Option 3: Gunakan WebView (lebih kompleks)
```

### 4.4 Handle Cloudflare

Jika website pakai Cloudflare:

```kotlin
override val hasCloudflare = true

// Gunakan Cloudflare bypass library
// Lihat: https://github.com/iamharshtrivedi/cloudflare-bypass
```

---

## 🏗️ Langkah 5: Build dan Test

### 5.1 Build Extension

```bash
# Debug build
./gradlew :scraper:providers:myextension:assembleDebug

# Release build
./gradlew :scraper:providers:myextension:assembleRelease
```

### 5.2 Output APK

APK akan tersimpan di:
```
scraper/providers/myextension/build/outputs/apk/
├── debug/myextension-debug.apk
└── release/myextension-release.apk
```

### 5.3 Install di Device

```bash
# Via ADB
adb install scraper/providers/myextension/build/outputs/apk/release/myextension-release.apk

# ATAU copy manual ke device dan install
```

### 5.4 Test di App

1. Buka NovelDokusha app
2. Extension akan auto-load
3. Cek di Settings → Extensions
4. Test search, browse, dan read

---

## 🐛 Langkah 6: Debugging

### 6.1 Extension Tidak Load

Cek logcat:
```bash
adb logcat | grep ExtensionManager
```

Kemungkinan masalah:
- ❌ Package name tidak dimulai dengan `my.noveldokusha.scraper.provider.`
- ❌ `ScraperFactoryImpl` tidak ada di root package
- ❌ Class tidak implement `ScraperFactory` interface

### 6.2 Search Tidak Berfungsi

Tambahkan logging:
```kotlin
override suspend fun search(query: String, page: Int): List<BookResult> {
    val url = "$baseUrl/search?q=$query&page=$page"
    android.util.Log.d("MyExtension", "Searching: $url")
    
    // ... rest of code
    
    android.util.Log.d("MyExtension", "Found ${results.size} results")
    return results
}
```

### 6.3 CSS Selector Salah

Test selector satu per satu:
```kotlin
val element = document.selectFirst(".novel-item")
android.util.Log.d("MyExtension", "Element found: ${element != null}")
android.util.Log.d("MyExtension", "HTML: ${element?.html()}")
```

---

## 📤 Langkah 7: Distribusi Extension

### 7.1 Build Release APK

```bash
./gradlew :scraper:providers:myextension:assembleRelease
```

### 7.2 Sign APK (Optional)

Untuk production, sign dengan keystore:

```kotlin
// scraper/providers/myextension/build.gradle.kts
android {
    signingConfigs {
        create("release") {
            storeFile = file("../../keystore.jks")
            storePassword = "your_password"
            keyAlias = "your_alias"
            keyPassword = "your_password"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 7.3 Upload ke GitHub

```bash
# Buat repo baru: my-noveldokusha-extensions
# Upload APK ke Releases
```

### 7.4 Buat Extension Repository

Buat file `repo.json`:

```json
{
  "extensions": [
    {
      "id": "myextension",
      "name": "My Extension",
      "version": "1.0.0",
      "url": "https://github.com/username/my-noveldokusha-extensions/releases/download/v1.0.0/myextension.apk",
      "checksum": "sha256_checksum_here"
    }
  ]
}
```

---

## 📚 Best Practices

### ✅ DO

- ✅ Gunakan `createScraperHttpClient()` untuk HTTP client
- ✅ Handle error dengan try-catch
- ✅ Berikan user agent yang proper
- ✅ Cache response jika memungkinkan
- ✅ Test di berbagai kondisi network
- ✅ Ikuti rate limit website target
- ✅ Hapus iklan dan promotional content

### ❌ DON'T

- ❌ Hardcode URL tanpa base URL
- ❌ Ignore error handling
- ❌ Scraping terlalu cepat (rate limit!)
- ❌ Include iklan dalam content
- ❌ Lupa update version number

---

## 🎯 Template Cepat

Untuk membuat extension baru dengan cepat, copy template ini:

```kotlin
package my.noveldokusha.scraper.provider.myextension

import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.models.*
import my.noveldokusha.scraper.api.utils.createScraperHttpClient
import okhttp3.OkHttpClient
import org.jsoup.Jsoup

class MyExtensionScraper : Scraper {
    override val id = BuildConfig.EXTENSION_ID
    override val name = BuildConfig.EXTENSION_NAME
    override val baseUrl = BuildConfig.EXTENSION_BASE_URL
    override val language = BuildConfig.EXTENSION_LANGUAGE
    override val version = BuildConfig.EXTENSION_VERSION
    override val hasCloudflare = false

    private val client: OkHttpClient = createScraperHttpClient()

    override suspend fun search(query: String, page: Int): List<BookResult> {
        TODO("Implement search")
    }

    override suspend fun getPopular(page: Int): List<BookResult> {
        TODO("Implement popular")
    }

    override suspend fun getLatestUpdates(page: Int): List<BookResult> {
        TODO("Implement latest updates")
    }

    override suspend fun getBookDetails(url: String): BookDetail {
        TODO("Implement book details")
    }

    override suspend fun getChapterContent(url: String): String {
        TODO("Implement chapter content")
    }
}
```

---

## 🔗 Referensi

- [Scraper API Documentation](../api/src/main/java/my/noveldokusha/scraper/api/)
- [RoyalRoad Example](../providers/royalroad/)
- [Template Extension](../providers/template/)
- [Jsoup Documentation](https://jsoup.org/apidocs/)
- [OkHttp Documentation](https://square.github.io/okhttp/)

---

## 📞 Support

Jika ada masalah:
1. Cek logcat untuk error messages
2. Test CSS selector di browser
3. Bandingkan dengan RoyalRoad extension
4. Buka issue di GitHub

---

**Happy Coding! 🎉**
