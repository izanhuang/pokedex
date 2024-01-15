package com.example.pokedex.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.PokedexViewModel
import com.example.pokedex.types.Screen
import com.example.pokedex.ui.theme.ClassicRed
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PokedexScreen(modifier: Modifier = Modifier, viewModel: PokedexViewModel = viewModel()) {
    val pokedex by viewModel.pokedex.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val startScreen = if (pokedex.generationDetails.currentGenerationId == null) {
        Screen.Setup
    } else {
        Screen.Home
    }

    val isSetupCompleted =
        !pokedex.isLoading && currentRoute != Screen.Setup.route &&
                pokedex.generationDetails.currentGenerationId != null

    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            if (isSetupCompleted) {
                TopBar(
                    currentRoute = currentRoute,
                    homeScreenTitle = pokedex.generationDetails.currentGenerationDisplayName,
                    homeScreenLabel = "NO. ${pokedex.pokemonList.first().id} - ${pokedex.pokemonList.last().id}",
                )
            }
        },
        bottomBar = {
            if (isSetupCompleted) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) {
        if (pokedex.isLoading) {
            if (isDarkMode) {
                systemUiController.setSystemBarsColor(
                    color = Color.Black
                )
            } else {
                systemUiController.setSystemBarsColor(
                    color = ClassicRed
                )
            }
            LoadingScreen(modifier = Modifier.fillMaxSize())
        } else {
            systemUiController.setSystemBarsColor(
                color = MaterialTheme.colorScheme.primary
            )
            MyAppNavHost(
                allGenerations = pokedex.generationDetails.allGenerations,
                pokemonList = pokedex.pokemonList,
                onGenerationSelect = { generationId ->
                    viewModel.getPokemonListFromGeneration(
                        generationId
                    )
                    navController.navigate(Screen.Home.route)
                },
                onResetAppClick = {
                    viewModel.resetApp()
                    navController.navigate(Screen.Setup.route)
                },
                onUpdatePokemonListClick = {
                    pokedex.generationDetails.currentGenerationId?.let { it ->
                        viewModel.getPokemonListFromGeneration(
                            it
                        )
                    }
                },
                navController = navController,
                startScreen = startScreen,
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}