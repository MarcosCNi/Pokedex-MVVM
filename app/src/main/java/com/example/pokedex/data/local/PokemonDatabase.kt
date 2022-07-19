package com.example.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.data.model.pokemon.PokemonModel

@Database(entities = [PokemonModel::class], version = 1, exportSchema = false)
@TypeConverters(PokemonConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}