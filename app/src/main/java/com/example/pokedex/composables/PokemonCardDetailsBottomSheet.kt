package com.example.pokedex.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.types.Pokemon
import com.example.pokedex.ui.theme.Red40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCardDetailsBottomSheet(
    selectedPokemonDetails: Pokemon?,
    sheetState: SheetState,
    onOutsideClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onOutsideClick,
        sheetState = sheetState,
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (selectedPokemonDetails != null) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "NO. ${selectedPokemonDetails.id}  ${selectedPokemonDetails.displayName.uppercase()}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                    PokemonSprite(
                        imageUrl = selectedPokemonDetails.sprites?.officialArtworkFrontDefault,
                        modifier = Modifier
                            .size(300.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .graphicsLayer { clip = true; shape = RoundedCornerShape(50.dp)  }
                            .drawBehind {
                                val borderSize = 4.dp.toPx()
                                val topLeftCornerOffset = 0f + borderSize / 2
                                val bottomRightCornerXOffset = size.width - borderSize / 2
                                val bottomRightCornerYOffset = size.height - borderSize / 2
                                drawLine(
                                    color = Red40,
                                    start = Offset(topLeftCornerOffset, 0f),
                                    end = Offset(topLeftCornerOffset, size.height / 5),
                                    strokeWidth = borderSize
                                )
                                drawLine(
                                    color = Red40,
                                    start = Offset(0f, topLeftCornerOffset),
                                    end = Offset(size.width / 5, topLeftCornerOffset),
                                    strokeWidth = borderSize
                                )
                                drawLine(
                                    color = Red40,
                                    start = Offset(bottomRightCornerXOffset, size.height),
                                    end = Offset(bottomRightCornerXOffset, size.height - size.height / 5),
                                    strokeWidth = borderSize
                                )
                                drawLine(
                                    color = Red40,
                                    start = Offset(size.width, bottomRightCornerYOffset),
                                    end = Offset(size.width - (size.width / 5), bottomRightCornerYOffset),
                                    strokeWidth = borderSize
                                )
                            }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Text(
                                text = "BASE XP",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${selectedPokemonDetails.baseExperience}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Text(
                                text = "HT",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = selectedPokemonDetails.height,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Text(
                                text = "WT",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = selectedPokemonDetails.weight,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                    selectedPokemonDetails.species?.let {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(text = it.genus, style = MaterialTheme.typography.bodyMedium)
                            Text(text = it.flavorText, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            } else {
                Text(
                    text = "Information on this pokemon could not be gathered at this time",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}