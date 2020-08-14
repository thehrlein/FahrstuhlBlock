
object Dependencies {

    object AndroidX {
        private const val appCompatVersion = "1.3.0-alpha01"
        private const val coreVersion = "1.5.0-alpha01"
        private const val constraintLayoutVersion = "2.0.0-beta7"
        private const val pagingVersion = "3.0.0-alpha02"
        private const val securityVersion = "1.1.0-alpha01"
        private const val swipeRefreshVersion = "1.1.0"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreVersion"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val paging = "androidx.paging:paging-runtime-ktx:$pagingVersion"
        const val security = "androidx.security:security-crypto:$securityVersion"
        const val swipeToRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion"

        object LifeCycle {
            private const val lifecycleVersion = "2.2.0"
            private const val lifecycleLiveDataVersion = "2.3.0-alpha05"
            private const val lifecycleViewModelVersion = "2.3.0-alpha05"

            const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val livedataExtensions =
                "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleLiveDataVersion"
            const val viewModelExtensions =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion"
        }

        object Navigation {
            private const val navigationVersion = "2.3.0"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
            const val ui = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        }

        object Room {
            private const val roomVersion = "2.3.0-alpha01"

            const val runtime = "androidx.room:room-runtime:$roomVersion"
            const val compiler = "androidx.room:room-compiler:$roomVersion"
            const val extensions = "androidx.room:room-ktx:$roomVersion"
        }

        object Test {
            private const val coreTestingVersion = "2.1.0"
            private const val uiAutomatorVersion = "2.2.0"
            private const val rulesVersion = "1.2.0"
            private const val runnerVersion = "1.2.0"
            private const val junitExtensionsVersion = "1.1.1"

            const val core = "androidx.arch.core:core-testing:$coreTestingVersion"
            const val junitExtensions = "androidx.test.ext:junit:$junitExtensionsVersion"

            const val rules = "androidx.test:rules:$rulesVersion"
            const val runner = "androidx.test:runner:$runnerVersion"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:$uiAutomatorVersion"


            object Espresso {
                private const val espressoVersion = "3.2.0"

                const val core = "androidx.test.espresso:espresso-core:$espressoVersion"
                const val contrib = "androidx.test.espresso:espresso-contrib:$espressoVersion"
            }
        }
    }

    object Google {
        private const val gsonVersion = "2.8.6"
        private const val materialVersion = "1.3.0-alpha01"

        const val gson = "com.google.code.gson:gson:$gsonVersion"
        const val material = "com.google.android.material:material:$materialVersion"

        object Firebase {
            private const val analyticsVersion = "17.4.3"
            private const val crashlyticsVersion = "17.1.0"
            private const val messagingVersion = "20.2.1"
            private const val performanceVersion = "19.0.7"

            const val analytics = "com.google.firebase:firebase-analytics-ktx:$analyticsVersion"
            const val crashlytics = "com.google.firebase:firebase-crashlytics:$crashlyticsVersion"
            const val messaging = "com.google.firebase:firebase-messaging:$messagingVersion"
            const val performance = "com.google.firebase:firebase-perf:$performanceVersion"
        }
    }

    object Kotlin {
        const val kotlinVersion = "1.3.72"

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

        object Coroutine {
            private const val coroutineVersion = "1.3.3"

            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
        }
    }

    object Koin {
        private const val koinVersion = "2.1.6"

        const val core = "org.koin:koin-core:$koinVersion"
        const val android = "org.koin:koin-android:$koinVersion"
        const val scope = "org.koin:koin-androidx-scope:$koinVersion"
        const val viewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"
    }

    object Network {
        private const val okHttp3Version = "4.7.2"
        private const val retrofitVersion = "2.9.0"
        private const val mockWebServerVersion = "4.2.2"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okHttp3 = "com.squareup.okhttp3:logging-interceptor:$okHttp3Version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$mockWebServerVersion"

    }

    object Mockito {
        private const val androidVersion = "3.1.0"
        private const val kotlinVersion = "2.1.0"

        const val android = "org.mockito:mockito-android:$androidVersion"
        const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$kotlinVersion"
    }

    object Other {
        private const val coilVersion = "0.11.0"
        private const val debugDbVersion = "1.0.6"
        private const val konfettiVersion= "1.2.2"
        private const val timberVersion = "4.7.1"
        private const val jUnitVersion = "4.13"

        const val coil = "io.coil-kt:coil:$coilVersion"
        const val debugDb = "com.amitshekhar.android:debug-db:$debugDbVersion"
        const val konfetti = "nl.dionsegijn:konfetti:$konfettiVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val junit = "junit:junit:$jUnitVersion"

    }
}

object Classpaths {

    private const val buildToolsVersion = "3.6.1"
    private const val detektVersion = "1.10.0-RC1"
    private const val firebaseCrashlyticsVersion = "2.2.0"
    private const val firebasePerformanceVersion = "1.3.1"
    private const val googleServicesVersion = "4.3.3"
    private const val gradleUpdateVersion = "0.28.0"
    private const val safeArgsVersion = "2.3.0"

    const val androidGradlePlugin = "com.android.tools.build:gradle:$buildToolsVersion"
    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion"
    const val gradleUpdate = "com.github.ben-manes:gradle-versions-plugin:$gradleUpdateVersion"
    const val googleServices = "com.google.gms:google-services:$googleServicesVersion"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsVersion"
    const val firebasePerformance = "com.google.firebase:perf-plugin:$firebasePerformanceVersion"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.kotlinVersion}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgsVersion"
}

object BuildPlugins {

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val gradleUpdater = "com.github.ben-manes.versions"
    const val googleServices = "com.google.gms.google-services"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val firebasePerformance = "com.google.firebase.firebase-perf"
    const val projectDependencyGraph =
        "https://raw.githubusercontent.com/JakeWharton/SdkSearch/master/gradle/projectDependencyGraph.gradle"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val taskTree = "com.dorongold.task-tree"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
}

object AndroidSdkTools {

    const val minSdk = 21
    const val targetSdk = 29
    const val compileSdk = 29
    const val version_code = 2
    const val version_name = "0.9.1"
    const val application_id = "com.tobiapplications.fahrstuhlblock"
}

object Others {

    const val ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val JVM_TARGET = "1.8"
}

object Module {

    object Framework {
        const val repositories = ":fw_repositories"

        object Database {
            const val room = ":fw_database_room"
        }
    }

    object Ui {
        const val common = ":ui_common"
        const val menu = ":ui_menu"
        const val gameSettings = ":ui_game_settings"
        const val block = ":ui_block"
        const val savedGames = ":ui_saved_games"
    }

    object General {
        const val presentation = ":presentation"
        const val interactor = ":interactor"
        const val entities = ":entities"
    }
}