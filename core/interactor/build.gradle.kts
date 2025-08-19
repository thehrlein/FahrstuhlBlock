plugins {
    alias(libs.plugins.ohhell.android.library) }

android {
    namespace = "com.tobiapplications.fahrstuhlblock.core.interactor"
}

dependencies {

    // Modules
    implementation(projects.core.entities)

    // Logging
    implementation(libs.timber)
}
