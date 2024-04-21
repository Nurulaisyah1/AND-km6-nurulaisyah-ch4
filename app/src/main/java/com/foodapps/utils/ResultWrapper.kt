package com.foodapps.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.lang.Exception

sealed class ResultWrapper<out T>(
    val payload: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Success<out T>(data: T) : ResultWrapper<T>(data)
    class Error<out T>(exception: Exception?, data: T? = null) :
        ResultWrapper<T>(data, exception = exception)

    class Empty<out T>(data: T? = null) : ResultWrapper<T>(data)
    class Loading<out T>(data: T? = null) : ResultWrapper<T>(data)
    class Idle<out T>(data: T? = null) : ResultWrapper<T>(data)
    companion object {
        fun proceedWhen(doOnSuccess: Any) {

        }
    }
}

fun <T> ResultWrapper<T>.proceedWhen(
    doOnSuccess: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnError: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnLoading: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnEmpty: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnIdle: ((resource: ResultWrapper<T>) -> Unit)? = null,
) {
    when (this) {
        is ResultWrapper.Success -> doOnSuccess?.invoke(this)
        is ResultWrapper.Error -> doOnError?.invoke(this)
        is ResultWrapper.Loading -> doOnLoading?.invoke(this)
        is ResultWrapper.Empty -> doOnEmpty?.invoke(this)
        is ResultWrapper.Idle -> doOnIdle?.invoke(this)
    }
}

suspend fun <T> ResultWrapper<T>.suspendProceedWhen(
    doOnSuccess: (suspend (resource: ResultWrapper<T>) -> Unit)? = null,
    doOnError: (suspend (resource: ResultWrapper<T>) -> Unit)? = null,
    doOnLoading: (suspend (resource: ResultWrapper<T>) -> Unit)? = null,
    doOnEmpty: (suspend (resource: ResultWrapper<T>) -> Unit)? = null,
    doOnIdle: (suspend (resource: ResultWrapper<T>) -> Unit)? = null,
) {
    when (this) {
        is ResultWrapper.Success -> doOnSuccess?.invoke(this)
        is ResultWrapper.Error -> doOnError?.invoke(this)
        is ResultWrapper.Loading -> doOnLoading?.invoke(this)
        is ResultWrapper.Empty -> doOnEmpty?.invoke(this)
        is ResultWrapper.Idle -> doOnIdle?.invoke(this)
    }
}

fun <T> ResultWrapper<T>.proceed(
    doOnSuccess: ((resource: ResultWrapper<T>) -> Unit),
    doOnError: ((resource: ResultWrapper<T>) -> Unit),
    doOnLoading: ((resource: ResultWrapper<T>) -> Unit),
) {
    when (this) {
        is ResultWrapper.Success -> doOnSuccess.invoke(this)
        is ResultWrapper.Error -> doOnError.invoke(this)
        is ResultWrapper.Loading -> doOnLoading.invoke(this)
        else -> {}
    }
}

suspend fun <T> proceed(block: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = block.invoke()
        if (result is Collection<*> && result.isEmpty()) {
            ResultWrapper.Empty(result)
        } else {
            ResultWrapper.Success(result)
        }
    } catch (e: Exception) {
        ResultWrapper.Error<T>(exception = Exception(e))
    }
}


fun <T> proceedFlow(block: suspend () -> T): Flow<ResultWrapper<T>> {
    return flow<ResultWrapper<T>> {
        val result = block.invoke()
        emit(
            if (result is Collection<*> && result.isEmpty()) {
                ResultWrapper.Empty(result)
            } else {
                ResultWrapper.Success(result)
            }
        )
    }.catch { e ->
        emit(ResultWrapper.Error(exception = Exception(e)))
    }.onStart {
        emit(ResultWrapper.Loading())
    }
}
