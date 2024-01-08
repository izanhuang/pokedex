package com.example.pokedex.types

import androidx.compose.runtime.Immutable
import me.sargunvohra.lib.pokekotlin.model.ResourceSummaryList

@Immutable
data class GenerationResourceSummaryList(
    override val results: List<NameAndUrl>,
    override val count: Int,
    override val next: String?,
    override val previous: String?
) : ResourceSummaryList<NameAndUrl>