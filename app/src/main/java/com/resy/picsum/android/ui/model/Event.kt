package com.resy.picsum.android.ui.model

data class Event<T>(
    private val eventValue: T
) {
    private var collected = false

    fun collect(): T? {
        if(!collected) {
            collected = true
            return eventValue
        }
        return null
    }
}
