package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id: Int?,
    val name: String?,
    val baseExperience: Int?,
    val height: Int?,
    val weight: Int?,
    val sprites: PokemonSprites?
)

@Immutable
data class PokemonSprites(
    val backDefault: String?,
    val frontDefault: String?,
)

data class PokemonFromNetwork(
    val id: Int?,
    val name: String?,
    val base_experience: Int?,
    val height: Int?,
    val weight: Int?,
    val sprites: PokemonSpritesFromNetwork?
)

data class PokemonSpritesFromNetwork(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)