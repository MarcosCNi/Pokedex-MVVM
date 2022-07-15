package com.example.pokedex.data.model.evolution

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChainLinkModel (
    @SerializedName("species")
    val details: SpeciesModel,
    @SerializedName("evolves_to")
    val evolvesTo: ChainLinkModel
): Serializable
