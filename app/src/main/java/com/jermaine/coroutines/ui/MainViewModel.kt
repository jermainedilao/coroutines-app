package com.jermaine.coroutines.ui

import android.util.Log
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

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _items by lazy {
        MutableLiveData<List<Item>>()
    }

    val items: LiveData<List<Item>> = _items

    private val _state by lazy {
        BroadcastChannel<MainState>(1)
    }

    val state: Flow<MainState> = _state.openSubscription().receiveAsFlow()

    fun search() {
        viewModelScope.launch {
            _state.send(MainState.ShowLoading)

            val sorted = try {
                val response = dataRepository.search()

                // Run data manipulation in Dispatchers.Default
                // This is similar to Schedulers.computation() in RxJava.
                withContext(Dispatchers.Default) {
                    response
                        .results
                        .sortedBy { it.trackName }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.send(MainState.Error)
                null
            }

            sorted?.let {
                _items.value = sorted
            }
            _state.send(MainState.HideLoading)
        }
    }
}