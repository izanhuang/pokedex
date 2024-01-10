package com.example.pokedex.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.PokedexViewModel

@Composable
fun PokedexScreen(modifier: Modifier = Modifier, viewModel: PokedexViewModel = viewModel()) {
    val pokedex by viewModel.pokedex.collectAsState()

    // TODO: Add sticky top app bar of "Generation #/nNO. #### - ####"
    // TODO: Add loading animation
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (pokedex.generation.id == null) {
            Column {
                pokedex.generation.generations.forEachIndexed { index, generation ->
                    viewModel.getGenerationDisplayName(generation.name)?.let {
                        Button(onClick = { viewModel.getGeneration(index + 1) }) {
                            Text(it)
                        }
                    }
                }
            }
        }
        if (!pokedex.isLoading && pokedex.pokemonList.isNotEmpty()) {
            Column {
                pokedex.generation.displayName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(pokedex.pokemonList) { pokemon ->
                        PokemonCard(pokemon = pokemon)
                    }
                }
            }
        }
        if (pokedex.isLoading) {
            Text("loading")
        }
    }
}