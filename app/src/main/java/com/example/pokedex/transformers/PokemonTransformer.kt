package com.example.pokedex.transformers

import com.example.pokedex.types.Pokemon
import com.example.pokedex.types.PokemonFromNetwork
import com.example.pokedex.types.PokemonSprites
import com.example.pokedex.types.PokemonSpritesFromNetwork

class PokemonTransformer {
    inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
        p1: T1?,
        p2: T2?,
        p3: T3?,
        p4: T4?,
        p5: T5?,
        block: (T1, T2, T3, T4, T5) -> R?
    ): R? {
        return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
            p1,
            p2,
            p3,
            p4,
            p5,
        ) else null
    }

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
            val maybeDisplayName = pokemonFromNetwork.name?.replaceFirstChar { char -> char.uppercase() }
            return safeLet(
                pokemonFromNetwork.id,
                pokemonFromNetwork.name,
                pokemonFromNetwork.height,
                pokemonFromNetwork.weight,
                maybeDisplayName
            ) {
                id, name, height, weight, displayName ->
                Pokemon(
                    id = id,
                    name = name,
                    displayName = displayName,
                    baseExperience = pokemonFromNetwork.base_experience,
                    height = height,
                    weight = weight,
                    sprites = pokemonFromNetwork.sprites?.let {
                       convertPokemonSpritesFromNetworkToPokemonSprites(it)
                    }
                )
            }
        }

        return null
    }
}