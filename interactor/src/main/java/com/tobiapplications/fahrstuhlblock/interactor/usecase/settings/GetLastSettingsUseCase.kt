package com.tobiapplications.fahrstuhlblock.interactor.usecase.settings

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsScreen
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetLastSettingsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<SettingsScreen, SettingsData>() {

    override suspend fun execute(parameters: SettingsScreen): AppResult<SettingsData> {
        return gameRepository.getLastSettingsData(parameters)
    }
}
