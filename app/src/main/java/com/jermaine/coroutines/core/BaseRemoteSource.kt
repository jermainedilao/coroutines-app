package com.jermaine.coroutines.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Base class for all remote source.
 */
open class BaseRemoteSource {
    /**
     * Executes a network request and handles error accordingly.
     */
    suspend fun <T : Any> execute(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result
                    .success(
                        block()
                    )
            } catch (e: Exception) {
                Result
                    .error<T>(
                        ErrorHandler.handleError(e)
                    )
            }
        }
    }
}