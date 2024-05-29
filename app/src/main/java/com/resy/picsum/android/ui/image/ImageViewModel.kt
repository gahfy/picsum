package com.resy.picsum.android.ui.image

import androidx.compose.runtime.State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.resy.picsum.data.model.Image
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * ViewModel which notifies the view about its current state for the image screen.
 *
 * @property image  The image that should be displayed in the view.
 * @property _state The current state of the view, which can be edited by the ViewModel
 * @property state The current state of the view that can be subscribed outside of the ViewModel
 *
 * @constructor Instantiates a new [ImageViewModel].
 *
 * @param image The image that should be displayed in the view
 */
@HiltViewModel(assistedFactory = ImageViewModel.Factory::class)
class ImageViewModel @AssistedInject constructor(
    @Assisted private val image: Image
): ViewModel() {
    private val _state: MutableState<ImageState> = mutableStateOf(
        ImageState(
            orientation = if(image.height >= image.width) Orientation.PORTRAIT else Orientation.LANDSCAPE,
            image = image
        )
    )
    val state: State<ImageState> = _state

    /**
     * Hilt interface for creating a ViewModel
     */
    @AssistedFactory
    interface Factory {
        /**
         * Returns an [ImageViewModel] with the given image and provided properties.
         *
         * @param image the image that should be included in the ViewModel.
         *
         * @return an [ImageViewModel] with the given image and provided properties
         */
        fun create(
            @Assisted image: Image
        ): ImageViewModel
    }

    companion object {
        /**
         * Provides a Factory for creating an [ImageViewModel].
         *
         * @param assistedFactory the factory for creating the ViewModel
         * @param image           the image that should be included in the ViewModel
         *
         * @return a Factory for creating an [ImageViewModel].
         */
        @Suppress("UNCHECKED_CAST", "unused")
        fun provideFactory(
            assistedFactory: Factory,
            image: Image
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(image) as T
            }
        }
    }
}
