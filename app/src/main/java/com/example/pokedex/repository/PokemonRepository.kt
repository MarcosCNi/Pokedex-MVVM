package com.example.pokedex.repository

import com.example.pokedex.data.local.PokemonDao
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.remote.ServiceApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: PokemonDao,
) {
    suspend fun listPokemon(limit: Int) = api.listPokemons(limit)

    suspend fun getPokemon(number: Int) = api.getPokemon(number)

    suspend fun getEvolutions(id: Int) = api.getEvolutions(id)

    suspend fun insert(pokemonModel: PokemonModel) = dao.insert(pokemonModel)
    fun getAll() = dao.getAll()
    suspend fun delete(pokemonModel: PokemonModel) = dao.delete(pokemonModel)
}