package com.example.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.types.PokedexScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexViewModel() : ViewModel() {
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
        getGenerations()
    }

    private fun getGenerations() {
        viewModelScope.launch {
            val response = repository.getGenerationList()
            if (response != null) {
                _pokedex.update {
                    _pokedex.value.copy(
                        generationCount = response.count,
                        generations = response.results,
                    )
                }
            }
            _pokedex.update {
                _pokedex.value.copy(
                    isLoading = false,
                )
            }
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
