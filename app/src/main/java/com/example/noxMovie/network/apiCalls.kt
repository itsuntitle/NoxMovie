package com.example.noxMovie.network

import com.example.noxMovie.model.Movies
import com.example.noxMovie.model.Search
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


suspend fun apiMainMovieCall(): List<Search> {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 30000
        }
    }

    return try {
        val response: Movies = client.get("https://www.omdbapi.com/?s=batman&apikey=3955e134").body()
        response.Search ?: emptyList()
    } catch (e: Exception) {
        throw e
    }
}
suspend fun apiPosterMovieCall(): List<Search> {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 30000
        }
    }

    return try {
        val response: Movies = client.get("https://www.omdbapi.com/?s=Guardians&apikey=3700d3aa").body()
        response.Search ?: emptyList()
    } catch (e: Exception) {
        e.toString()
        emptyList()
    } finally {
        client.close()
    }
}
