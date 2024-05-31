package com.resy.picsum.android.ui.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme

/**
 * The screen to be displayed for loading an image.
 */
@Suppress("FunctionNaming")
@Composable
fun ImageLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun ImageLoadingScreenPreview() {
    AppTheme {
        AppSurface {
            ImageLoadingScreen()
        }
    }
}
