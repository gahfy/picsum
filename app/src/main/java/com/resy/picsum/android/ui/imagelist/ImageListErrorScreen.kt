package com.resy.picsum.android.ui.imagelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme

@Suppress("FunctionNaming")
@Composable
fun ImageListErrorScreen(
    state: ImageListState.ImageListStateError
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Can't load list of images"
                )

                TextButton(onClick = state.onErrorActionClick) {
                    Text(
                        text = stringResource(id = R.string.try_again).uppercase()
                    )
                }
            }
        }
    }
}

@Suppress("FunctionNaming")
@Preview
@Composable
fun ImageListErrorScreenPreview() {
    AppTheme {
        AppSurface {
            ImageListErrorScreen(
                state = ImageListState.ImageListStateError {  }
            )
        }
    }
}
