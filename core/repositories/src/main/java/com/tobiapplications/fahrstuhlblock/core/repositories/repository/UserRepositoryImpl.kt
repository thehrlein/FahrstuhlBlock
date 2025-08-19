package com.tobiapplications.fahrstuhlblock.core.repositories.repository

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.sharedpref.UserSettingsPersistence
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.UserRepository

class UserRepositoryImpl(
    private val userSettingsPersistence: UserSettingsPersistence
) : UserRepository {

    override suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean> {
        return AppResult.Success(userSettingsPersistence.isShowTrumpDialogEnabled)
    }

    override suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit> {
        userSettingsPersistence.isShowTrumpDialogEnabled = enabled
        return AppResult.Success(Unit)
    }
}
