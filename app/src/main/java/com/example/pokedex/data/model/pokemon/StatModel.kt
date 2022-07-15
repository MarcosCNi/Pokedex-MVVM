package com.example.pokedex.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StatModel (
    @SerializedName("name")
    val name: String,
) : Serializable