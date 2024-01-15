package com.example.pokedex.types

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int? = null,
    val icon: ImageVector,
    @StringRes val resourceId: Int
) {
    data object Setup : Screen(
        route = "setup",
        title = "Select a generation",
        icon = Icons.Filled.Home,
        resourceId = R.string.setup
    )

    data object Home :
        Screen(
            route = "home",
            title = "",
            iconId = R.drawable.twotone_catching_pokemon_24,
            icon = Icons.Filled.Home,
            resourceId = R.string.home
        )

    data object Settings :
        Screen(
            route = "settings",
            title = "Settings",
            icon = Icons.Filled.Settings,
            resourceId = R.string.settings
        )
}