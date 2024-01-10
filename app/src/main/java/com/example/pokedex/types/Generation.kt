package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class Generation(
    val id: Int?,
    val name: String?,
    val pokemonSpecies: List<NameAndUrl>?
)

data class GenerationFromNetwork(
    val id: Int,
    val name: String,
    val pokemon_species: List<NameAndUrl>?
)