package com.tobiapplications.fahrstuhlblock.core.interactor.repository

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult

interface UserRepository {

    suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean>

    suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit>
}
