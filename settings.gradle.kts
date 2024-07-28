
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}



include(":app")

// ui
include(":ui_common")
include(":ui_menu")
include(":ui_game_settings")
include(":ui_block")
include(":ui_saved_games")

// framework modules
include(":fw_repositories")
include(":fw_database_room")

// basic architecture modules
include(":presentation")
include(":interactor")
include(":entities")

rootProject.name = "FahrstuhlBlock"
