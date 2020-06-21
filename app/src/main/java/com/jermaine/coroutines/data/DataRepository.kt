package com.jermaine.coroutines.data

import com.jermaine.coroutines.core.BaseRemoteSource
import com.jermaine.coroutines.core.Result
import com.jermaine.coroutines.data.remote.DataRemoteSource
import com.jermaine.coroutines.service.ApiService

class DataRepository(private val remote: DataRemoteSource) : BaseRemoteSource() {
    suspend fun search(): Result<List<Item>> {
        return remote.search()
    }
}