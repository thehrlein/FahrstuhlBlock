plugins {
    alias(libs.plugins.ohhell.android.library)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.ui_menu"
}

dependencies {

    // Modules
    implementation(projects.uiCommon)
    implementation(projects.presentation)
    implementation(projects.interactor)
    implementation(projects.entities)

    // AndroidX
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
