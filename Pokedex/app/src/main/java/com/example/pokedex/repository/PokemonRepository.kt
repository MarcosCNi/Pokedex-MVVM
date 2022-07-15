package com.example.pokedex.repository

import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.remote.ServiceApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun listPokemon(limit: Int) = api.listPokemons(limit)

    suspend fun getPokemon(number: Int) = api.getPokemon(number)

    suspend fun getEvolutions(id: Int) = api.getEvolutions(id)
}