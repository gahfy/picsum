package com.resy.picsum.android.ui.imagelist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.component.ImageListItem
import com.resy.picsum.android.ui.component.ImageListItemState
import com.resy.picsum.data.model.Image
import kotlinx.coroutines.launch

/**
 * The screen with the list of images.
 *
 * @param state             the current state of the view
 * @param onNavigateToImage the action to be called when navigating to an image
 */
@Suppress("FunctionNaming")
@Composable
fun ImageListSuccessScreen(
    state: ImageListState.ImageListStateSuccess,
    onNavigateToImage: (Image) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        state.errorMessage?.collect()?.let {
            val tryAgainMessage = stringResource(id = R.string.try_again)
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = tryAgainMessage,
                    duration = SnackbarDuration.Long
                )
                if(result == SnackbarResult.ActionPerformed) {
                    state.onErrorActionClick()
                }
            }
        }
        LazyColumn(Modifier.padding(it)) {
            items(state.images) { item ->
                ImageListItem(
                    ImageListItemState(
                        image = item,
                        onImageClick = onNavigateToImage
                    )
                )
            }
        }
    }
}
