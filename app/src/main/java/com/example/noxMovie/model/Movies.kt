package com.example.noxMovie.model

import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    val Response: String?=null,
    val Search: List<Search>? = null,
    val totalResults: String? = null
)