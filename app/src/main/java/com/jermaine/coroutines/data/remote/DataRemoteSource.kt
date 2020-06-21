package com.jermaine.coroutines.data.remote

import com.jermaine.coroutines.core.BaseRemoteSource
import com.jermaine.coroutines.core.Result
import com.jermaine.coroutines.data.Item
import com.jermaine.coroutines.service.ApiService

class DataRemoteSource(private val apiService: ApiService) : BaseRemoteSource() {
    suspend fun search(): Result<List<Item>> {
        return execute {
            apiService
                .search("john+mayer")
                .results
        }
    }
}