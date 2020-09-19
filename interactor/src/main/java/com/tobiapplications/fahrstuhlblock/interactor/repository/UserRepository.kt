package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult

interface UserRepository {

    suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean>

    suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit>
}
