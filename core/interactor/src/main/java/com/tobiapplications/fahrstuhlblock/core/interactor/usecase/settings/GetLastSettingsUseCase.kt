package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.settings

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.settings.SettingsData
import com.tobiapplications.fahrstuhlblock.core.entities.models.settings.SettingsScreen
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class GetLastSettingsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<SettingsScreen, SettingsData>() {

    override suspend fun execute(parameters: SettingsScreen): AppResult<SettingsData> {
        return gameRepository.getLastSettingsData(parameters)
    }
}
