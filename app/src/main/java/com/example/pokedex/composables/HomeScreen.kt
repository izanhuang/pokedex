package com.example.pokedex.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.types.BasicPokemon

@Composable
fun HomeScreen(pokemonList: List<BasicPokemon>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon = pokemon)
        }
    }
}