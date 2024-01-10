package com.example.pokedex.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.types.Pokemon

@Composable
fun PokemonCard(
    pokemon: Pokemon,
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

    Card(modifier = modifier) {
        Text(
            text = displayPokemonId,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        // TODO: Handle case where sprite is null or there is no image url
        AsyncImage(
            model = pokemon.sprites?.frontDefault,
            contentDescription = stringResource(R.string.pokemon_card),
            modifier = Modifier
                .defaultMinSize(minWidth = 200.dp, minHeight = 200.dp)
                .background(color = Color.Green),
        )
        Text(
            text = "${pokemon.displayName}",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}