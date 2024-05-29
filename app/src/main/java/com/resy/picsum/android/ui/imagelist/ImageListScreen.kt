package com.resy.picsum.android.ui.imagelist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.ui.imagelist.ImageListErrorScreen
import com.resy.picsum.android.ui.imagelist.ImageListLoadingScreen
import com.resy.picsum.android.ui.imagelist.ImageListScreen
import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import com.resy.picsum.data.model.Image


/**
 * Main screen of the application.
 *
 * @param modifier The modifier to be applied to the screen.
 */
@Suppress("FunctionNaming")
@Composable
fun ImageListScreen(
    state: ImageListState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        when(state) {
            is ImageListState.ImageListStateSuccess -> {
                ImageListSuccessScreen(state)
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
fun MainScreenPreview() {
    AppTheme {
        AppSurface {
            ImageListScreen(
                state = ImageListState.ImageListStateSuccess(
                    images = listOf(
                        Image(0, 3000, 4000, "0.jpg", "John Doe"),
                        Image(1, 3000, 4000, "1.jpg", "Alice"),
                        Image(2, 3000, 4000, "2.jpg", "Bob"),
                    ),
                    errorMessage = Event("Test"),
                    onErrorActionClick = {}
                )
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
fun MainScreenInDarkModePreview() {
    AppTheme {
        AppSurface {
            ImageListScreen(
                state = ImageListState.ImageListStateSuccess(
                    images = listOf(
                        Image(0, 3000, 4000, "0.jpg", "John Doe"),
                        Image(1, 3000, 4000, "1.jpg", "Alice"),
                        Image(2, 3000, 4000, "2.jpg", "Bob"),
                    ),
                    errorMessage = null,
                    onErrorActionClick = {}
                )
            )
        }
    }
}
