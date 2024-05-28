package com.resy.picsum.android.ui.model

/**
 * Event class which provides a value which will be available only the first time it is requestted.
 *
 * @property eventValue the original event value
 *
 * @constructor Instantiates a new [Event].
 *
 * @param eventValue the original event value to set
 */
data class Event<T>(
    private val eventValue: T
) {
    private var collected = false

    /**
     * Returns the value if it has not been collected before, or null otherwise.
     *
     * @return the valus if it has not been collected before, or null otherwise
     */
    fun collect(): T? {
        if(!collected) {
            collected = true
            return eventValue
        }
        return null
    }
}
