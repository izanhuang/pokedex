package com.example.pokedex.transformers

import com.example.pokedex.types.Generation
import com.example.pokedex.types.GenerationFromNetwork

class GenerationTransformer {

    fun transformGenerationFromNetworkToGeneration(
        generationFromNetwork: GenerationFromNetwork?
    ): Generation? {
        if (generationFromNetwork != null ){
            return Generation(
                id = generationFromNetwork.id,
                name = generationFromNetwork.name,
                pokemonSpecies = generationFromNetwork.pokemon_species
            )
        }

        return null
    }
}