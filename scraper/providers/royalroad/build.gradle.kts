plugins {
    id("noveldokusha.android.library")
}

android {
    namespace = "my.noveldokusha.scraper.provider.royalroad"
    
    defaultConfig {
        // Extension metadata
        buildConfigField("String", "EXTENSION_ID", "\"royalroad\"")
        buildConfigField("String", "EXTENSION_NAME", "\"Royal Road\"")
        buildConfigField("String", "EXTENSION_VERSION", "\"1.0.0\"")
        buildConfigField("String", "EXTENSION_BASE_URL", "\"https://www.royalroad.com\"")
        buildConfigField("String", "EXTENSION_LANGUAGE", "\"en\"")
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    // IMPORTANT: Use compileOnly for scraper API to avoid bundling
    compileOnly(projects.scraper.api)
    
    implementation(libs.jsoup)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
}
