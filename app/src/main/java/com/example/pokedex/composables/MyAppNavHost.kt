package com.example.pokedex.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.types.BasicPokemon
import com.example.pokedex.types.Screen

@Composable
fun MyAppNavHost(
    allGenerations: List<String>,
    pokemonList: List<BasicPokemon>,
    onGenerationSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startScreen: Screen = Screen.Setup,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startScreen.route
    ) {
        composable("setup") {
            SetupScreen(
                allGenerations = allGenerations,
                onGenerationSelect = { generationId ->
                    onGenerationSelect(
                        generationId
                    )
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        composable(route = "home",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            HomeScreen(
                pokemonList = pokemonList, modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        composable(
            route = "settings"
        ) { SettingsScreen() }
    }
}