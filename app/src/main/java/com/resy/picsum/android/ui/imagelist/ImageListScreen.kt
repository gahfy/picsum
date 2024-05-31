package com.resy.picsum.android.ui.imagelist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import com.resy.picsum.data.model.Image


/**
 * Screen displaying to be used for the list of images.
 *
 * @param state             the state of the image list screen
 * @param onNavigateToImage the action to be called when navigating to an image
 */
@Suppress("FunctionNaming")
@Composable
fun ImageListScreen(
    state: ImageListState,
    onNavigateToImage: (Image) -> Unit
) {
    Box {
        when(state) {
            is ImageListState.ImageListStateSuccess -> {
                ImageListSuccessScreen(state, onNavigateToImage)
            }
            is ImageListState.ImageListStateError -> {
                ImageListErrorScreen(
                    state = state
                )
            }
            is ImageListState.ImageListStateLoading -> {
                ImageListLoadingScreen()
            }
        }
    }
}

@Suppress("FunctionNaming","MagicNumber")
@Composable
@Preview(
    widthDp = 280,
    heightDp = 480
)
fun ImageListScreenPreview() {
    AppTheme {
        AppSurface {
            ImageListScreen(
                state = ImageListState.ImageListStateSuccess(
                    images = listOf(
                        Image(0, 3000, 4000, "0.jpg", "John Doe", "jpg"),
                        Image(1, 3000, 4000, "1.jpg", "Alice", "jpg"),
                        Image(2, 3000, 4000, "2.jpg", "Bob", "jpg"),
                    ),
                    errorMessage = Event("Test"),
                    onErrorActionClick = {}
                ),
                onNavigateToImage = {}
            )
        }
    }
}

@Suppress("FunctionNaming","MagicNumber")
@Composable
@Preview(
    widthDp = 280,
    heightDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun ImageListScreenInDarkModePreview() {
    AppTheme {
        AppSurface {
            ImageListScreen(
                state = ImageListState.ImageListStateSuccess(
                    images = listOf(
                        Image(0, 3000, 4000, "0.jpg", "John Doe", "jpg"),
                        Image(1, 3000, 4000, "1.jpg", "Alice", "jpg"),
                        Image(2, 3000, 4000, "2.jpg", "Bob", "jpg"),
                    ),
                    errorMessage = null,
                    onErrorActionClick = {}
                ),
                onNavigateToImage = {}
            )
        }
    }
}
