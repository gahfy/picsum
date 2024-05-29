package com.resy.picsum.android.ui.image

import com.resy.picsum.data.model.Image

/**
 * State which represents the current state of an [ImageScreen].
 *
 * @property orientation The orientation of the image (Portrait or Landscape)
 * @property image The image to be displayed in the screen
 *
 * @constructor Instantiates a new [ImageState].
 *
 * @param orientation The orientation of the image (Portrait or Landscape) to set
 * @param image The image to be displayed in the screen to set
 */
data class ImageState(
    val orientation: Orientation,
    val image: Image
)
