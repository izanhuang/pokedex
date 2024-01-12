package com.example.pokedex.types

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex.R

sealed class Screen(val route: String, val title: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    data object Setup : Screen("setup", "Select a generation", Icons.Filled.Refresh, R.string.setup)
    data object Home : Screen("home", "", Icons.Filled.Home, R.string.home)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings, R.string.settings)
}