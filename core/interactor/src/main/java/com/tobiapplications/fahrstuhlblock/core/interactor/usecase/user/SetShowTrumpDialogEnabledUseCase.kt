package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.user

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.UserRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class SetShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun execute(parameters: Boolean): AppResult<Unit> {
        return userRepository.setShowTrumpDialogEnabled(parameters)
    }
}
