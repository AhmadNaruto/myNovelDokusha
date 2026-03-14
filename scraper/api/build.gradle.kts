plugins {
    id("noveldokusha.android.library")
}

android {
    namespace = "my.noveldokusha.scraper.api"
}

dependencies {
    // Core dependencies - keep minimal to avoid circular deps
    implementation(projects.core)
    
    // Export these to extensions
    api(libs.jsoup)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.serialization.json)
    
    // Networking
    api(libs.okhttp)
}
