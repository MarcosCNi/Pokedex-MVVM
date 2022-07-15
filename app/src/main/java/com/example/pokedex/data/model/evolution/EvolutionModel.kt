package com.example.pokedex.data.model.evolution

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EvolutionModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("chain")
    val evolutions: ChainLinkModel
): Serializable
