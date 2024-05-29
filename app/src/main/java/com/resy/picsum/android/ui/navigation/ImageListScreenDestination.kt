package com.resy.picsum.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.resy.picsum.android.ui.imagelist.ImageListScreen
import com.resy.picsum.android.ui.imagelist.ImageListViewModel

@Suppress("FunctionNaming")
@Composable
fun ImageListScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<ImageListViewModel, ImageListViewModel.Factory> { factory ->
        factory.create { image ->
            navController.navigateToImage(image)
        }
    }
    ImageListScreen(
        state = viewModel.state.value
    )
}
