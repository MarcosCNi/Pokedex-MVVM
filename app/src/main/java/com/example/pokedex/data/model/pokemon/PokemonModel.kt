package com.example.pokedex.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonModel (

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("stats")
    val stats: List<PokemonStatModel>? = null,
    @SerializedName("types")
    val types: List<PokemonTypeSlot>? = null,
): Serializable
{
}
