package com.example.pokedex.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onResetAppClick: () -> Unit,
    onUpdatePokemonListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ), shape = MaterialTheme.shapes.medium
                )
        ) {
            SettingsScreenItem(
                label = "Reset the pokedex and the pokemon generation you selected",
                buttonText = "Reset",
                onClick = onResetAppClick
            )
            SettingsScreenItem(
                label = "Update the pokedex and retrieve the latest pokemon from the pokemon generation you selected",
                buttonText = "Update",
                onClick = onUpdatePokemonListClick,
                isLastItem = true
            )
        }
    }
}