package com.resy.picsum.android.ui.image

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Suppress("FunctionNaming")
@Composable
fun ImageScreen(
    state: ImageState
) {
    Column {
        Text(text = (state as? ImageState.ImageStateSuccess)?.orientation.toString())
        Text(text = (state as? ImageState.ImageStateSuccess)?.image?.filename.toString())
    }
}
