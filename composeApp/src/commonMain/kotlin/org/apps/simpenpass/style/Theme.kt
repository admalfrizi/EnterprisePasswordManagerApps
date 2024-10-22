package org.apps.simpenpass.style

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import resources.Res
import resources.poppins_bold
import resources.poppins_medium
import resources.poppins_regular
import resources.poppins_semibold

internal val authScreenBgColor = Brush.linearGradient(
    listOf(primaryColor, Color(0xFF023066),Color(0xFF013373),Color(0xFF003376)),
    start = Offset.Zero,
    end = Offset.Infinite
)

@Composable
fun setTypography(): Typography {
    val bold = FontFamily(
        Font(Res.font.poppins_bold, FontWeight.Bold)
    )

    val semibold = FontFamily(
        Font(Res.font.poppins_semibold, FontWeight.SemiBold)
    )

    val medium = FontFamily(
        Font(Res.font.poppins_medium, FontWeight.Bold)
    )

    val regular = FontFamily(
        Font(Res.font.poppins_regular, FontWeight.Bold)
    )

    return Typography(
        h1 = TextStyle(
            fontFamily = bold,
            fontSize = 52.sp,
            color = fontColor1
        ),
        h2 = TextStyle(
            fontFamily = medium,
            fontSize = 48.sp,
        ),
        h5 = TextStyle(
            fontFamily = bold,
            fontSize = 24.sp,
            color = fontColor1
        ),
        h6 = TextStyle(
            fontFamily = bold,
            fontSize = 14.sp,
            color = fontColor1
        ),
        subtitle1 = TextStyle(
            fontFamily = regular,
            fontSize = 12.sp,
        ),
        subtitle2 = TextStyle(
            fontFamily = medium,
            fontSize = 12.sp,
        ),
        caption = TextStyle(
            fontFamily = regular,
            fontSize = 14.sp,
        ),
        body1 = TextStyle(
            fontFamily = semibold,
            fontSize = 14.sp,
        ),
        body2 = TextStyle(
            fontFamily = medium,
            fontSize = 14.sp,
            color = fontColor1
        ),
        button = TextStyle(
            fontFamily = bold,
            fontSize = 16.sp,
            color = fontColor1
        )
    )
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
){
    MaterialTheme(
        typography = setTypography(),
        content = {
            content()
        }
    )
}