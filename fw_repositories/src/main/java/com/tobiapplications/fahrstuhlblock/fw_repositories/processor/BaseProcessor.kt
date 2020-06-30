package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult

interface BaseProcessor {

    fun <T> safeCall(call: () -> T): AppResult<T> {
        return safeCall(call) { response ->
            AppResult.Success(response)
        }
    }

    private fun <T, R> safeCall(
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