package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrandCyan,
    onPrimary = Color.Black,
    primaryContainer = BrandNavySurface,
    onPrimaryContainer = Color.White,
    secondary = BrandGold,
    onSecondary = Color.Black,
    tertiary = BrandEmerald,
    onTertiary = Color.White,
    background = BgDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    error = BrandCrimson,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BrandCobalt,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = BrandNavyDark,
    secondary = BrandGold,
    onSecondary = Color.Black,
    tertiary = BrandEmerald,
    onTertiary = Color.White,
    background = BgLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    error = BrandCrimson,
    onError = Color.White
)

@Composable
fun YolnexTheme(
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
