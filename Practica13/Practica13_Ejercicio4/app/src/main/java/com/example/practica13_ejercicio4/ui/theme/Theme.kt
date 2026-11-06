package com.example.practica13_ejercicio4.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VerdeOscuro,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF005141),
    onPrimaryContainer = VerdeClaro,
    secondary = AzulSecundario,
    background = FondoOscuro,
    onBackground = Color(0xFFE2E3E1),
    surface = SuperficieOscura,
    onSurface = Color(0xFFE2E3E1),
    onSurfaceVariant = Color(0xFFA0ACA7)
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
    onSurface = Color(0xFF1C1D1C),
    onSurfaceVariant = Color(0xFF53635E)
)

@Composable
fun Practica13_Ejercicio4Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}