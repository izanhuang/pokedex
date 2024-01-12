package com.example.pokedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SetupScreen(
    allGenerations: List<String>,
    onGenerationSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        allGenerations.forEachIndexed { index, generationName ->
            Button(onClick = { onGenerationSelect(index + 1) }) {
                Text(generationName)
            }
        }
    }
}