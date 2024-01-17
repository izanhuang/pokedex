package com.example.pokedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.types.Pokemon

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
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            if (selectedPokemonDetails != null) {
                Text(selectedPokemonDetails.displayName.uppercase())
                Text("base exp: ${selectedPokemonDetails.baseExperience}")
                Text("height: ${selectedPokemonDetails.height}")
                Text("weight: ${selectedPokemonDetails.weight}")
                selectedPokemonDetails.species?.let {
                    Text(it.genus)
                    Text(it.flavorText)
                }
            } else {
                Text("Information on this pokemon could not be gathered at this time")
            }
        }
    }
}