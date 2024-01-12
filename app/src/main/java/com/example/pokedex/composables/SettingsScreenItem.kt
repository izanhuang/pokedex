package com.example.pokedex.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreenItem(
    label: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLastItem: Boolean = false,
) {
    Row(
        modifier = modifier.padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(0.6f))
        Button(onClick = onClick) {
            Text(text = buttonText)
        }
    }
    if (!isLastItem) {
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondaryContainer)
    }
}