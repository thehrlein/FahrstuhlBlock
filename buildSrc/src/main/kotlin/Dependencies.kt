
object Dependencies {

    object AndroidX {
        private const val appCompatVersion = "1.6.1"
        private const val coreVersion = "1.10.1"
        private const val constraintLayoutVersion = "2.1.4"
        private const val securityVersion = "1.1.0-alpha02"
        private const val swipeRefreshVersion = "1.1.0"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreVersion"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val security = "androidx.security:security-crypto:$securityVersion"
        const val swipeToRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion"

        object LifeCycle {
            private const val lifecycleVersion = "2.6.1"

            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val livedataExtensions =
                "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModelExtensions =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        }

        object Navigation {
            // Do not update to 2.7.0+ if not targeting api 34
            private const val navigationVersion = "2.6.0"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
            const val ui = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        }

        object Room {
            private const val roomVersion = "2.5.2"

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
        private const val gsonVersion = "2.10.1"
        private const val materialVersion = "1.9.0"

        const val gson = "com.google.code.gson:gson:$gsonVersion"
        const val material = "com.google.android.material:material:$materialVersion"

        object Firebase {
            private const val firebaseVersion = "32.2.2"

            const val bom = "com.google.firebase:firebase-bom:$firebaseVersion"
            const val analytics = "com.google.firebase:firebase-analytics-ktx"
            const val crashlytics = "com.google.firebase:firebase-crashlytics"
            const val messaging = "com.google.firebase:firebase-messaging"
            const val performance = "com.google.firebase:firebase-perf"
        }
    }

    object Kotlin {
        const val kotlinVersion = "1.8.10"

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

        object Coroutine {
            private const val coroutineVersion = "1.7.3"

            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
        }
    }

    object Koin {
        private const val koinVersion = "3.4.3"

        const val android = "io.insert-koin:koin-android:$koinVersion"
    }

    object Network {
        private const val okHttp3Version = "4.9.3"
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
        private const val coilVersion = "2.4.0"
        private const val konfettiVersion = "1.3.2"
        private const val timberVersion = "5.0.1"
        private const val jUnitVersion = "4.13.2"

        const val coil = "io.coil-kt:coil:$coilVersion"
        const val konfetti = "nl.dionsegijn:konfetti:$konfettiVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val junit = "junit:junit:$jUnitVersion"
    }
}

object Classpaths {

    private const val buildToolsVersion = "8.0.0"
    private const val firebaseCrashlyticsVersion = "2.2.0"
    private const val firebasePerformanceVersion = "1.3.1"
    private const val googleServicesVersion = "4.3.3"
    private const val gradleUpdateVersion = "0.29.0"
    private const val safeArgsVersion = "2.3.0"

    const val androidGradlePlugin = "com.android.tools.build:gradle:$buildToolsVersion"
    const val gradleUpdate = "com.github.ben-manes:gradle-versions-plugin:$gradleUpdateVersion"
    const val googleServices = "com.google.gms:google-services:$googleServicesVersion"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsVersion"
    const val firebasePerformance = "com.google.firebase:perf-plugin:$firebasePerformanceVersion"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.kotlinVersion}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgsVersion"
}

object BuildPlugins {
    const val detektVersion = "1.11.0"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val ksp = "com.google.devtools.ksp"
    const val gradleUpdater = "com.github.ben-manes.versions"
    const val googleServices = "com.google.gms.google-services"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val firebasePerformance = "com.google.firebase.firebase-perf"
    const val projectDependencyGraph =
        "https://raw.githubusercontent.com/JakeWharton/SdkSearch/master/gradle/projectDependencyGraph.gradle"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
    const val taskTree = "com.dorongold.task-tree"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
}

object AndroidSdkTools {

    const val minSdk = 24
    const val targetSdk = 34
    const val compileSdk = 34
    const val version_code = 7
    const val version_name = "1.2.0"
    const val application_id = "com.tobiapplications.fahrstuhlblock"
}

object Others {

    const val ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val JVM_TARGET = "17"
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
