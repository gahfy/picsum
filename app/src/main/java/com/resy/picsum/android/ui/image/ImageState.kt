package com.resy.picsum.android.ui.image

import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.data.model.Image

/**
 * State for screens in images
 *
 */
sealed class ImageState(
    val onSizeAvailable: (width: Int, height: Int, density: Float) -> Unit
) {
    data class ImageLoadingState(
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)

    /**
     * State which represents the current state of an [ImageSuccessScreen].
     *
     * @property orientation         The orientation of the image (Portrait or Landscape)
     * @property image               The image to be displayed in the screen
     * @property cachedFile          The cached file of the image
     *
     * @constructor Instantiates a new [ImageSuccessState].
     *
     * @param orientation         The orientation of the image (Portrait or Landscape) to set
     * @param image               The image to be displayed in the screen to set
     * @param cachedFile          The cached file of the image to set
     */
    data class ImageSuccessState(
        val orientation: Orientation,
        val image: Image,
        val cachedFile: CachedFile?,
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)

    data class ImageErrorState(
        val onErrorActionClick: () -> Unit,
        val onSize: (width: Int, height: Int, density: Float) -> Unit
    ): ImageState(onSize)
}
