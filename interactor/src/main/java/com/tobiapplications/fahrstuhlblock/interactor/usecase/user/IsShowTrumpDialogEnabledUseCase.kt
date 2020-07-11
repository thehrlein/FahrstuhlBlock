package com.tobiapplications.fahrstuhlblock.interactor.usecase.user

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.interactor.repository.UserRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class IsShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun execute(parameters: Unit): AppResult<Boolean> {
        return userRepository.isShowTrumpDialogEnabled()
    }
}