package com.resy.picsum.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resy.picsum.android.ui.navigation.Navigation.Args.IMAGE_PARAM
import com.resy.picsum.android.ui.navigation.Navigation.Routes.IMAGE
import com.resy.picsum.android.ui.navigation.Navigation.Routes.IMAGE_LIST
import com.resy.picsum.data.model.Image

@Suppress("FunctionNaming")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = IMAGE_LIST
    ) {
        composable(
            route = IMAGE_LIST
        ) {
            ImageListScreenDestination(navController)
        }

        composable(
            route = IMAGE
        ) {
            val image = navController.previousBackStackEntry?.savedStateHandle?.get<Image>(
                IMAGE_PARAM
            )
            image?.let {
                ImageScreenDestination(image)
            }
        }
    }
}

object Navigation {
    object Args {
        const val IMAGE_PARAM = "image"
    }

    object Routes {
        const val IMAGE_LIST = "image_list"
        const val IMAGE = "image"
    }
}

fun NavController.navigateToImage(image: Image) {
    currentBackStackEntry?.savedStateHandle?.set(IMAGE_PARAM, image)
    navigate(route = IMAGE)
}
