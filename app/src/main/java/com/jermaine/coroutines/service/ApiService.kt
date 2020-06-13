package com.jermaine.coroutines.service

import com.jermaine.coroutines.data.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun search(
        @Query("term") query: String
    ): Response
}