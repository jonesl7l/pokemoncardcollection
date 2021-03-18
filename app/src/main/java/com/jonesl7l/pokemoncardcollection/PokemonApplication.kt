package com.jonesl7l.pokemoncardcollection

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Declare the application to use Hilt
 */
@HiltAndroidApp
class PokemonApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }
}