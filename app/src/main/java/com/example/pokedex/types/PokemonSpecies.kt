package com.example.pokedex.types

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class PokemonSpecies(
    val genus: String,
    val flavorText: String
)

@Immutable
data class PokemonSpeciesFromNetwork(
    val genera: List<Genus>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorText>
)

@Immutable
data class Genus(
    val genus: String,
    val language: NameAndUrl,
)

@Immutable
data class FlavorText(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: NameAndUrl,
    val version: NameAndUrl,
)