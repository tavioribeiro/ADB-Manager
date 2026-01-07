package org.tavioribeiro.adb_manager.core_ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val color1: Color,
    val color2: Color,
    val color3: Color,
    val color4: Color,
    val color5: Color,
    val color6: Color,
    val color7: Color,
    val color8: Color,
    val color9: Color,
    val color10: Color,

    val onColor1: Color,
    val onColor2: Color,
    val onColor3: Color,
    val onColor4: Color,
    val onColor5: Color,
    val onColor6: Color,
    val onColor7: Color,
    val onColor8: Color,
    val onColor9: Color,
    val onColor10: Color,
)

val darkColors = AppColors(
    color1 = Color(0xFF121212),

    color2 = Color(0xFF1E1E1E),   // Cinza levemente mais claro
    color3 = Color(0xFF303030),   // Cinza bem escuro
    color4 = Color(0xFF484848),   // Cinza escuro
    color5 = Color(0xFF606060),   // Cinza médio-escuro
    color6 = Color(0xFF787878),   // Cinza médio

    color7 = Color(0xFF05C958),   // Verde vibrante
    color8 = Color(0xFF0E163D),   // Azul Escuro profundo
    color9 = Color(0xFF69E09A),   // Verde claro
    color10 = Color(0xFFFFD24D),  // Amarelo suave

    onColor1 = Color(0xFFE4E4E4), // Cinza quase branco
    onColor2 = Color(0xFFDCDCDC), // Cinza claro
    onColor3 = Color(0xFFD2D2D2), // Cinza claro suave
    onColor4 = Color(0xFFC8C8C8), // Cinza claro mais quente
    onColor5 = Color(0xFFBEBEBE), // Cinza médio-claro
    onColor6 = Color(0xFFE4E4E4), // Cinza quase branco

    onColor7 = Color(0xFF0E163D), // Azul escuro sobre o verde
    onColor8 = Color(0xFFFFFFFF), // Branco sobre o azul escuro
    onColor9 = Color(0xFF1A1A1A), // Preto suave
    onColor10 = Color(0xFF1A1A1A) // Preto suave
)

val lightColors = AppColors(
    color1 = Color(0xFFFFFFFF),   // Branco
    color2 = Color(0xFFF5F5F5),   // Cinza muito claro
    color3 = Color(0xFFEBEBEB),   // Cinza claro
    color4 = Color(0xFFDCDCDC),   // Cinza claro médio
    color5 = Color(0xFFC8C8C8),   // Cinza médio claro
    color6 = Color(0xFFBEBEBE),   // Cinza médio

    color7 = Color(0xFF05C958),   // Verde vibrante
    color8 = Color(0xFF0E163D),   // Azul Escuro
    color9 = Color(0xFF69E09A),   // Verde claro
    color10 = Color(0xFFFFD24D),  // Amarelo suave

    onColor1 = Color(0xFF1A1A1A), // Preto suave
    onColor2 = Color(0xFF1A1A1A), // Preto suave
    onColor3 = Color(0xFF1A1A1A), // Preto suave
    onColor4 = Color(0xFF1A1A1A), // Preto suave
    onColor5 = Color(0xFF1A1A1A), // Preto suave
    onColor6 = Color(0xFF1A1A1A), // Preto suave

    onColor7 = Color(0xFFFFFFFF), // Branco
    onColor8 = Color(0xFFFFFFFF), // Branco
    onColor9 = Color(0xFF1A1A1A), // Preto suave
    onColor10 = Color(0xFF1A1A1A) // Preto suave
)