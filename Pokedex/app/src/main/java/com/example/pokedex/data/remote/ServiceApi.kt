package com.example.pokedex.data.remote

import com.example.pokedex.data.model.evolution.EvolutionModel
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.model.pokemon.PokemonModelResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int? = null
    ): Response<PokemonModelResults>

    @GET("pokemon/{number}")
    suspend fun getPokemon(@Path("number")number: Int): Response<PokemonModel>

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutions(
        @Path(
            value = "id",
            encoded = true
        ) id: Int
    ): Response<EvolutionModel>
}