package com.example.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _pokedex = MutableStateFlow<PokedexScreenState>(
        PokedexScreenState(
            isLoading = true
        )
    )
    val pokedex: StateFlow<PokedexScreenState> = _pokedex

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> = _isNetworkErrorShown

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

    private suspend fun setPokemonList(pokemonSpecies: List<NameAndUrl>) {
        var newPokemonList = emptyList<BasicPokemon>()
        for (pokemon in pokemonSpecies) {
            val pokemon = repository.getPokemon(pokemon.name)
            if (pokemon != null) {
                val basicPokemon = BasicPokemon(
                    id = pokemon.id,
                    name = pokemon.name,
                    displayName = pokemon.displayName,
                    sprites = pokemon.sprites
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

    suspend fun getSelectedPokemonDetails(pokemonName: String) {
        val pokemon = repository.getPokemon(pokemonName)
        if (pokemon != null) {
            _pokedex.update {
                _pokedex.value.copy(
                    selectedPokemonDetails = pokemon
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

//    fun fetchPokemon() {
//        viewModelScope.launch {
//            try {
//                val pokemon = repository.getPokemon()
//                _pokemon.value = pokemon
//            } catch (error: Exception) {
//
//            }
//        }
//    }
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