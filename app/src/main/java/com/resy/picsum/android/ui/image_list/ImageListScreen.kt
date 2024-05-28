package com.resy.picsum.android.ui.image_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.component.ImageListItem
import com.resy.picsum.android.ui.main.MainState
import kotlinx.coroutines.launch

@Composable
fun ImageListScreen(
    state: MainState.MainStateSuccess
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
                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = tryAgainMessage,
                    duration = SnackbarDuration.Long
                )
            }
        }
        LazyColumn(Modifier.padding(it)) {
            items(state.images) { item ->
                ImageListItem(image = item)
            }
        }
    }
}