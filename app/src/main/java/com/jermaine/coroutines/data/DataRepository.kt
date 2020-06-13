package com.jermaine.coroutines.data

import com.jermaine.coroutines.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val apiService: ApiService) {
    suspend fun search(): Response {
        return withContext(Dispatchers.IO) {
            apiService.search("john+mayer")
        }
    }
}