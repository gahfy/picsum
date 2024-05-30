package com.resy.picsum.android.ui.imagelist

import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.data.model.Image

/**
 * UI state for the image list screen
 */
sealed class ImageListState {
    /**
     * UI state for the image list screen when it is loading
     */
    data object ImageListStateLoading: ImageListState()

    /**
     * UI state for the image list screen when it is successfully loaded
     */
    data class ImageListStateSuccess(
        val images: List<Image>,
        val errorMessage: Event<String>? = null,
        val onErrorActionClick: () -> Unit
    ): ImageListState()

    /**
     * UI state for the image list screen when an error occurred
     */
    data class ImageListStateError(
        val onErrorActionClick: () -> Unit
    ): ImageListState()
}
