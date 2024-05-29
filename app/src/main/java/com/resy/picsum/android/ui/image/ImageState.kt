package com.resy.picsum.android.ui.image

import com.resy.picsum.data.model.Image

sealed class ImageState {
    data class ImageStateSuccess(
        val orientation: Orientation,
        val image: Image
    ): ImageState()
}
