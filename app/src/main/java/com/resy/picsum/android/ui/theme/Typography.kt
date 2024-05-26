package com.resy.picsum.android.ui.theme

import android.content.Context
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.resy.picsum.android.R

fun defaultTypography(
    context: Context
) = Typography(
    bodyMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.shirens)!!
        ),
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)
