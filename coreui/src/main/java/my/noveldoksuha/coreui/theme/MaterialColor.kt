package my.noveldoksuha.coreui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance

@Composable
fun ColorScheme.isLightTheme() = background.luminance() > 0.5

/**
 * Light color scheme with Material 3 surface container and fixed color roles.
 * Updated for Compose Material3 1.4.0+ with full constructor parameters.
 * 
 * Includes:
 * - Surface Container roles (Material3 1.3.0+)
 * - Fixed color roles (Material3 1.4.0+)
 */
val light_colorScheme = ColorScheme(
    // Primary colors
    primary = Grey25,
    onPrimary = Grey900,
    primaryContainer = Grey50,
    onPrimaryContainer = Grey800,
    inversePrimary = Grey900,
    
    // Secondary colors
    secondary = Grey25,
    onSecondary = Grey900,
    secondaryContainer = ColorAccent,
    onSecondaryContainer = Grey25,
    
    // Tertiary colors
    tertiary = Grey200,
    onTertiary = Grey600,
    tertiaryContainer = Grey50,
    onTertiaryContainer = Grey900,
    
    // Background & Surface
    background = Grey25,
    onBackground = Grey900,
    surface = Grey25,
    onSurface = Grey900,
    surfaceVariant = Grey50,
    onSurfaceVariant = Grey900,
    surfaceTint = Grey300,
    inverseSurface = Grey900,
    inverseOnSurface = Grey25,
    
    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey50,
    surfaceContainerLow = Grey25,
    surfaceContainerLowest = Grey0,
    surfaceContainerHigh = Grey100,
    surfaceContainerHighest = Grey200,
    
    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey100,
    surfaceDim = Grey25,
    
    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = Grey50,
    primaryFixedDim = Grey25,
    onPrimaryFixed = Grey900,
    onPrimaryFixedVariant = Grey800,
    secondaryFixed = ColorAccent,
    secondaryFixedDim = ColorAccent,
    onSecondaryFixed = Grey25,
    onSecondaryFixedVariant = Grey75,
    tertiaryFixed = Grey200,
    tertiaryFixedDim = Grey100,
    onTertiaryFixed = Grey600,
    onTertiaryFixedVariant = Grey500,
    
    // Error colors
    error = Error300,
    onError = Grey900,
    errorContainer = Error200,
    onErrorContainer = Grey800,
    
    // Outline colors
    outline = Grey800,
    outlineVariant = Grey200,
    
    // Scrim
    scrim = Grey300,
)

/**
 * Dark color scheme with Material 3 surface container and fixed color roles.
 * Updated for Compose Material3 1.4.0+ with full constructor parameters.
 * 
 * Includes:
 * - Surface Container roles (Material3 1.3.0+)
 * - Fixed color roles (Material3 1.4.0+)
 */
val dark_colorScheme = ColorScheme(
    // Primary colors
    primary = Grey900,
    onPrimary = Grey25,
    primaryContainer = Grey800,
    onPrimaryContainer = Grey50,
    inversePrimary = Grey25,
    
    // Secondary colors
    secondary = Grey900,
    onSecondary = Grey25,
    secondaryContainer = ColorAccent,
    onSecondaryContainer = Grey75,
    
    // Tertiary colors
    tertiary = Grey700,
    onTertiary = Grey300,
    tertiaryContainer = Grey600,
    onTertiaryContainer = Grey50,
    
    // Background & Surface
    background = Grey900,
    onBackground = Grey50,
    surface = Grey900,
    onSurface = Grey25,
    surfaceVariant = Grey900,
    onSurfaceVariant = Grey50,
    surfaceTint = Grey700,
    inverseSurface = Grey25,
    inverseOnSurface = Grey900,
    
    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey800,
    surfaceContainerLow = Grey700,
    surfaceContainerLowest = Grey700,
    surfaceContainerHigh = Grey900,
    surfaceContainerHighest = Grey900,
    
    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey900,
    surfaceDim = Grey800,
    
    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = Grey900,
    primaryFixedDim = Grey800,
    onPrimaryFixed = Grey25,
    onPrimaryFixedVariant = Grey50,
    secondaryFixed = ColorAccent,
    secondaryFixedDim = ColorAccent,
    onSecondaryFixed = Grey75,
    onSecondaryFixedVariant = Grey100,
    tertiaryFixed = Grey700,
    tertiaryFixedDim = Grey600,
    onTertiaryFixed = Grey300,
    onTertiaryFixedVariant = Grey400,
    
    // Error colors
    error = Error600,
    onError = Grey25,
    errorContainer = Error800,
    onErrorContainer = Grey50,
    
    // Outline colors
    outline = Grey25,
    outlineVariant = Grey700,
    
    // Scrim
    scrim = Grey800,
)

/**
 * Black color scheme with Material 3 surface container and fixed color roles.
 * Updated for Compose Material3 1.4.0+ with full constructor parameters.
 * 
 * Includes:
 * - Surface Container roles (Material3 1.3.0+)
 * - Fixed color roles (Material3 1.4.0+)
 */
val black_colorScheme = ColorScheme(
    // Primary colors
    primary = Grey1000,
    onPrimary = Grey25,
    primaryContainer = Grey900,
    onPrimaryContainer = Grey100,
    inversePrimary = Grey25,
    
    // Secondary colors
    secondary = Grey1000,
    onSecondary = Grey25,
    secondaryContainer = ColorAccent,
    onSecondaryContainer = Grey75,
    
    // Tertiary colors
    tertiary = Grey800,
    onTertiary = Grey300,
    tertiaryContainer = Grey700,
    onTertiaryContainer = Grey50,
    
    // Background & Surface
    background = Grey1000,
    onBackground = Grey50,
    surface = Grey1000,
    onSurface = Grey25,
    surfaceVariant = Grey1000,
    onSurfaceVariant = Grey50,
    surfaceTint = Grey800,
    inverseSurface = Grey25,
    inverseOnSurface = Grey1000,
    
    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey900,
    surfaceContainerLow = Grey900,
    surfaceContainerLowest = Grey800,
    surfaceContainerHigh = Grey1000,
    surfaceContainerHighest = Grey1000,
    
    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey1000,
    surfaceDim = Grey900,
    
    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = Grey1000,
    primaryFixedDim = Grey900,
    onPrimaryFixed = Grey25,
    onPrimaryFixedVariant = Grey100,
    secondaryFixed = ColorAccent,
    secondaryFixedDim = ColorAccent,
    onSecondaryFixed = Grey75,
    onSecondaryFixedVariant = Grey100,
    tertiaryFixed = Grey800,
    tertiaryFixedDim = Grey700,
    onTertiaryFixed = Grey300,
    onTertiaryFixedVariant = Grey500,
    
    // Error colors
    error = Error600,
    onError = Grey25,
    errorContainer = Error800,
    onErrorContainer = Grey50,
    
    // Outline colors
    outline = Grey25,
    outlineVariant = Grey800,
    
    // Scrim
    scrim = Grey900,
)
