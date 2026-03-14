plugins {
    alias(libs.plugins.noveldokusha.android.library)
    alias(libs.plugins.noveldokusha.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "my.noveldoksuha.data"
}

dependencies {
    implementation(projects.core)
    implementation(projects.networking)
    implementation(projects.scraper)
    implementation(projects.tooling.localDatabase)
    implementation(projects.tooling.epubParser)

    implementation(libs.jsoup)
    implementation(libs.readability4j)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.timber)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.test.junit)
}