package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class Generation(
    val id: Int,
    val name: String,
    val displayName: String? = null,
    val pokemonSpecies: List<NameAndUrl>? = null
)

data class GenerationFromNetwork(
    val id: Int,
    val name: String,
    val pokemon_species: List<NameAndUrl>?
)