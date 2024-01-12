package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class PokedexScreenState(
    val isLoading: Boolean = false,
    val generationDetails: GenerationViewData = GenerationViewData(),
    val pokemonList: List<BasicPokemon> = emptyList(),
    val selectedPokemonDetails: Pokemon? = null
)