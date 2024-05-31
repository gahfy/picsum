package com.resy.picsum.android.ui.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * The screen which displays an image when it has been successfully loaded.
 *
 * @param state the current state of the screen
 */
@Suppress("FunctionNaming")
@Composable
fun ImageSuccessScreen(
    state: ImageState.ImageSuccessState
) {
    var bitmapState by remember{ mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    if(state.cachedFile != null) {
        LaunchedEffect(state.cachedFile.filename) {
            // Decode the bitmap on a background thread
            withContext(Dispatchers.IO) {
                val file = File(context.cacheDir, state.cachedFile.filename)
                val inputStream = file.inputStream()
                if (file.exists()) {
                    bitmapState = BitmapFactory.decodeStream(inputStream)
                }
                inputStream.close()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                it.size.width
            }
    ) {
        if(null != bitmapState) {
            val modifier = Modifier
                .fillMaxWidth()
                .align(
                    if (state.orientation == Orientation.LANDSCAPE) {
                        Alignment.Center
                    } else {
                        Alignment.TopStart
                    }
                )
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                val bitmap = bitmapState!!.asImageBitmap()
                Image(
                    bitmap = bitmap,
                    state.image.filename,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(state.image.width.toFloat() / state.image.height.toFloat()),
                    colorFilter = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                    text = state.image.author
                )
            }
        }
    }
}
