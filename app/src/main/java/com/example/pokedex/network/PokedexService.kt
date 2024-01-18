package com.example.pokedex.network

import com.example.pokedex.types.Generation
import com.example.pokedex.types.GenerationList
import com.example.pokedex.types.PokemonFromNetwork
import com.example.pokedex.types.PokemonSpeciesFromNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A retrofit service to fetch pokedex information.
 */
interface PokedexService {
    @GET("generation")
    suspend fun getAllGenerations(): Response<GenerationList>

    @GET("generation/{id}")
    suspend fun getGeneration(@Path("id") id: Int): Response<Generation>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<PokemonFromNetwork>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): Response<PokemonSpeciesFromNetwork>
}