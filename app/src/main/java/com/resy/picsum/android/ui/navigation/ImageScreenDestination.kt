package com.resy.picsum.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.resy.picsum.android.ui.image.ImageScreen
import com.resy.picsum.android.ui.image.ImageViewModel
import com.resy.picsum.data.model.Image

/**
 * Destination element which instantiates the ViewModel and generates the view with the state from
 * the ViewModel.
 *
 * @param image         the image to set to the ViewModel
 * @param navController the Controller to be used for navigation
 */
@Suppress("FunctionNaming")
@Composable
fun ImageScreenDestination(image: Image, navController: NavController) {
    val viewModel = hiltViewModel<ImageViewModel, ImageViewModel.Factory>(
        creationCallback = {
            factory -> factory.create(image)
        }
    )
    ImageScreen(
        state = viewModel.state.value,
        onNavigateBack = { navController.popBackStack() }
    )
}
