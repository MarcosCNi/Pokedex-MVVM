package com.example.pokedex.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.data.remote.ServiceApi
import com.example.pokedex.utils.Constants.BASE_URL
import com.example.pokedex.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideMarvelDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PokemonDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providePokedexDao(database: PokemonDatabase) = database.pokemonDao()

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit): ServiceApi{
        return retrofit.create(ServiceApi::class.java)
    }
}