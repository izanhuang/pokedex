package com.example.pokedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.types.Screen

@Composable
fun TopBar(
    currentRoute: String?,
    homeScreenTitle: String?,
    homeScreenLabel: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (currentRoute) {
            Screen.Home.route -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = homeScreenTitle ?: "Pokedex",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (homeScreenLabel != null) {
                        Text(
                            text = homeScreenLabel,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Screen.Settings.route -> {
                Text(
                    text = Screen.Settings.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}