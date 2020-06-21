package com.jermaine.coroutines.data

import com.jermaine.coroutines.core.BaseRepository
import com.jermaine.coroutines.core.Result
import com.jermaine.coroutines.service.ApiService

class DataRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun search(): Result<List<Item>> {
        return execute {
            apiService
                .search("john+mayer")
                .results
        }
    }
}