package com.example.pokedex.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.types.BasicPokemon

@Composable
fun PokemonCard(
    pokemon: BasicPokemon,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = modifier.clickable { onClick(pokemon.name) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.background
                        ),
                    )
                ),
        ) {
            Text(
                text = "NO. ${pokemon.id}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            if (pokemon.sprites?.frontDefault != null) {
                AsyncImage(
                    model =
                    pokemon.sprites.frontDefault,
                    contentDescription = stringResource(R.string.pokemon_card),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(width = 200.dp, height = 144.dp)
                )
            } else {
                Image(
                    painterResource(
                        R.drawable.question_mark
                    ),
                    contentDescription = stringResource(R.string.pokemon_card),
                    modifier = Modifier
                        .padding(vertical = 48.dp)
                        .size(width = 200.dp, height = 80.dp)

                )
            }
            Text(
                text = pokemon.displayName.uppercase(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}