package com.example.pokedex.data.local

import androidx.room.*
import com.example.pokedex.data.model.pokemon.PokemonModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonModel: PokemonModel) : Long

    @Query("SELECT * FROM pokemonModel ORDER BY id")
    fun getAll(): Flow<List<PokemonModel>>

    @Delete
    suspend fun delete(pokemonModel: PokemonModel)
}