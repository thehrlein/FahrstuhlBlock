package com.tobiapplications.fahrstuhlblock.koin

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.fw_database_room.databaseModule
import com.tobiapplications.fahrstuhlblock.fw_repositories.repository.GameRepositoryImpl
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.GetPlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.StorePlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.main.MainViewModel
import com.tobiapplications.fahrstuhlblock.presentation.menu.MenuViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.*
import com.tobiapplications.fahrstuhlblock.presentation.settings.gamerules.GameRulesViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.playerorder.PlayerOrderViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.playersettings.PlayerSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.pointrules.PointRulesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelper
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelperImpl
import com.tobiapplications.fahrstuhlblock.utils.NavigationComponentsHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object Koin {

    private val single = module {
        // general
        single<ResourceHelper> { ResourceHelperImpl(get()) }

        // repository
        single<GameRepository> { GameRepositoryImpl(get(), get()) }
    }

    private val factory = module {
        // navigation handler
        factory<NavigationHandler> { (activity: AppCompatActivity, navHostController: NavHostController?) ->
            NavigationComponentsHandler(
                activity,
                navHostController,
                get()
            )
        }
        // usecases
        factory { StorePlayerNamesUseCase(get()) }
        factory { GetPlayerNamesUseCase(get()) }

    }

    private val viewModel = module {
        viewModel { MainViewModel() }
        viewModel { MenuViewModel() }
        viewModel { GameSettingsViewModel() }
        viewModel {
            PlayerSettingsViewModel(
                get(),
                get()
            )
        }
        viewModel { (playerSettingsData: PlayerSettingsData) ->
            PlayerOrderViewModel(
                playerSettingsData
            )
        }
        viewModel { (playerSettingsData: PlayerSettingsData) ->
            GameRulesViewModel(
                playerSettingsData
            )
        }
        viewModel { (gameRuleSettingsData: GameRuleSettingsData) ->
            PointRulesViewModel(
                gameRuleSettingsData
            )
        }
        viewModel { (fahrstuhlGame: FahrstuhlGame) -> BlockViewModel(fahrstuhlGame) }
        viewModel { BlockResultsViewModel() }

    }

    fun getModules(): List<Module> {
        return listOf(
            single,
            factory,
            viewModel,
            databaseModule
        )
    }
}