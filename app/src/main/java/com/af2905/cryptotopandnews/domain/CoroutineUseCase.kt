package com.af2905.cryptotopandnews.domain

import timber.log.Timber
import java.lang.Exception

abstract class CoroutineUseCase<in P, R> {

    suspend operator fun invoke(params: P): Result<R> {
        return try {
            Result.success(execute(params))
        } catch (e: Exception) {
            Timber.d(e)
            Result.failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): R
}