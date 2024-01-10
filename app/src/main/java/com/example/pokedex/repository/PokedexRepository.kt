package com.example.pokedex.repository

import com.example.pokedex.transformers.GenerationTransformer
import com.example.pokedex.network.RetrofitInstance
import com.example.pokedex.transformers.PokemonTransformer
import com.example.pokedex.types.Generation
import com.example.pokedex.types.GenerationList
import com.example.pokedex.types.Pokemon

/**
 * Repository for fetching pokedex information from the network and storing them on disk
 */
class PokedexRepository() {
    private val pokedexService = RetrofitInstance.pokedexService

    suspend fun getGenerationList(): GenerationList? {
        return pokedexService.getGenerationList().body()
    }

    suspend fun getGeneration(id: Int): Generation? {
        val generationFromNetwork = pokedexService.getGeneration(id).body()
        return GenerationTransformer().transformGenerationFromNetworkToGeneration(generationFromNetwork)
    }

    suspend fun getPokemon(name: String): Pokemon? {
        val pokemonFromNetwork = pokedexService.getPokemon(name).body()
        return PokemonTransformer().convertPokemonFromNetworkToPokemon(pokemonFromNetwork)
    }
}