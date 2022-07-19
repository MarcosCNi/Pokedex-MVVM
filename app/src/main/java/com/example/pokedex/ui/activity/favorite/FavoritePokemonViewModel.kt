package com.example.pokedex.ui.activity.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.model.pokemon.PokemonModelResults
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FavoritePokemonViewModel @Inject constructor(
  private val repository: PokemonRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<ResourceState<List<PokemonModel>>>(ResourceState.Empty())
    val favorites: StateFlow<ResourceState<List<PokemonModel>>> = _favorites

    private val _list = MutableStateFlow<ResourceState<PokemonModelResults>>(ResourceState.Empty())
    val list: StateFlow<ResourceState<PokemonModelResults>> = _list

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        repository.getAll().collectLatest { pokemonList ->
            if(pokemonList.isNullOrEmpty()){
                _favorites.value = ResourceState.Empty()
            }else{
                _favorites.value = ResourceState.Success(pokemonList)
                safeFetch()
            }
        }
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.listPokemon(100000)
            _list.value = handleResponse(response)
        }catch (t: Throwable){
            when(t){
                is IOException -> _list.value = ResourceState.Error("Erro de Conecxão com a internet")
                else -> _list.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<PokemonModelResults>): ResourceState<PokemonModelResults> {
        if(response.isSuccessful){
            response.body()?.let { values ->
                    _favorites.value.data?.map { pokemonModel ->
                        values.results = values.results.filter {
                            it.name.contains(pokemonModel.name, true)
                    }
                }
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

    fun delete(pokemonModel: PokemonModel) = viewModelScope.launch {
        repository.delete(pokemonModel)
    }
}