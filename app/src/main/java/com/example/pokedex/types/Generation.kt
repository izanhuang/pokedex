package com.example.pokedex.types

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Generation(
    val id: Int,
    val name: String,
    val displayName: String? = null,
    @SerializedName("pokemon_species")
    val pokemonSpecies: List<NameAndUrl>? = null
)