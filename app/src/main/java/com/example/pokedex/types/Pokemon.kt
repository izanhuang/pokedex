package com.example.pokedex.types

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Pokemon(
    val id: Int,
    val name: String,
    val displayName: String,
    val baseExperience: Int?,
    val height: String,
    val weight: String,
    val sprites: PokemonSprites?,
    val species: PokemonSpecies?
)

@Immutable
data class BasicPokemon(
    val id: Int,
    val name: String,
    val displayName: String,
    val sprites: PokemonSprites?
)

@Immutable
data class PokemonSprites(
    val backDefault: String?,
    val frontDefault: String?,
    val officialArtworkFrontDefault: String?,
    val officialArtworkFrontShiny: String?
)

data class PokemonFromNetwork(
    val id: Int?,
    val name: String?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    val height: Int?,
    val weight: Int?,
    val sprites: PokemonSpritesFromNetwork?
)

data class PokemonSpritesFromNetwork(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("front_default")
    val frontDefault: String?,
    val other: PokemonSpritesOther?
)

data class PokemonSpritesOther(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonSpritesOfficialArtwork
)

data class PokemonSpritesOfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
)