package com.example.pokedex.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonTypeModel(
    @SerializedName("name")
    val name: String
): Serializable

data class PokemonTypeSlot(
    @SerializedName("type")
    val pokemonType: PokemonTypeModel
)