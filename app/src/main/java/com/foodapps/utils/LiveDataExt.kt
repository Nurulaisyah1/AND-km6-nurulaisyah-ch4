package com.foodapps.utils

class LiveDataExt<T> { // Remove 'abstract' modifier
    private val observers = mutableListOf<(T) -> Unit>()
    private var value: T? = null

    fun observe(observer: (T) -> Unit) {
        observers.add(observer)
        value?.let(observer)
    }

    fun setValue(newValue: T) {
        value = newValue
        notifyObservers()
    }

    private fun notifyObservers() {
        value?.let { data ->
            observers.forEach { observer ->
                observer(data)
            }
        }
    }
}
