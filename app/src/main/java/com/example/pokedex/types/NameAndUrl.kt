package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class NameAndUrl(
    val name: String,
    val url: String,
)