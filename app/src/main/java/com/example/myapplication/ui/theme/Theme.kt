package com.example.myapplication.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val MidnightBlue = Color(0xFF1A2332)
val Champagne = Color(0xFFCBB392)
val MossGreen = Color(0xFF2D4030)
val Onyx = Color(0xFF0C0C00)
val UmberDark = Color(0xFF2B231F)
val Burgundy = Color(0xFF6E2024)

val SaveItDarkColorScheme = darkColorScheme(
    primary = Champagne,
    onPrimary = Onyx,
    secondary = MossGreen,
    onSecondary = Color.White,
    background = Onyx,
    onBackground = Color.White,
    surface = MidnightBlue,
    onSurface = Color.White,
    surfaceVariant = UmberDark
)

val SaveItLightColorScheme = lightColorScheme(
    primary = MidnightBlue,
    onPrimary = Color.White,
    secondary = Champagne,
    onSecondary = Onyx,
    background = Color(0xFFF4F1EA),
    onBackground = Onyx,
    surface = Color.White,
    onSurface = Onyx,
    surfaceVariant = Champagne
)