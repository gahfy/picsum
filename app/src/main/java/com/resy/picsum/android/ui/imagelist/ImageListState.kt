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
     * UI state for the image list screen when it is successfully loaded.
     *
     * @property images             the list of images to display in the screen
     * @property errorMessage       the error message to be displayed
     * @property onErrorActionClick the action to be performed when clicking on an error action
     *
     * @constructor Instantiates a new [ImageListStateSuccess].
     *
     * @param images             the list of images to display in the screen to set
     * @param errorMessage       the error message to be displayed to set
     * @param onErrorActionClick the action to be performed when clicking on an error action to set
     */
    data class ImageListStateSuccess(
        val images: List<Image>,
        val errorMessage: Event<String>? = null,
        val onErrorActionClick: () -> Unit
    ): ImageListState()

    /**
     * UI state for the image list screen when an error occurred.
     *
     * @property onErrorActionClick the action to be performed when clicking on an error action
     *
     * @constructor Instantiates a new [ImageListStateError].
     *
     * @param onErrorActionClick the action to be performed when clicking on an error action to set
     */
    data class ImageListStateError(
        val onErrorActionClick: () -> Unit
    ): ImageListState()
}
