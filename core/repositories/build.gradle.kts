plugins {
    alias(libs.plugins.ohhell.android.library)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.core.repositories"
}

dependencies {
    // Modules
    implementation(projects.feature.common)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)

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
