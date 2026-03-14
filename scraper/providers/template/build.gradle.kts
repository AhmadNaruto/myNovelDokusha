plugins {
    id("noveldokusha.android.library")
}

android {
    namespace = "my.noveldokusha.scraper.provider.template"
    
    defaultConfig {
        // Extension metadata - CHANGE THESE for your extension
        buildConfigField("String", "EXTENSION_ID", "\"template\"")
        buildConfigField("String", "EXTENSION_NAME", "\"Template Source\"")
        buildConfigField("String", "EXTENSION_VERSION", "\"1.0.0\"")
        buildConfigField("String", "EXTENSION_BASE_URL", "\"https://example.com\"")
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
