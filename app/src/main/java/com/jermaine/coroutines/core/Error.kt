package com.jermaine.coroutines.core

data class Error(
    val message: String = "",
    val cause: Throwable? = null,
    val errorCode: String = ""
)
