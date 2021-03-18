package com.jonesl7l.pokemoncardcollection.model

sealed class Result<out T : Any?> {
    
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}