package com.example.pokedex.repository

import com.example.pokedex.network.RetrofitInstance
import com.example.pokedex.types.GenerationResourceSummaryList

/**
 * Repository for fetching pokedex information from the network and storing them on disk
 */
class PokedexRepository() {
    private val pokedexService = RetrofitInstance.pokedexService

    suspend fun getGenerationList(): GenerationResourceSummaryList? {
        return pokedexService.getGenerationList().body()
    }
}