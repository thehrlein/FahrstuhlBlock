package com.tobiapplications.fahrstuhlblock.koin

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.google.firebase.analytics.FirebaseAnalytics
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.fw_database_room.databaseModule
import com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.sharedpref.FahrstuhlBlockSharedPreferences
import com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.firebase.AnalyticsDatasourceImpl
import com.tobiapplications.fahrstuhlblock.fw_repositories.processor.BlockInputsProcessorImpl
import com.tobiapplications.fahrstuhlblock.fw_repositories.processor.BlockResultsProcessorImpl
import com.tobiapplications.fahrstuhlblock.fw_repositories.repository.FirebaseRepositoryImpl
import com.tobiapplications.fahrstuhlblock.fw_repositories.repository.GameRepositoryImpl
import com.tobiapplications.fahrstuhlblock.fw_repositories.repository.UserRepositoryImpl
import com.tobiapplications.fahrstuhlblock.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiapplications.fahrstuhlblock.interactor.datasource.sharedpref.UserSettingsPersistence
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockInputsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.repository.FirebaseRepository
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.repository.UserRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.GetPlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.StorePlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.savedgames.GetAllSavedGamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.settings.GetLastSettingsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.presentation.block.input.BlockInputViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.results.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.scores.BlockScoresViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.trump.BlockTrumpViewModel
import com.tobiapplications.fahrstuhlblock.presentation.main.MainViewModel
import com.tobiapplications.fahrstuhlblock.presentation.menu.MenuViewModel
import com.tobiapplications.fahrstuhlblock.presentation.savedgames.SavedGamesViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.*
import com.tobiapplications.fahrstuhlblock.presentation.settings.gamerules.GameRulesViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.playerorder.PlayerOrderViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.playersettings.PlayerSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.pointrules.PointRulesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelper
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelperImpl
import com.tobiapplications.fahrstuhlblock.utils.NavigationHandlerImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module


object Koin {

    private val single = module {
        // general
        single<ResourceHelper> { ResourceHelperImpl(get()) }
        single { FirebaseAnalytics.getInstance(get()) }

        // processor
        single<BlockInputsProcessor> { BlockInputsProcessorImpl() }
        single<BlockResultsProcessor> { BlockResultsProcessorImpl() }

        // repository
        single<GameRepository> { GameRepositoryImpl(get(), get(), get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<FirebaseRepository> { FirebaseRepositoryImpl(get()) }

        // datasource
        single { FahrstuhlBlockSharedPreferences(get()) } binds (arrayOf(UserSettingsPersistence::class))
        single<AnalyticsDatasource> { AnalyticsDatasourceImpl(get()) }
    }

    private val factory = module {
        // navigation handler
        factory<NavigationHandler> { (activity: AppCompatActivity, navHostController: NavHostController?) ->
            NavigationHandlerImpl(
                activity,
                navHostController,
                get()
            )
        }
        // usecases
        factory { StorePlayerNamesUseCase(get()) }
        factory { GetPlayerNamesUseCase(get()) }
        factory { GetLastSettingsUseCase(get()) }
        factory { StoreGameInfoUseCase(get()) }
        factory { GetGameUseCase(get()) }
        factory { GetGameScoresUseCase(get()) }
        factory { CalculateResultsUseCase(get()) }
        factory { StoreRoundUseCase(get()) }
        factory { GetBlockResultsUseCase(get()) }
        factory { InputsValidUseCase(get()) }
        factory { SetShowTrumpDialogEnabledUseCase(get()) }
        factory { IsShowTrumpDialogEnabledUseCase(get()) }
        factory { TrackAnalyticsEventUseCase(get()) }
        factory { GetAllSavedGamesUseCase(get()) }
        factory { StoreGameFinishedUseCase(get()) }
    }

    private val viewModel = module {
        viewModel { MainViewModel(get()) }
        viewModel { MenuViewModel() }
        viewModel { GameSettingsViewModel() }
        viewModel {
            PlayerSettingsViewModel(
                get(),
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
                playerSettingsData,
                get(),
                get(),
                get(),
                get()
            )
        }
        viewModel { (gameRuleSettingsData: GameRuleSettingsData) ->
            PointRulesViewModel(
                gameRuleSettingsData,
                get(),
                get(),
                get()
            )
        }
        viewModel { (gameId: Long) -> BlockViewModel(gameId) }
        viewModel {
            BlockResultsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        viewModel { (gameId: Long) ->
            BlockInputViewModel(
                gameId,
                get(),
                get(),
                get(),
                get()
            )
        }

        viewModel { BlockScoresViewModel() }
        viewModel { BlockTrumpViewModel(get(), get(), get()) }
        viewModel { SavedGamesViewModel(get()) }

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