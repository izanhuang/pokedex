package com.example.pokedex.transformers

import com.example.pokedex.types.PokemonSpecies
import com.example.pokedex.types.PokemonSpeciesFromNetwork

class PokemonSpeciesTransformer {
    private fun removeCharacterEscapeSequences(value: String): String {
      return value.replace("\\n|\\s".toRegex(), " ")
    }

    fun transformPokemonSpeciesFromNetworkToPokemonSpecies(pokemonSpeciesFromNetwork: PokemonSpeciesFromNetwork?): PokemonSpecies? {
        val maybeGenusInEnglish = pokemonSpeciesFromNetwork?.genera?.find {
            it.language.name == "en"
        }
        val maybeFlavorTextInEnglish = pokemonSpeciesFromNetwork?.flavorTextEntries?.find {
            it.language.name == "en" && it.version.name == "emerald"
        }

        if (maybeGenusInEnglish != null && maybeFlavorTextInEnglish != null) {
            return PokemonSpecies(
                genus = maybeGenusInEnglish.genus,
                flavorText = removeCharacterEscapeSequences(maybeFlavorTextInEnglish.flavorText),
            )
        }

        return null
    }
}