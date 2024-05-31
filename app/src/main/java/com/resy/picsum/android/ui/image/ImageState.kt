package com.resy.picsum.android.ui.image

import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.data.model.Image

/**
 * State which represents the current state of an [ImageScreen].
 *
 * @property onSizeAvailable Action to be called when the size of the screen becomes available
 *
 * @constructor Instantiates a new [ImageState].
 *
 * @param onSizeAvailable Action to be called when the size of the screen becomes available to set
 */
sealed class ImageState(
    val onSizeAvailable: (width: Int, height: Int, density: Float) -> Unit
) {
    /**
     * State which represents the current state of an [ImageLoadingScreen].
     *
     * @property onSize Action to be called when the size of the screen becomes available
     *
     * @constructor Instantiates a new [ImageLoadingState].
     *
     * @param onSize Action to be called when the size of the screen becomes available to set
     */
    data class ImageLoadingState(
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)

    /**
     * State which represents the current state of an [ImageSuccessScreen].
     *
     * @property orientation The orientation of the image (Portrait or Landscape)
     * @property image       The image to be displayed in the screen
     * @property cachedFile  The cached file of the image
     * @property onSize      Action to be called when the size of the screen becomes available
     *
     * @constructor Instantiates a new [ImageSuccessState].
     *
     * @param orientation The orientation of the image (Portrait or Landscape) to set
     * @param image       The image to be displayed in the screen to set
     * @param cachedFile  The cached file of the image to set
     * @param onSize      Action to be called when the size of the screen becomes available to set
     */
    data class ImageSuccessState(
        val orientation: Orientation,
        val image: Image,
        val cachedFile: CachedFile?,
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)

    /**
     * State which represents the current state of an [ImageErrorScreen].
     *
     * @property onErrorActionClick Action to be called when the action button is clicked
     * @property onSize             Action to be called when the size of the screen becomes
     *                              available
     *
     * @constructor Instantiates a new [ImageErrorState].
     *
     * @param onErrorActionClick Action to be called when the action button is clicked
     * @param onSize             Action to be called when the size of the screen becomes available
     *                           to set
     */
    data class ImageErrorState(
        val onErrorActionClick: () -> Unit,
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)
}
