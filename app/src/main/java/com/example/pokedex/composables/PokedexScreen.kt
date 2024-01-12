package com.example.pokedex.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
        if (pokedex.generationDetails.currentGenerationId == null) {
            SetupScreen(
                allGenerations = pokedex.generationDetails.allGenerations,
                onGenerationSelect = { generationId -> viewModel.getAllPokemonFromGeneration(generationId) }
            )
        }
        if (!pokedex.isLoading && pokedex.pokemonList.isNotEmpty()) {
            Column {
                pokedex.generationDetails.currentGenerationDisplayName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HomeScreen(pokemonList = pokedex.pokemonList)
            }
        }
        if (pokedex.isLoading) {
            Text("loading")
        }
    }
}