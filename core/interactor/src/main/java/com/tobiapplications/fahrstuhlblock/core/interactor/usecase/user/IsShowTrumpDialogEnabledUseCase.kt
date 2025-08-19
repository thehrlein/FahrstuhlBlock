package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.user

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.UserRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class IsShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun execute(parameters: Unit): AppResult<Boolean> {
        return userRepository.isShowTrumpDialogEnabled()
    }
}
