package com.jonesl7l.pokemoncardcollection.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * App module as an object because it's more efficient than using a class
 * @Module tells us that it's a Dagger module
 * @Provides tells how it creates instance of the method return type
 * @InstallIn tells how we want to scope the objects
 * @Singleton only one instance throughout the app
 *
 * No @Inject as we don't own the classes and can't go in to annotate
 */
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