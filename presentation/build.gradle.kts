plugins {
    alias(libs.plugins.ohhell.android.library)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.presentation"
}

dependencies {

    // Modules
    implementation(projects.interactor)
    implementation(projects.entities)

    // AndroidX
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Google
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
