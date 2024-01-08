package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class PokedexScreenState(
    val isLoading: Boolean = false,
    val generationCount: Int = 0,
    val generations: List<NameAndUrl> = emptyList(),
    val currentGeneration: String = "",
    val pokemonList: List<PokemonViewData> = emptyList(),
    val selectedPokemonDetails: PokemonDetailsViewData? = null
)