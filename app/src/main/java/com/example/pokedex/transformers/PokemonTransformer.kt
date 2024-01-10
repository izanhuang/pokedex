package com.example.pokedex.transformers

import com.example.pokedex.types.Pokemon
import com.example.pokedex.types.PokemonFromNetwork
import com.example.pokedex.types.PokemonSprites
import com.example.pokedex.types.PokemonSpritesFromNetwork

class PokemonTransformer {

    private fun convertPokemonSpritesFromNetworkToPokemonSprites(
        pokemonSpritesFromNetwork: PokemonSpritesFromNetwork
    ): PokemonSprites {
        return PokemonSprites(
            backDefault = pokemonSpritesFromNetwork.back_default,
            frontDefault = pokemonSpritesFromNetwork.front_default
        )
    }

    fun convertPokemonFromNetworkToPokemon(
        pokemonFromNetwork: PokemonFromNetwork?
    ): Pokemon? {
        if (pokemonFromNetwork != null) {
            return Pokemon(
                id = pokemonFromNetwork.id,
                name = pokemonFromNetwork.name,
                baseExperience = pokemonFromNetwork.base_experience,
                height = pokemonFromNetwork.height,
                weight = pokemonFromNetwork.weight,
                sprites = pokemonFromNetwork.sprites?.let {
                    convertPokemonSpritesFromNetworkToPokemonSprites(
                        it
                    )
                }
            )
        }

        return null
    }
}