package com.example.pokedex.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.types.BasicPokemon
import com.example.pokedex.ui.theme.LightGreen

@Composable
fun PokemonCard(
    pokemon: BasicPokemon,
    modifier: Modifier = Modifier
) {
    val MAX_POKEMON_ID_LENGTH = 4

    var pokemonIdAsString = pokemon.id.toString()
    val displayPokemonId = if (pokemonIdAsString.length < MAX_POKEMON_ID_LENGTH) {
        val lengthDifference = MAX_POKEMON_ID_LENGTH - pokemonIdAsString.length
        for (i in 0 until lengthDifference) {
            pokemonIdAsString = "0" + pokemonIdAsString
        }

        "NO. $pokemonIdAsString"
    } else {
        "NO. $pokemonIdAsString"
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            LightGreen,
                            MaterialTheme.colorScheme.background
                        ),
                    )
                ),
        ) {
            Text(
                text = displayPokemonId,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            // TODO: Handle case where sprite is null or there is no image url
            AsyncImage(
                model = pokemon.sprites?.frontDefault,
                contentDescription = stringResource(R.string.pokemon_card),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .defaultMinSize(minWidth = 200.dp, minHeight = 144.dp)
            )
            pokemon.displayName?.let {
                Text(
                    text = it.uppercase(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}