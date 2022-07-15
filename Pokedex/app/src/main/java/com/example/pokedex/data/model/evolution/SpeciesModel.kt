package com.example.pokedex.data.model.evolution

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpeciesModel (
    @SerializedName("name")
    val name: String
): Serializable