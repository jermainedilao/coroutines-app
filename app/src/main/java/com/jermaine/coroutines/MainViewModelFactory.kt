package com.jermaine.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jermaine.coroutines.data.DataRepository
import com.jermaine.coroutines.service.ApiService
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@UnstableDefault
class MainViewModelFactory : ViewModelProvider.Factory {

    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(
                Json(
                    JsonConfiguration(ignoreUnknownKeys = true)
                ).asConverterFactory("application/json".toMediaType())
            )
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(
                DataRepository(
                    apiService
                )
            ) as T
        } else {
            throw IllegalArgumentException("Unknown view model class ${modelClass::class.java.simpleName}")
        }
    }
}