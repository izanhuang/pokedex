package com.example.pokedex.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.pokedex.types.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        val items = listOf(
            Screen.Home, Screen.Settings
        )

        Row {
            items.forEach { screen ->
                val isRouteActive = currentRoute == screen.route
                val contentColor = if (isRouteActive) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }

                BottomNavigationItem(
                    selected = isRouteActive,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (screen.iconId != null) {
                            Icon(
                                painterResource(screen.iconId),
                                contentDescription = stringResource(screen.resourceId),
                                tint = contentColor
                            )
                        } else {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = stringResource(screen.resourceId),
                                tint = contentColor
                            )
                        }
                    },
                )
            }
        }
    }
}