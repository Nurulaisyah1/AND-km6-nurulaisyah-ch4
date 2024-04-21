package com.foodapps.data.source.network

sealed class ResponseWrapper<out T> {
    data class Success<out T>(val data: T) : ResponseWrapper<T>()
    data class Error(val exception: Exception) : ResponseWrapper<Nothing>()
    object Loading : ResponseWrapper<Nothing>()
}