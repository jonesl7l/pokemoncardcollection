package com.jonesl7l.pokemoncardcollection.utils

import android.content.Context
import android.text.SpannableString
import android.view.View
import com.jonesl7l.pokemoncardcollection.PokemonApplication
import com.jonesl7l.pokemoncardcollection.R
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.InputStream

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String?.orNotSet(): String = this ?: appContext().getString(R.string.generic_not_set)

fun Int?.orZero(): Int = this ?: 0

fun appContext(): Context = PokemonApplication.appContext

infix fun <T> Boolean.then(param: T): T? = if (this) param else null

fun getLottieJson(reference: String?): String? {
    if (reference == null) {
        Timber.e("getLottieJson reference = null")
        return null
    }
    var file: InputStream? = null
    try {
        file = appContext().assets?.open("lottie/$reference.json")
    } catch (exception: FileNotFoundException) {
        Timber.e(exception)
    }
    return (file != null) then file?.bufferedReader()?.readText()
}

fun getResId(context: Context, resName: String, type: String): Int {
    return try {
        context.resources.getIdentifier(resName, type, context.packageName)
    } catch (exception: Exception) {
        Timber.e(exception)
        0
    }
}