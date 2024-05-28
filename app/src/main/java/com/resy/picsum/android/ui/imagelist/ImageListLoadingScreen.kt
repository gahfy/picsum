package com.resy.picsum.android.ui.imagelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme

@Suppress("FunctionNaming")
@Composable
fun ImageListLoadingScreen() {
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
fun ImageListLoadingScreenPreview() {
    AppTheme {
        AppSurface {
            ImageListLoadingScreen()
        }
    }
}
