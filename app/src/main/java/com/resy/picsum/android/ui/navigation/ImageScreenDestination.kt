package com.resy.picsum.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.resy.picsum.android.ui.image.ImageScreen
import com.resy.picsum.android.ui.image.ImageViewModel
import com.resy.picsum.data.model.Image

@Suppress("FunctionNaming")
@Composable
fun ImageScreenDestination(image: Image) {
    val viewModel = hiltViewModel<ImageViewModel, ImageViewModel.Factory>(
        creationCallback = { factory -> factory.create(image) }
    )
    ImageScreen(
        state = viewModel.state.value
    )
}
