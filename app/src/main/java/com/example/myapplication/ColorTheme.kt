package com.example.myapplication.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Tobacco = Color(0xFF1B0F07)
val Ink = Color(0xFF100603)
val Mahogany = Color(0xFF29180E)
val Umber = Color(0xFF7E6856)
val Brume = Color(0xFF594B42)
val Silt = Color(0xFF3B3535)

val BankDarkColorScheme = darkColorScheme(
    primary = Umber,
    onPrimary = Ink,
    secondary = Brume,
    onSecondary = Color.White,
    background = Tobacco,
    onBackground = Color.White,
    surface = Mahogany,
    onSurface = Color.White,
    surfaceVariant = Silt
)

val BankLightColorScheme = lightColorScheme(
    primary = Mahogany,
    onPrimary = Color.White,
    secondary = Umber,
    onSecondary = Color.White,
    background = Color(0xFFF9F6F0),
    onBackground = Tobacco,
    surface = Color.White,
    onSurface = Tobacco,
    surfaceVariant = Brume
)