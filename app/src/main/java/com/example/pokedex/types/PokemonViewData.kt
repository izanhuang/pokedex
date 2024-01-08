package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonViewData(
    val id: Int,
    val name: String,
    val imageUrl: String,
)