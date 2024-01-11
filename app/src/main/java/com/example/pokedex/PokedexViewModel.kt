package com.example.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.prefdatastore.DataStoreManager
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.types.NameAndUrl
import com.example.pokedex.types.PokedexDetail
import com.example.pokedex.types.PokedexScreenState
import com.example.pokedex.types.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokedexViewModel @Inject constructor(
    val dataStoreManager: DataStoreManager
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
                    getGeneration(pokedexDetail.generationId.toInt())
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

    fun getGenerationDisplayName(name: String?): String? {
        if (name != null) {
            val nameParts = name.split("-")

            return "${nameParts[0].replaceFirstChar { char -> char.uppercase() }} ${nameParts[1].uppercase()}"
        }

        return null
    }

    private fun getGenerations() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleIsLoading(true)
            val response = repository.getGenerationList()
            if (response != null) {
                _pokedex.update {
                    _pokedex.value.copy(
                        generation = _pokedex.value.generation.copy(
                            totalCount = response.count,
                            generations = response.results
                        )
                    )
                }
            }
            toggleIsLoading(false)
        }
    }

    fun getGeneration(generationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.saveToDataStore(
                PokedexDetail(generationId = generationId.toString())
            )
            toggleIsLoading(true)
            val response = repository.getGeneration(generationId)
            if (response != null) {
                _pokedex.update {
                    _pokedex.value.copy(
                        generation = _pokedex.value.generation.copy(
                            id = response.id,
                            name = response.name,
                            displayName = getGenerationDisplayName(response.name)
                        )
                    )
                }
                // NOTE: Must make additional call to get each pokemon's sprites/images url
                if (response.pokemonSpecies != null) {
                    getAllPokemon(response.pokemonSpecies)
                }
            }
            toggleIsLoading(false)
        }
    }

    private suspend fun getAllPokemon(pokemonSpecies: List<NameAndUrl>) {
        var newPokemonList = emptyList<Pokemon>()
        for (pokemon in pokemonSpecies) {
            val pokemon = repository.getPokemon(pokemon.name)
            if (pokemon != null) {
                newPokemonList = newPokemonList.plus(pokemon)
            }
        }

        val orderedPokemonList = newPokemonList.sortedBy { pokemon -> pokemon.id }
        _pokedex.update {
            _pokedex.value.copy(
                pokemonList = orderedPokemonList
            )
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