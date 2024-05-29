package com.resy.picsum.android.ui.component

import com.resy.picsum.data.model.Image

/**
 * State which represents the current state of an [ImageListItem].
 *
 * @property image        the image to be displayed in the item
 * @property onImageClick the action to be performed when an image is clicked
 *
 * @constructor Instantiates a new [ImageListItemState].
 *
 * @param image        the image to be displayed in the item to set
 * @param onImageClick the action to be performed when an image is clicked to set
 */
data class ImageListItemState(
    val image: Image,
    val onImageClick: (Image) -> Unit
)
