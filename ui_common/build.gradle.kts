plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // AndroidX
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.security)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    implementation(Dependencies.Google.material)


    // Koin
    implementation(Dependencies.Koin.viewModel)

    // Logging
    implementation(Dependencies.Other.timber)
}