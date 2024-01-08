package com.example.pokedex.network

import com.example.pokedex.types.GenerationResourceSummaryList
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A retrofit service to fetch pokedex information.
 */
interface PokedexService {
    @GET("generation")
    suspend fun getGenerationList(): Response<GenerationResourceSummaryList>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<Pokemon>
}