plugins {
    alias(libs.plugins.ohhell.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.core.databaseroom"
}

dependencies {

    // Modules
    implementation(projects.core.interactor)
    implementation(projects.core.entities)

    // AndroidX
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    // Kotlin
    implementation(libs.kotlin.coroutines)

    // Koin
    implementation(libs.koin)

    // Gson
    implementation(libs.google.gson)

    // Logging
    implementation(libs.timber)
}
