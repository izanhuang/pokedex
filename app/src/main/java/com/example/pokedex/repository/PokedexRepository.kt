package com.example.pokedex.repository

import com.example.pokedex.transformers.GenerationTransformer
import com.example.pokedex.network.RetrofitInstance
import com.example.pokedex.transformers.PokemonSpeciesTransformer
import com.example.pokedex.transformers.PokemonTransformer
import com.example.pokedex.types.Generation
import com.example.pokedex.types.GenerationList
import com.example.pokedex.types.Pokemon
import com.example.pokedex.types.PokemonSpecies

/**
 * Repository for fetching pokedex information from the network and storing them on disk
 */
class PokedexRepository() {
    private val pokedexService = RetrofitInstance.pokedexService

    suspend fun getAllGenerations(): GenerationList? {
        return pokedexService.getAllGenerations().body()
    }

    suspend fun getGeneration(id: Int): Generation? {
        val generationFromNetwork = pokedexService.getGeneration(id).body()
        return GenerationTransformer().transformGenerationFromNetworkToGeneration(generationFromNetwork)
    }

    suspend fun getPokemon(id: Int): Pokemon? {
        val pokemonFromNetwork = pokedexService.getPokemon(id).body()
        return PokemonTransformer().transformPokemonFromNetworkToPokemon(pokemonFromNetwork)
    }

    suspend fun getPokemonSpecies(id: Int): PokemonSpecies? {
        val pokemonSpeciesFromNetwork = pokedexService.getPokemonSpecies(id).body()
        return PokemonSpeciesTransformer().transformPokemonSpeciesFromNetworkToPokemonSpecies(pokemonSpeciesFromNetwork)
    }
}