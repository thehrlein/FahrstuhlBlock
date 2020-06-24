package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult

interface BaseCache {

    fun <T> safeCallDevice(call: () -> T): AppResult<T> {
        return safeCallDevice(call) { response ->
            AppResult.Success(response)
        }
    }

    private fun <T, R> safeCallDevice(
        call: () -> T,
        transform: (T) -> AppResult<R>
    ): AppResult<R> {
        return try {
            transform(call())
        } catch (ex: Exception) {
            AppResult.Error(
                reason = AppResult.Error.Reason.Unknown,
                cause = ex
            )
        }
    }
}