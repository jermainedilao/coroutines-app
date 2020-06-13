package com.jermaine.coroutines.data

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val resultCount: Int = 0,
    val results: List<Item> = emptyList()
)

@Serializable
data class Item(
    val trackId: Int = 0,
    val artistName: String = "",
    val trackName: String = ""
)
