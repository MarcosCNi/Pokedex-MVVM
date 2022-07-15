package com.example.pokedex.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonStatModel (
    @SerializedName("stat")
    val stat: StatModel,
    @SerializedName("id")
    val base_stat: Int
) : Serializable