package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class GenerationViewData(
    val id: Int? = null,
    val name: String? = null,
    val displayName: String? = null,
    val generations: List<NameAndUrl> = emptyList(),
    val totalCount: Int = 0,
)