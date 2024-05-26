package com.resy.picsum.android.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun MainScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(
                id = R.string.hello_world
            ),
            style = MaterialTheme.typography.bodyMedium,
        )
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
            MainScreen()
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
            MainScreen()
        }
    }
}
