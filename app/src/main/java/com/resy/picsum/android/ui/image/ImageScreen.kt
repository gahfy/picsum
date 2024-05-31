package com.resy.picsum.android.ui.image

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.theme.BLACK

/**
 * Screen to be displayed when displaying an image details.
 *
 * @param state          the current state of the screen
 * @param onNavigateBack the action to be performed when navigating back
 */
@Suppress("FunctionNaming")
@Composable
fun ImageScreen(
    state: ImageState,
    onNavigateBack: () -> Unit
) {
    var isBackVisible by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val localDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    LaunchedEffect(configuration) {
        state.onSizeAvailable(
            (configuration.screenWidthDp.toFloat() * localDensity.density).toInt(),
            (configuration.screenHeightDp.toFloat() * localDensity.density).toInt(),
            localDensity.density
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(indication = null, interactionSource = interactionSource) {
                isBackVisible = !isBackVisible
            }
    ) {
        Box {
            when (state) {
                is ImageState.ImageLoadingState -> ImageLoadingScreen()
                is ImageState.ImageSuccessState -> ImageSuccessScreen(state = state)
                is ImageState.ImageErrorState -> ImageErrorScreen(state = state)
            }
        }
        val imageAlpha: Float by animateFloatAsState(
            targetValue = if (isBackVisible) 1f else 0f,
            animationSpec = tween(durationMillis = 225, easing = LinearEasing),
            label = "imageAlpha"
        )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .alpha(imageAlpha)
                    .background(
                        Brush.verticalGradient(
                            listOf(BLACK, Color(0x00000000))
                        )
                    ),
            ) {
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
    }
}
