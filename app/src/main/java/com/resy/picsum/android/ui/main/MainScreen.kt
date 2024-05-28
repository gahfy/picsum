package com.resy.picsum.android.ui.main

import android.content.res.Configuration
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.image_list.ImageListErrorScreen
import com.resy.picsum.android.ui.image_list.ImageListLoadingScreen
import com.resy.picsum.android.ui.image_list.ImageListScreen
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
fun MainScreen(
    state: MainState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        when(state) {
            is MainState.MainStateSuccess -> {
                ImageListScreen(state)
            }
            is MainState.MainStateError -> {
                ImageListErrorScreen(
                    state = state
                )
            }
            is MainState.MainStateLoading -> {
                ImageListLoadingScreen()
            }
        }
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview(
    widthDp = 280,
    heightDp = 480
)
fun MainScreenPreview() {
    AppTheme {
        AppSurface {
            MainScreen(
                state = MainState.MainStateSuccess(
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

@Suppress("FunctionNaming")
@Composable
@Preview(
    widthDp = 280,
    heightDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun MainScreenInDarkModePreview() {
    AppTheme {
        AppSurface {
            MainScreen(
                state = MainState.MainStateSuccess(
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
