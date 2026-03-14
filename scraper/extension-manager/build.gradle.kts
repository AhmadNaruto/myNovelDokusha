plugins {
    id("noveldokusha.android.library")
}

android {
    namespace = "my.noveldokusha.scraper.extension.manager"
}

dependencies {
    implementation(projects.core)
    api(projects.scraper.api)
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.timber)
}
