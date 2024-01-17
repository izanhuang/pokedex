package com.example.pokedex.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.PokedexViewModel
import com.example.pokedex.types.Screen
import com.example.pokedex.ui.theme.ClassicRed
import com.example.pokedex.ui.theme.Red40
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScreen(modifier: Modifier = Modifier, viewModel: PokedexViewModel = viewModel()) {
    val pokedex by viewModel.pokedex.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
    val startScreen =
        if (pokedex.generationDetails.currentGenerationId == null) Screen.Setup else Screen.Home
    val isSetupCompleted =
        !pokedex.isLoading && currentRoute != Screen.Setup.route &&
                pokedex.generationDetails.currentGenerationId != null

    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()

    LaunchedEffect(pokedex.isLoading) {
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
        } else {
            systemUiController.setSystemBarsColor(
                color = Red40
            )
        }
    }

    Scaffold(
        topBar = {
            if (isSetupCompleted) {
                val homeScreenLabel = if (pokedex.pokemonList.isNotEmpty()) {
                    "NO. ${pokedex.pokemonList.first().id} - ${pokedex.pokemonList.last().id}"
                } else {
                    null
                }
                TopBar(
                    currentRoute = currentRoute,
                    homeScreenTitle = pokedex.generationDetails.currentGenerationDisplayName,
                    homeScreenLabel = homeScreenLabel,
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
    ) { paddingValues ->
        if (pokedex.isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        } else {
            MyAppNavHost(
                allGenerations = pokedex.generationDetails.allGenerations,
                pokemonList = pokedex.pokemonList,
                onGenerationSelect = { generationId ->
                    scope.launch(Dispatchers.IO) {
                        viewModel.getPokemonListFromGeneration(
                            generationId
                        )
                    }
                    navController.navigate(Screen.Home.route)
                },
                onPokemonCardClick = { pokemonId ->
                    scope.launch {
                        viewModel.getSelectedPokemonDetails(pokemonId)
                        showBottomSheet = true
                    }
                },
                onResetAppClick = {
                    viewModel.resetApp()
                    navController.navigate(Screen.Setup.route)
                },
                onUpdatePokemonListClick = {
                    pokedex.generationDetails.currentGenerationId?.let {
                        scope.launch(Dispatchers.IO) {
                            viewModel.getPokemonListFromGeneration(
                                it
                            )
                        }
                    }
                },
                navController = navController,
                startScreen = startScreen,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            if (showBottomSheet) {
                PokemonCardDetailsBottomSheet(
                    selectedPokemonDetails = pokedex.selectedPokemonDetails,
                    sheetState = sheetState,
                    onOutsideClick = {
                        showBottomSheet = false
                        viewModel.clearSelectedPokemonDetails()
                    })
            }
        }
    }
}