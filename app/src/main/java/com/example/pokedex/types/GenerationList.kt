package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class GenerationList(
    val count: Int,
    val results: List<NameAndUrl>
)