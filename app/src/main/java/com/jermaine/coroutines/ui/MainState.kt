package com.jermaine.coroutines.ui

sealed class MainState {
    object ShowLoading : MainState()

    object HideLoading : MainState()

    class Error(val message: String = "") : MainState()
}