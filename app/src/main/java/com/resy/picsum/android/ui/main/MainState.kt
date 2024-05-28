package com.resy.picsum.android.ui.main

import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.data.model.Image

/**
 * UI state for the main screen
 */
sealed class MainState {
    /**
     * UI state for the main screen when it is loading
     */
    object MainStateLoading: MainState()

    /**
     * UI state for the main screen when it is successfully loaded
     */
    data class MainStateSuccess(
        val images: List<Image>,
        val errorMessage: Event<String>? = null,
        val onErrorActionClick: () -> Unit
    ): MainState()

    /**
     * UI state for the main screen when an error occurred
     */
    data class MainStateError(
        val onErrorActionClick: () -> Unit
    ): MainState()
}
