package com.example.pokedex.network

import com.example.pokedex.types.GenerationFromNetwork
import com.example.pokedex.types.GenerationList
import com.example.pokedex.types.PokemonFromNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A retrofit service to fetch pokedex information.
 */
interface PokedexService {
    @GET("generation")
    suspend fun getGenerationList(): Response<GenerationList>

    @GET("generation/{id}")
    suspend fun getGeneration(@Path("id") id: Int): Response<GenerationFromNetwork>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Response<PokemonFromNetwork>
}