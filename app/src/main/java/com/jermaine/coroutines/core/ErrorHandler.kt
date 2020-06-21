package com.jermaine.coroutines.core

import android.util.Log
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler {
    companion object {
        private const val TAG = "ErrorHandler"

        fun handleError(error: Throwable): Error {
            return when (error) {
                is HttpException -> {
                    val message = "Network error"
                    Error(message, error, error.response()!!.code().toString())
                }
                is IOException -> {
                    Error("Please check your internet connection", error)
                }
                else -> {
                    Log.e(TAG, "handleError", error)
                    Error("Oops! Something went wrong", error)
                }
            }
        }
    }
}
