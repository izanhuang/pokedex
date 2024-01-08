package com.example.pokedex.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Main entry point for network access. Call like `PokedexNetwork.pokedex.getGenerationCount()`
 */
object RetrofitInstance {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokedexService: PokedexService by lazy {
        retrofit.create(PokedexService::class.java)
    }
}