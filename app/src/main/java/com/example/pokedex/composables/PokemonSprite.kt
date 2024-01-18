package com.example.pokedex.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R

@Composable
fun PokemonSprite(imageUrl: String?, modifier: Modifier = Modifier) {
    if (imageUrl != null) {
        AsyncImage(
            model =
            imageUrl,
            contentDescription = stringResource(R.string.pokemon_image),
            modifier = modifier
                .padding(vertical = 16.dp)
                .size(width = 200.dp, height = 144.dp)
        )
    } else {
        Image(
            painterResource(
                R.drawable.question_mark
            ),
            contentDescription = stringResource(R.string.pokemon_image),
            modifier = modifier
                .padding(vertical = 48.dp)
                .size(width = 200.dp, height = 80.dp)

        )
    }
}