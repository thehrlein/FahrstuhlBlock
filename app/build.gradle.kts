plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.googleServices)
    id(BuildPlugins.firebaseCrashlytics)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.Ui.common))
    implementation(project(Module.Ui.menu))
    implementation(project(Module.Ui.gameSettings))
    implementation(project(Module.Ui.block))
    implementation(project(Module.Framework.repositories))
    implementation(project(Module.Framework.Database.room))
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // Google
    implementation(Dependencies.Google.material)
    implementation(Dependencies.Google.Firebase.analytics)
    implementation(Dependencies.Google.Firebase.crashlytics)

    // AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.LifeCycle.extensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    // Kotlin
    implementation(Dependencies.Kotlin.kotlin)
    implementation(Dependencies.Kotlin.Coroutine.core)

    // Koin (Dependency Injection)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.scope)
    implementation(Dependencies.Koin.viewModel)

    // Network
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.gsonConverter)
    implementation(Dependencies.Network.okHttp3)

    // Logging
    implementation(Dependencies.Other.timber)

    // Images
    implementation(Dependencies.Other.coil)

    debugImplementation(Dependencies.Other.debugDb)

}
