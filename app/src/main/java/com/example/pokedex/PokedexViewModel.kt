package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.prefdatastore.DataStoreManager
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.types.BasicPokemon
import com.example.pokedex.types.Generation
import com.example.pokedex.types.GenerationViewData
import com.example.pokedex.types.NameAndUrl
import com.example.pokedex.types.PokedexDetail
import com.example.pokedex.types.PokedexScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokedexViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val repository = PokedexRepository()

    private val _pokedex = MutableStateFlow(
        PokedexScreenState(
            isLoading = true
        )
    )
    val pokedex: StateFlow<PokedexScreenState> = _pokedex

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.getFromDataStore().collect { pokedexDetail ->
                if (pokedexDetail.generationId.isNotEmpty()) {
                    getPokemonListFromGeneration(pokedexDetail.generationId.toInt())
                } else {
                    getGenerations()
                }
            }
        }
    }

    private fun toggleIsLoading(isLoading: Boolean) {
        _pokedex.update {
            _pokedex.value.copy(
                isLoading = isLoading,
            )
        }
    }

    private fun getGenerationDisplayName(name: String): String {
        val nameParts = name.split("-")

        return "${nameParts[0].replaceFirstChar { char -> char.uppercase() }} ${nameParts[1].uppercase()}"
    }

    private suspend fun getGenerations() {
        toggleIsLoading(true)
        val response = repository.getAllGenerations()
        if (response != null) {
            val generationNamesList = response.results.map { getGenerationDisplayName(it.name) }
            _pokedex.update {
                _pokedex.value.copy(
                    generationDetails = _pokedex.value.generationDetails.copy(
                        allGenerations = generationNamesList
                    )
                )
            }
        }
        toggleIsLoading(false)
    }

    suspend fun getPokemonListFromGeneration(generationId: Int) {
        toggleIsLoading(true)
        dataStoreManager.saveToDataStore(
            PokedexDetail(generationId = generationId.toString())
        )
        val generationDetails = getGenerationDetails(generationId)
        if (generationDetails != null) {
            // NOTE: Must make additional call to get each pokemon's sprites/images url
            if (generationDetails.pokemonSpecies != null) {
                setPokemonList(generationDetails.pokemonSpecies)
            }
        }
        toggleIsLoading(false)
    }

    private suspend fun getGenerationDetails(generationId: Int): Generation? {
        val response = repository.getGeneration(generationId)
        if (response != null) {
            _pokedex.update {
                _pokedex.value.copy(
                    generationDetails = _pokedex.value.generationDetails.copy(
                        currentGenerationId = response.id,
                        currentGenerationName = response.name,
                        currentGenerationDisplayName = getGenerationDisplayName(response.name)
                    )
                )
            }
        }

        return response
    }

    private fun getPokemonIdFromUrl(url: String): Int {
        val urlPathSegments = url.split('/')
        return urlPathSegments.get(urlPathSegments.size - 2).toInt()
    }

    private suspend fun setPokemonList(pokemonSpecies: List<NameAndUrl>) {
        var newPokemonList = emptyList<BasicPokemon>()
        for (pokemon in pokemonSpecies) {
            val pokemonId = getPokemonIdFromUrl(pokemon.url)
            val maybePokemon = repository.getPokemon(pokemonId)
            if (maybePokemon != null) {
                val basicPokemon = BasicPokemon(
                    id = maybePokemon.id,
                    name = maybePokemon.name,
                    displayName = maybePokemon.displayName,
                    sprites = maybePokemon.sprites
                )
                newPokemonList = newPokemonList.plus(basicPokemon)
            }
        }

        val orderedPokemonList = newPokemonList.sortedBy { pokemon -> pokemon.id }
        _pokedex.update {
            _pokedex.value.copy(
                pokemonList = orderedPokemonList
            )
        }
    }

    suspend fun getSelectedPokemonDetails(pokemonId: Int) {
        val pokemon = repository.getPokemon(pokemonId)
        val pokemonSpecies = repository.getPokemonSpecies(pokemonId)
        if (pokemon != null) {
            _pokedex.update {
                _pokedex.value.copy(
                    selectedPokemonDetails = pokemon.copy(species = pokemonSpecies)
                )
            }
        }
    }

    fun clearSelectedPokemonDetails() {
        _pokedex.update {
            _pokedex.value.copy(
                selectedPokemonDetails = null
            )
        }
    }

    fun resetApp() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleIsLoading(true)
            dataStoreManager.clearDataStore()
            _pokedex.update {
                _pokedex.value.copy(
                    generationDetails = GenerationViewData(),
                    pokemonList = emptyList(),
                    selectedPokemonDetails = null,
                )
            }
            toggleIsLoading(false)
        }
    }
}

//class PokedexViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return PokedexViewModel(dataStoreManager) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}