package com.example.pokedex.data.local

import androidx.room.TypeConverter
import com.example.pokedex.data.model.pokemon.Move
import com.example.pokedex.data.model.pokemon.Species
import com.example.pokedex.data.model.pokemon.Stat
import com.example.pokedex.data.model.pokemon.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverter {

    @TypeConverter
    fun fromTypesSlot(typeSlot: List<Type>): String = Gson().toJson(typeSlot)

    @TypeConverter
    fun toTypesSlot(typeSlot: String): List<Type> =
        Gson().fromJson(typeSlot, object : TypeToken<List<Type>>() {}.type)

    @TypeConverter
    fun fromMove(move: List<Move>): String = Gson().toJson(move)

    @TypeConverter
    fun toMove(move: String): List<Move> =
        Gson().fromJson(move, object : TypeToken<List<Move>>() {}.type)

    @TypeConverter
    fun fromStats(stats: List<Stat>): String = Gson().toJson(stats)

    @TypeConverter
    fun toStats(stats: String): List<Stat> =
        Gson().fromJson(stats, object : TypeToken<List<Stat>>() {}.type)

    @TypeConverter
    fun fromSpecies(specie: Species): String = Gson().toJson(specie)

    @TypeConverter
    fun toSpecies(species: String): Species =
        Gson().fromJson(species, Species::class.java)
}
