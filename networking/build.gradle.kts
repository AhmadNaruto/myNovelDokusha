plugins {
    alias(libs.plugins.noveldokusha.android.library)
    alias(libs.plugins.noveldokusha.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "my.noveldokusha.networking"
}

dependencies {

    implementation(projects.core)

    // Networking
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.okhttp.glideIntegration)
    implementation(libs.brotli4j)

    // Logging
    implementation(libs.timber)

    implementation(libs.jsoup)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}