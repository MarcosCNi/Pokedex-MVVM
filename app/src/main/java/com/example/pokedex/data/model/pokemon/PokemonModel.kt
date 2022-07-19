package com.example.pokedex.data.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "pokemonModel")
data class PokemonModel (

    val height: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val moves: List<Move>,
    val name: String,
    val species: Species,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Long
): Serializable


data class Species (
    val name: String,
    val url: String
)

data class Move (
    val move: Species,
)

data class Stat (
    @SerializedName("base_stat")
    val baseStat: Long,

    val effort: Long,
    val stat: Species
)

data class Type (
    val slot: Long,
    val type: Species
)
