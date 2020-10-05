package com.tobiapplications.fahrstuhlblock.interactor.usecase.user

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.interactor.repository.UserRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class SetShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun execute(parameters: Boolean): AppResult<Unit> {
        return userRepository.setShowTrumpDialogEnabled(parameters)
    }
}
