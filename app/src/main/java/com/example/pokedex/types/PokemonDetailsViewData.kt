package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonDetailsViewData(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
)