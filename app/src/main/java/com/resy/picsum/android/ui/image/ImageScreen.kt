package com.resy.picsum.android.ui.image

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * The screen which displays an image.
 *
 * @param state the current state of the screen
 */
@Suppress("FunctionNaming")
@Composable
fun ImageScreen(
    state: ImageState
) {
    Column {
        Text(text = state.orientation.toString())
        Text(text = state.image.filename)
    }
}
