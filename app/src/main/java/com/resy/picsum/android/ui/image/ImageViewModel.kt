package com.resy.picsum.android.ui.image

import androidx.compose.runtime.State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.data.model.Image
import com.resy.picsum.domain.usecase.GetCachedFileUseCase
import com.resy.picsum.framework.BuildConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel which notifies the view about its current state for the image screen.
 *
 * @property image                The image that should be displayed in the view
 * @property getCachedFileUseCase The use case to get the flow of cached files
 * @property _state               The current state of the view, which can be edited by the
 *                                ViewModel
 * @property state                The current state of the view that can be subscribed outside of
 *                                the ViewModel
 * @property imageWidth           The width in pixels of the currently displayed image
 * @property imageHeight          The height in pixels of the currently displayed image
 * @property width                The current width in pixels of the screen
 * @property height               The current height in pixels of the screen
 * @property density              The current density of the screen (as float dp*density = px)
 *
 * @constructor Instantiates a new [ImageViewModel].
 *
 * @param image                The image that should be displayed in the view to set
 * @param getCachedFileUseCase The use case to get the flow of cached files to set
 */
@HiltViewModel(assistedFactory = ImageViewModel.Factory::class)
class ImageViewModel @AssistedInject constructor(
    @Assisted private val image: Image,
    private val getCachedFileUseCase: GetCachedFileUseCase
): ViewModel() {
    private val _state: MutableState<ImageState> = mutableStateOf(
        ImageState.ImageLoadingState(
            onSize = ::onSizeAvailable
        )
    )
    val state: State<ImageState> = _state

    private var imageWidth: Int? = null
    private var imageHeight: Int? = null
    private var width: Int? = null
    private var height: Int? = null
    private var density: Float? = null

    /**
     * Called when the size of the screen is available (may be called multiple times when screen
     * size changes).
     *
     * @param width   the width in pixels of the screen
     * @param height  the height in pixels of the screen
     * @param density the density of the screen (as float, dp*density = px)
     */
    private fun onSizeAvailable(width: Int, height: Int, density: Float) {
        if(this.width != width || this.height != height || this.density != density) {
            this.width = width
            this.height = height
            this.density = density
            // If the image will be wider, we need to download it again, otherwise, we just change
            // the state
            if(width > (imageWidth?:0)) {
                downloadImage()
            } else {
                updateSuccessState(
                    orientation = null,
                    imageHeight = ((imageHeight?:1).toFloat() / (imageWidth?:1).toFloat() * width.toFloat()).toInt()
                )
            }
        }
    }

    /**
     * Called when an image needs to be downloaded.
     */
    private fun downloadImage() {
        updateLoadingState()
        val imageHeight =
            ((width?:0).toFloat() / image.width.toFloat() * image.height.toFloat()).toInt()
        viewModelScope.launch {
            try {
                val cachedFile = getCachedFileUseCase.invoke(
                    "https://picsum.photos/${width?:0}/${imageHeight}?image=${image.id}",
                    image.type
                )
                updateSuccessState(
                    cachedFile = cachedFile,
                    imageHeight = imageHeight
                )
                imageWidth = width
                this@ImageViewModel.imageHeight = imageHeight
            } catch(e: IOException) {
                if(BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                updateErrorState()
            }
        }
    }

    /**
     * Updates the success state of the screen.
     *
     * @param orientation the orientation of the image if known, or null if not yet determined
     * @param image       the image to be displayed
     * @param imageHeight the height in pixels of the image
     * @param cachedFile  the cached file containing the path to the file of the image
     */
    private fun updateSuccessState(
        orientation: Orientation? = (_state.value as? ImageState.ImageSuccessState)?.orientation,
        image: Image = (_state.value as? ImageState.ImageSuccessState)?.image?:this.image,
        imageHeight: Int,
        cachedFile: CachedFile? = (_state.value as? ImageState.ImageSuccessState)?.cachedFile
    ) {
        val orientationToApply = orientation?:
            determineOrientation(
                availableHeight = height?:0,
                imageHeight = imageHeight,
                imageWidth = width?:0
            )
        _state.value = ImageState.ImageSuccessState(
            orientation = orientationToApply,
            image = image,
            cachedFile = cachedFile,
            onSize = ::onSizeAvailable
        )
    }

    /**
     * Updates the loading state of the screen.
     */
    private fun updateLoadingState() {
        _state.value = ImageState.ImageLoadingState(
            onSize = ::onSizeAvailable
        )
    }

    /**
     * Updates the error state of the screen.
     */
    private fun updateErrorState() {
        _state.value = ImageState.ImageErrorState(
            onErrorActionClick = { downloadImage() },
            onSize = ::onSizeAvailable
        )
    }

    /**
     * Determines the orientation to apply to the image according to the given parameters.
     *
     * If height is higher than width, then it will be portrait in all cases
     *
     * If the height is not enough to display the image in landscape mode, then it will be displayed
     * in portrait.
     *
     * Example scenario :
     * Let's assume we have a screen of 1000px (width) by 1001px (height) and an image which is
     * 1000px (width) by 999px (height)
     * If we count a density of 1, let's assume 26px for the status bar, and 50px for the author,
     * this will make an available height of 1001 - 26 - 50 = 925px
     * If we try to display the image in landscape mode, then it will overflow, that's why we need
     * to add this condition
     *
     * An other advantage of this is to allow landscape mode for the application.
     *
     * @param availableHeight the available height for the screen
     * @param imageHeight the height of the image
     * @param imageWidth the width of the image
     *
     * @return the orientation to apply to the image according to the given parameters.
     */
    @Suppress("ForbiddenComment", "MagicNumber")
    private fun determineOrientation(
        availableHeight: Int,
        imageHeight: Int,
        imageWidth: Int
    ) : Orientation {
        // TODO: Calculate real height
        val supposedAuthorHeight = 50f
        val heightInAddition = (supposedAuthorHeight / (density?:0f)).toInt()
        return if(imageHeight > imageWidth || availableHeight < imageHeight + heightInAddition) {
            Orientation.PORTRAIT
        } else {
            Orientation.LANDSCAPE
        }
    }

    /**
     * Hilt interface for creating a ViewModel
     */
    @AssistedFactory
    interface Factory {
        /**
         * Returns an [ImageViewModel] with the given image and provided properties.
         *
         * @param image        the image that should be included in the ViewModel.
         *
         * @return an [ImageViewModel] with the given image and provided properties
         */
        fun create(
            @Assisted image: Image,
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
