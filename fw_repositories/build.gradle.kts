plugins {
    alias(libs.plugins.ohhell.android.library)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.fw_repositories"
}

dependencies {
    // Modules
    implementation(projects.uiCommon)
    implementation(projects.interactor)
    implementation(projects.entities)

    // Google
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)

    // Kotlin
    implementation(libs.kotlin.coroutines)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
