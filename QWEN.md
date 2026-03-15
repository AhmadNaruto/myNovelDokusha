# NovelDokusha - Project Context

## Project Overview

**NovelDokusha** is an Android web novel reader application focused on simplicity and reading immersion. It allows users to search from a large catalog of content, open novels, and enjoy them with advanced reading features.

### Key Features
- **Multiple Sources**: Chinese (69书吧, UU看书, 顶点小说, 乐阅读, Twkan) and English novel sources
- **Live Translation**: ML Kit-based on-device translation
- **Advanced Reader**: Infinite scroll, custom fonts/sizes, live translation
- **Text-to-Speech**: Background playback, adjustable voice/pitch/speed
- **Local Source**: Read local EPUB files
- **Backup & Restore**: Easy data backup functionality
- **Material 3 Design**: Modern UI with light/dark themes
- **Automatic Cloudflare Bypass**: Seamless access to protected sources

## Tech Stack

### Core Technologies
- **Language**: Kotlin
- **UI**: XML Views + Jetpack Compose (hybrid approach)
- **Architecture**: Multi-module Gradle project with convention plugins
- **Dependency Injection**: Hilt
- **Database**: Room (SQLite)
- **Networking**: OkHttp, Retrofit
- **Image Loading**: Coil, Glide
- **Serialization**: Gson, Moshi, Kotlinx Serialization

### Key Libraries
- Jsoup (HTML parsing)
- Kotlin Coroutines & Flow
- LiveData, ViewModel (Architecture Components)
- Google MLKit (translation)
- Android TTS & Media APIs
- Readability4j, Crux (text extraction)

## Project Structure

```
myNovelDokusha/
├── app/                          # Main application module
├── build-logic/                  # Custom Gradle convention plugins
│   └── convention/               # Kotlin DSL build conventions
├── core/                         # Core utilities and base classes
├── coreui/                       # Shared UI components
├── data/                         # Data layer (repositories, models)
├── features/                     # Feature modules
│   ├── reader/                   # Novel reader feature
│   ├── chaptersList/             # Chapter listing
│   ├── libraryExplorer/          # Library management
│   ├── settings/                 # App settings
│   ├── sourceExplorer/           # Source browsing
│   ├── catalogExplorer/          # Catalog browsing
│   ├── databaseExplorer/         # Database search
│   ├── globalSourceSearch/       # Global search
│   └── webview/                  # WebView integration
├── navigation/                   # Navigation graph
├── networking/                   # Network configuration
├── scraper/                      # Web scrapers for novel sources
├── strings/                      # String resources
├── tooling/                      # Utility modules
│   ├── local_database/           # Local database handling
│   ├── epub_parser/              # EPUB parsing
│   ├── epub_importer/            # EPUB import functionality
│   ├── text_translator/          # Translation feature
│   ├── text_to_speech/           # TTS functionality
│   ├── backup_create/            # Backup creation
│   ├── backup_restore/           # Backup restoration
│   ├── application_workers/      # WorkManager jobs
│   ├── local_source/             # Local file source
│   └── algorithms/               # Utility algorithms
└── gradle/
    └── libs.versions.toml        # Version catalog
```

## Building and Running

### Prerequisites
- JDK 17
- Android SDK (API 34+)
- Gradle 8.2+

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd myNovelDokusha
   ```

2. **Configure signing (optional, for release builds)**
   Create `local.properties` in the project root:
   ```properties
   storeFile=/path/to/keystore.jks
   storePassword=your_store_password
   keyAlias=your_key_alias
   keyPassword=your_key_password
   ```

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing config)
./gradlew assembleRelease -PlocalPropertiesFilePath=local.properties

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build

# Build specific flavor
./gradlew assembleFullDebug    # Full version (with ML Kit translation)
./gradlew assembleFossDebug    # FOSS version (no proprietary deps)
```

### Product Flavors
- **full**: Includes all features (ML Kit translation)
- **foss**: Open-source only dependencies (translation disabled)

### Custom Build Properties
```bash
# Split APK by ABI
./gradlew assembleRelease -PsplitByAbi=true -PsplitByAbiDoUniversal=true
```

## Development Conventions

### Code Style
- **Kotlin**: Official Kotlin code style (`kotlin.code.style=official`)
- **Architecture**: MVVM with Repository pattern
- **DI**: Hilt for dependency injection across all modules

### Module Conventions
- Custom Gradle convention plugins enforce consistent configuration:
  - `noveldokusha.android.application` - Application modules
  - `noveldokusha.android.library` - Library modules
  - `noveldokusha.android.compose` - Compose-enabled modules

### Best Practices
- ViewBinding enabled for XML layouts
- Jetpack Compose for modern UI components
- Coroutines for async operations
- LiveData/StateFlow for UI state
- Timber for logging
- Room for local persistence

### Testing
- Unit tests: JUnit, Mockito-Kotlin
- UI tests: Espresso, Compose UI Testing
- Test orchestrator enabled for instrumented tests

## CI/CD

GitHub Actions workflows:
- **buildRelease.yml**: Automated release builds with signing
- **ui_test.yml**: UI test automation

## Key Configuration Files

| File | Purpose |
|------|---------|
| `build.gradle.kts` | Root build configuration |
| `settings.gradle.kts` | Project modules and dependency resolution |
| `gradle/libs.versions.toml` | Centralized version catalog |
| `gradle.properties` | Gradle and Android configuration |
| `build-logic/convention/` | Custom Gradle plugins |

## Notable Implementation Details

- **Configuration Cache**: Enabled with warnings allowed
- **JVM Args**: `-Xmx3g` for Gradle daemon
- **Target SDK**: Configured via convention plugins
- **ProGuard**: Custom rules in `app/proguard-rules.pro`
- **Signing**: Supports custom signing config via properties file
