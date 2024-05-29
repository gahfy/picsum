package com.resy.picsum.android.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import com.resy.picsum.data.model.Image

/**
 * Item for the list of images.
 *
 * @param state    the state of the item
 * @param modifier the modifier to apply to the item
 */
@Suppress("FunctionNaming")
@Composable
fun ImageListItem(
    state: ImageListItemState,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable {
                state.onImageClick(state.image)
            },
        headlineContent = {
            Text(
                state.image.filename
            )
        },
        supportingContent = {
            Text(
                state.image.author
            )
        }
    )
}

@Suppress("FunctionNaming","MagicNumber")
@Composable
@Preview
fun ImageListItemPreview() {
    AppTheme {
        AppSurface {
            ImageListItem(
                ImageListItemState(
                    image = Image(0, 1000, 2000, "0.jpg", "John Doe"),
                    onImageClick = {}
                )
            )
        }
    }
}

@Suppress("FunctionNaming","MagicNumber")
@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun ImageListItemPreviewDark() {
    AppTheme {
        AppSurface {
            ImageListItem(
                ImageListItemState(
                    image = Image(0, 1000, 2000, "0.JPG", "John Doe"),
                    onImageClick = {}
                )
            )
        }
    }
}
