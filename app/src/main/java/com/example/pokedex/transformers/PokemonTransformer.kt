package com.example.pokedex.transformers

import com.example.pokedex.types.Pokemon
import com.example.pokedex.types.PokemonFromNetwork
import com.example.pokedex.types.PokemonSprites
import com.example.pokedex.types.PokemonSpritesFromNetwork
import java.text.DecimalFormat

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

    private val decimalFormat = DecimalFormat("#.##")

    private fun convertFromDecimetresToFeetAndInches(value: Int): String {
        val valueInFeet = decimalFormat.format(value / 3.048)
        val separatedFeetAndInches = valueInFeet.toString().split(".")

        return "${separatedFeetAndInches[0]}'${separatedFeetAndInches[1]}\""
    }

    private fun convertFromHectogramToPounds(value: Int): String {
        return "${decimalFormat.format(value / 4.536)} lbs"
    }

    private fun transformPokemonSpritesFromNetworkToPokemonSprites(
        pokemonSpritesFromNetwork: PokemonSpritesFromNetwork
    ): PokemonSprites {
        return PokemonSprites(
            backDefault = pokemonSpritesFromNetwork.back_default,
            frontDefault = pokemonSpritesFromNetwork.front_default,
            officialArtworkFrontDefault = pokemonSpritesFromNetwork.other?.officialArtwork?.front_default,
            officialArtworkFrontShiny = pokemonSpritesFromNetwork.other?.officialArtwork?.front_shiny
        )
    }

    fun transformPokemonFromNetworkToPokemon(
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
                    height = convertFromDecimetresToFeetAndInches(height),
                    weight = convertFromHectogramToPounds(weight),
                    sprites = pokemonFromNetwork.sprites?.let {
                        transformPokemonSpritesFromNetworkToPokemonSprites(it)
                    },
                    species = null,
                )
            }
        }

        return null
    }
}