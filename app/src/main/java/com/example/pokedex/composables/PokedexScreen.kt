package com.example.pokedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.PokedexViewModel

@Composable
fun PokedexScreen(modifier: Modifier = Modifier, viewModel: PokedexViewModel = viewModel()) {
    val pokedex by viewModel.pokedex.collectAsState()

    Column(modifier = modifier) {
        if (pokedex.generation.id == null) {
            pokedex.generation.generations.forEachIndexed { index, generation ->
                Button(onClick = { viewModel.getGeneration(index + 1) }) {
                    Text(generation.name)
                }
            }
        }
        if (pokedex.generation.id != null) {
            Text(pokedex.pokemonList.toString())
        }
        if (pokedex.isLoading) {
            Text("loading")
        }
    }
}