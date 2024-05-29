package com.resy.picsum.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * The color scheme to apply when in light mode
 */
private val LightColors = lightColorScheme(
    primary = SKY_BLUE,
    onPrimary = WHITE,
    secondary = SEA_BLUE,
    onSecondary = WHITE,
    background = WHITE,
    onBackground = DARK_SEA,
    surface = WHITE,
    onSurface = DARK_SEA
)

/**
 * The color scheme to apply when in dark mode
 */
private val DarkColors = darkColorScheme(
    primary = GROUND_ORANGE,
    onPrimary = BLACK,
    secondary = GOLD_STAR,
    onSecondary = WHITE,
    background = BLACK,
    onBackground = GOLD_STAR,
    surface = BLACK,
    onSurface = GOLD_STAR
)

/**
 * The theme to apply to the application. It basically uses the color schemes if not using
 * dynamic colors.
 *
 * @param useDarkTheme     whether to return the dark mode color scheme or not
 * @param useDynamicColors whether to use material dynamic colors or not
 * @param content          the content to be applied by that theme
 */
@Suppress("FunctionNaming")
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = getColorScheme(
        useDarkTheme = useDarkTheme,
        useDynamicColors = useDynamicColors
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.secondary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkTheme
        }
    }


    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = defaultTypography(LocalContext.current)
    )
}

/**
 * The surface to be the content of the application. It basically uses the theme colors to apply
 * it as a background.
 *
 * @param modifier         the modifier to apply to the Surface.
 * @param content          the content to put in the surface
 */
@Suppress("FunctionNaming")
@Composable
fun AppSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        content = content
    )
}

/**
 * Returns the color scheme to be used by the application.
 *
 * @param useDarkTheme     whether to use dark mode colors or not
 * @param useDynamicColors whether to use material dynamic colors or not
 *
 * @return the color scheme to be used by the application
 */
@Composable
private fun getColorScheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean
) : ColorScheme {
    val context = LocalContext.current
    return when {
        (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColors
        else -> LightColors
    }
}
