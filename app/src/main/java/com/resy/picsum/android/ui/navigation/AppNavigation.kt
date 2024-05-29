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

/**
 * The Navigation to be used for the application, which contains all the destinations
 */
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

/**
 * Helper objects which contains constants used by navigation
 */
object Navigation {
    /**
     * The name of the arguments to be used when navigating to a destination
     */
    object Args {
        /**
         * The name of the image argument
         */
        const val IMAGE_PARAM = "image"
    }

    /**
     * The name of the destinations to navigate to
     */
    object Routes {
        /**
         * The name of the destination for the screen with the list of images
         */
        const val IMAGE_LIST = "image_list"

        /**
         * The name of the destination for the screen which displays an image
         */
        const val IMAGE = "image"
    }
}

/**
 * Navigates to the screen displaying an image.
 *
 * @param image the image to be displayed
 */
fun NavController.navigateToImage(image: Image) {
    currentBackStackEntry?.savedStateHandle?.set(IMAGE_PARAM, image)
    navigate(route = IMAGE)
}
