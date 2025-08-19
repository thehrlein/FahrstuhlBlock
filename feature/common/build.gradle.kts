plugins {
    alias(libs.plugins.ohhell.android.library)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.feature.common"
}

dependencies {

    // Modules
    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)

    // AndroidX
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Google
    implementation(libs.google.material)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
