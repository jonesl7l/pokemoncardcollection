package com.jonesl7l.pokemoncardcollection.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.pokemontcg.io/"

    @Provides
    @Singleton
    fun getRetrofitBuilder(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getPokemonCardService(retrofit: Retrofit): PokemonCardService = retrofit.create(PokemonCardService::class.java)
}