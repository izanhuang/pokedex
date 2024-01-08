package com.example.pokedex.types

import androidx.compose.runtime.Immutable
import me.sargunvohra.lib.pokekotlin.model.ResourceSummary

@Immutable
data class NameAndUrl(
    val name: String,
    val url: String,
    override val category: String,
    override val id: Int,
) : ResourceSummary