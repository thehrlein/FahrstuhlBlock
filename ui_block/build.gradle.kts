plugins {
    alias(libs.plugins.ohhell.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.safeargs)
}


android {
    namespace = "com.tobiapplications.fahrstuhlblock.ui_block"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(projects.uiCommon)
    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)

    // AndroidX
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)

    // Other
    implementation(libs.konfetti)

    testImplementation(libs.junit)
}
