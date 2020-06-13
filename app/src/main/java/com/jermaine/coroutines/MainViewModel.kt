package com.jermaine.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jermaine.coroutines.data.DataRepository
import com.jermaine.coroutines.data.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _items by lazy {
        MutableLiveData<List<Item>>()
    }

    val items: LiveData<List<Item>> = _items

    private val _loadingState by lazy {
        MutableLiveData(false)
    }

    val loadingState: LiveData<Boolean> = _loadingState

    fun search() {
        viewModelScope.launch {
            _loadingState.value = true

            val response = dataRepository.search()

            // Run data manipulation in Dispatchers.Default
            // This is similar to Schedulers.computation() in RxJava.
            val sorted = withContext(Dispatchers.Default) {
                response
                    .results
                    .sortedBy { it.trackName }
            }

            _items.value = sorted
            _loadingState.value = false
        }
    }
}