package com.example.pokedex.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonModelResults(
    @SerializedName("results")
    var results: List<PokemonResult>
) : Serializable

data class PokemonResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Serializable