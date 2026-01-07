package org.tavioribeiro.adb_manager.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import adbmanager.composeapp.generated.resources.figtree_black_italic
import adbmanager.composeapp.generated.resources.figtree_bold
import adbmanager.composeapp.generated.resources.figtree_bold_italic
import adbmanager.composeapp.generated.resources.figtree_extrabold
import adbmanager.composeapp.generated.resources.figtree_extrabold_italic
import adbmanager.composeapp.generated.resources.figtree_italic
import adbmanager.composeapp.generated.resources.figtree_light
import adbmanager.composeapp.generated.resources.figtree_light_italic
import adbmanager.composeapp.generated.resources.figtree_medium
import adbmanager.composeapp.generated.resources.figtree_medium_italic
import adbmanager.composeapp.generated.resources.figtree_regular
import adbmanager.composeapp.generated.resources.figtree_semibold
import adbmanager.composeapp.generated.resources.figtree_semibold_italic
import adbmanager.composeapp.generated.resources.Res
import adbmanager.composeapp.generated.resources.figtree_black
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font


@OptIn(ExperimentalResourceApi::class)
@Composable
fun FigtreeFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.figtree_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(Res.font.figtree_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
        Font(Res.font.figtree_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(Res.font.figtree_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(Res.font.figtree_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(Res.font.figtree_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
        Font(Res.font.figtree_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
        Font(Res.font.figtree_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
        Font(Res.font.figtree_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(Res.font.figtree_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(Res.font.figtree_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
        Font(Res.font.figtree_extrabold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
        Font(Res.font.figtree_black, weight = FontWeight.Black, style = FontStyle.Normal),
        Font(Res.font.figtree_black_italic, weight = FontWeight.Black, style = FontStyle.Italic)
    )
}

@Composable
fun FigtreeTypography(): Typography {
    val fontFamily = FigtreeFontFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}