package com.jermaine.coroutines.ui

sealed class MainState {
    object ShowLoading : MainState()

    object HideLoading : MainState()

    object Error : MainState()
}