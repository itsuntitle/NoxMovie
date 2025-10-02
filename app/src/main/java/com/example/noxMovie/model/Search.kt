package com.example.noxMovie.model

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val Poster: String? = null,
    val Title: String? = null,
    val Type: String? = null,
    val Year: String? = null,
    val imdbID: String? = null
)