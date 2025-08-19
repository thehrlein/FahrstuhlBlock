plugins {
    alias(libs.plugins.ohhell.android.library) }

android {
    namespace = "com.tobiapplications.fahrstuhlblock.interactor"
}

dependencies {

    // Modules
    implementation(projects.entities)

    // Logging
    implementation(libs.timber)
}
