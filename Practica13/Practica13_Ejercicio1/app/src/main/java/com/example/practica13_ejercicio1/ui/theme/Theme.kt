package com.example.practica13_ejercicio1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VerdeOscuro,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF005141),
    onPrimaryContainer = VerdeClaro,
    secondary = AzulSecundario,
    background = FondoOscuro,
    onBackground = Color(0xFFE2E3E1),
    surface = SuperficieOscura,
    onSurface = Color(0xFFE2E3E1)
)

private val LightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    onPrimary = Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = Color(0xFF002019),
    secondary = AzulSecundario,
    background = FondoClaro,
    onBackground = Color(0xFF1C1D1C),
    surface = Color.White,
    onSurface = Color(0xFF1C1D1C)
)

@Composable
fun Practica13_Ejercicio1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}