package com.jermaine.coroutines.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jermaine.coroutines.data.DataRepository
import com.jermaine.coroutines.data.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _items by lazy {
        MutableLiveData<List<Item>>()
    }

    val items: LiveData<List<Item>> = _items

    private val _state by lazy {
        BroadcastChannel<MainState>(1)
    }

    val state: Flow<MainState> = _state.openSubscription().receiveAsFlow()

    fun search(isRefresh: Boolean = false) {
        if (!isRefresh && _items.value?.isNotEmpty() == true) {
            // Do not re fetch when list if not empty and not coming from refresh.
            return
        }

        viewModelScope.launch {
            if (!isRefresh) {
                // Skip showing loading when coming from swipe refresh.
                _state.send(MainState.ShowLoading)
            }

            getSearchList()?.let {
                _items.value = it
            }
            _state.send(MainState.HideLoading)
        }
    }

    /**
     * Responsible for getting list from repository.
     *
     * @return list of items or null if error happens.
     */
    private suspend fun getSearchList(): List<Item>? {
        return try {
            val result = dataRepository.search()
            if (result.isSuccessful) {
                // Run data manipulation in Dispatchers.Default
                // This is similar to Schedulers.computation() in RxJava.
                withContext(Dispatchers.Default) {
                    result
                        .value()
                        .sortedBy { it.trackName }
                }
            } else {
                // Handled error occurred.
                _state
                    .send(
                        MainState.Error(
                            result.error().message
                        )
                    )
                null
            }
        } catch (e: Exception) {
            // Unexpected error occurred.
            e.printStackTrace()
            _state
                .send(
                    MainState.Error()
                )
            null
        }
    }
}