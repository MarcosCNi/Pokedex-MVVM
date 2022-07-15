package com.example.pokedex.ui.activity.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsPokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemon = MutableStateFlow<ResourceState<PokemonModel>>(ResourceState.Load())
    val pokemon: StateFlow<ResourceState<PokemonModel>> = _pokemon

    fun fetch(pokemonId: Int) = viewModelScope.launch {
        safeFetch(pokemonId)
    }

    private suspend fun safeFetch(pokemonId: Int){
        _pokemon.value = ResourceState.Load()
        try {
            val response = repository.getPokemon(pokemonId)
            _pokemon.value = handleResponse(response)
        }catch (t: Throwable){
            when(t){
                is IOException -> _pokemon.value = ResourceState.Error("Erro de Rede ou Conexão")
                else -> _pokemon.value = ResourceState.Error("Erro")
            }
        }
    }

    private fun handleResponse(response: Response<PokemonModel>): ResourceState<PokemonModel> {
        if (response.isSuccessful){
            response.body().let { values ->
                return ResourceState.Success(values!!)
            }
        }
        return ResourceState.Error(response.message())
    }
}