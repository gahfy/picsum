package com.resy.picsum.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.resy.picsum.android.ui.imagelist.ImageListScreen
import com.resy.picsum.android.ui.imagelist.ImageListViewModel

/**
 * Destination element which instantiates the ViewModel and generates the view with the state from
 * the ViewModel.
 *
 * @param navController the Controller to be used for navigation
 */
@Suppress("FunctionNaming")
@Composable
fun ImageListScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<ImageListViewModel>()
    ImageListScreen(
        state = viewModel.state.value,
        onNavigateToImage = {
            image -> navController.navigateToImage(image)
        }
    )
}
