package com.example.pokedex.types

import androidx.compose.runtime.Immutable

@Immutable
data class GenerationViewData(
    val currentGenerationId: Int? = null,
    val currentGenerationName: String? = null,
    val currentGenerationDisplayName: String? = null,
    val allGenerations: List<NameAndUrl> = emptyList(),
)