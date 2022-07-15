package com.example.pokedex.ui.activity.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokemon.PokemonModelResults
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
class SearchPokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _searchPokemon =
        MutableStateFlow<ResourceState<PokemonModelResults>>(ResourceState.Empty())
    val searchPokemon: StateFlow<ResourceState<PokemonModelResults>> = _searchPokemon

    fun fetch(query: String) = viewModelScope.launch {
        safeFetch(query)
    }

    private suspend fun safeFetch(query: String) {
        try {
            val response = repository.listPokemon(100000)
            _searchPokemon.value = handleResponse(response, query)
        }catch (t: Throwable){
            when(t){
                is IOException -> _searchPokemon.value = ResourceState.Error("Erro de Conecxão com a internet")
                else -> _searchPokemon.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<PokemonModelResults>, query: String): ResourceState<PokemonModelResults> {
        if(response.isSuccessful){
            response.body()?.let { values ->
                values.results = values.results.filter {
                    it.name.contains(query, true)
                }
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}