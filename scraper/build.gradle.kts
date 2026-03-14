plugins {
    alias(libs.plugins.noveldokusha.android.library)
    alias(libs.plugins.noveldokusha.android.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "my.noveldokusha.scraper"
}

dependencies {
    implementation(projects.strings)
    implementation(projects.core)
    implementation(projects.networking)
    implementation(projects.scraper.api)
    implementation(projects.scraper.extensionManager)

    implementation(libs.androidx.core.ktx)
    implementation(libs.jsoup)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    androidTestImplementation(libs.test.androidx.espresso.core)
}