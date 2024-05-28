package com.resy.picsum.android.ui.theme

import android.content.Context
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.resy.picsum.android.R

/**
 * Returns the Typography to be used by the application.
 *
 * @param context Context in which the application is running.
 *
 * @return the Typography to be used by the application
 */
fun defaultTypography(
    context: Context
): Typography {
    val robotoRegular = ResourcesCompat.getFont(context, R.font.roboto_regular)?.let {
        FontFamily(typeface = it)
    }?: FontFamily.SansSerif
    val robotoMedium = ResourcesCompat.getFont(context, R.font.roboto_medium)?.let {
        FontFamily(typeface = it)
    }?: FontFamily.SansSerif
    val shirens = ResourcesCompat.getFont(context, R.font.shirens)?.let {
        FontFamily(typeface = it)
    }?: FontFamily.SansSerif

    return Typography(

        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = robotoRegular,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),

        bodyLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontFamily = robotoMedium,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),

        labelLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontFamily = shirens,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}
