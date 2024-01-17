package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonSpecies(
    val genus: String,
    val flavorText: String
)

@Immutable
data class PokemonSpeciesFromNetwork(
    val genera: List<Genus>,
    val flavor_text_entries: List<FlavorText>
)

@Immutable
data class Genus(
    val genus: String,
    val language: NameAndUrl,
)

@Immutable
data class FlavorText(
    val flavor_text: String,
    val language: NameAndUrl,
    val version: NameAndUrl,
)