package my.noveldokusha.coreui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance

@Composable
fun ColorScheme.isLightTheme() = background.luminance() > 0.5

/**
 * Modern Light Color Scheme with Material 3 design principles.
 * Features:
 * - Subtle off-white backgrounds for reduced eye strain
 * - Soft surface containers with tonal elevation
 * - Clear contrast for accessibility
 * - Modern neutral palette with subtle warm undertones
 */
val light_colorScheme = ColorScheme(
    // Primary colors - Using subtle accent as primary
    primary = ColorAccent,
    onPrimary = Grey0,
    primaryContainer = ColorAccent.copy(alpha = 0.12f),
    onPrimaryContainer = ColorAccent.copy(alpha = 0.8f),
    inversePrimary = Grey200,

    // Secondary colors
    secondary = ColorAccent.copy(alpha = 0.8f),
    onSecondary = Grey0,
    secondaryContainer = ColorAccent.copy(alpha = 0.2f),
    onSecondaryContainer = ColorAccent.copy(alpha = 0.9f),

    // Tertiary colors - Warm accent for variety
    tertiary = Info500,
    onTertiary = Grey0,
    tertiaryContainer = Info200,
    onTertiaryContainer = Info900,

    // Background & Surface - Off-white for reduced eye strain
    background = Grey25,
    onBackground = Grey900,
    surface = Grey25,
    onSurface = Grey900,
    surfaceVariant = Grey50,
    onSurfaceVariant = Grey700,
    surfaceTint = ColorAccent.copy(alpha = 0.1f),
    inverseSurface = Grey800,
    inverseOnSurface = Grey25,

    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey50,
    surfaceContainerLow = Grey25,
    surfaceContainerLowest = Grey0,
    surfaceContainerHigh = Grey100,
    surfaceContainerHighest = Grey200,

    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey100,
    surfaceDim = Grey50,

    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = ColorAccent.copy(alpha = 0.15f),
    primaryFixedDim = ColorAccent.copy(alpha = 0.25f),
    onPrimaryFixed = ColorAccent.copy(alpha = 0.9f),
    onPrimaryFixedVariant = ColorAccent,
    secondaryFixed = Success200,
    secondaryFixedDim = Success300,
    onSecondaryFixed = Success900,
    onSecondaryFixedVariant = Success700,
    tertiaryFixed = Info200,
    tertiaryFixedDim = Info300,
    onTertiaryFixed = Info900,
    onTertiaryFixedVariant = Info700,

    // Error colors
    error = Error300,
    onError = Grey0,
    errorContainer = Error100,
    onErrorContainer = Error900,

    // Outline colors - Subtle for modern look
    outline = Grey400,
    outlineVariant = Grey200,

    // Scrim
    scrim = Grey500.copy(alpha = 0.5f),
)

/**
 * Modern Dark Color Scheme with Material 3 design principles.
 * Features:
 * - True dark surfaces for OLED displays
 * - Reduced contrast for comfortable night reading
 * - Muted accent colors to prevent eye strain
 */
val dark_colorScheme = ColorScheme(
    // Primary colors
    primary = ColorAccent.copy(alpha = 0.9f),
    onPrimary = Grey0,
    primaryContainer = ColorAccent.copy(alpha = 0.25f),
    onPrimaryContainer = ColorAccent.copy(alpha = 0.9f),
    inversePrimary = Grey700,

    // Secondary colors
    secondary = ColorAccent.copy(alpha = 0.85f),
    onSecondary = Grey0,
    secondaryContainer = ColorAccent.copy(alpha = 0.3f),
    onSecondaryContainer = Grey25,

    // Tertiary colors
    tertiary = Info400,
    onTertiary = Grey900,
    tertiaryContainer = Info800,
    onTertiaryContainer = Info100,

    // Background & Surface - True dark for OLED
    background = Grey900,
    onBackground = Grey50,
    surface = Grey900,
    onSurface = Grey50,
    surfaceVariant = Grey850,
    onSurfaceVariant = Grey200,
    surfaceTint = ColorAccent.copy(alpha = 0.15f),
    inverseSurface = Grey200,
    inverseOnSurface = Grey900,

    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey850,
    surfaceContainerLow = Grey800,
    surfaceContainerLowest = Grey900,
    surfaceContainerHigh = Grey900,
    surfaceContainerHighest = Grey950,

    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey900,
    surfaceDim = Grey850,

    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = ColorAccent.copy(alpha = 0.2f),
    primaryFixedDim = ColorAccent.copy(alpha = 0.35f),
    onPrimaryFixed = Grey25,
    onPrimaryFixedVariant = ColorAccent.copy(alpha = 0.8f),
    secondaryFixed = Success700,
    secondaryFixedDim = Success600,
    onSecondaryFixed = Grey25,
    onSecondaryFixedVariant = Success300,
    tertiaryFixed = Info700,
    tertiaryFixedDim = Info600,
    onTertiaryFixed = Grey25,
    onTertiaryFixedVariant = Info300,

    // Error colors
    error = Error400,
    onError = Grey0,
    errorContainer = Error800,
    onErrorContainer = Error100,

    // Outline colors - Subtle for dark mode
    outline = Grey500,
    outlineVariant = Grey700,

    // Scrim
    scrim = Grey900.copy(alpha = 0.5f),
)

/**
 * Modern Black Color Scheme (AMOLED optimized) with Material 3 design principles.
 * Features:
 * - Pure black background for maximum OLED efficiency
 * - Carefully balanced contrast for readability
 * - Muted accents for comfortable viewing
 */
val black_colorScheme = ColorScheme(
    // Primary colors
    primary = ColorAccent.copy(alpha = 0.85f),
    onPrimary = Grey0,
    primaryContainer = ColorAccent.copy(alpha = 0.2f),
    onPrimaryContainer = ColorAccent.copy(alpha = 0.95f),
    inversePrimary = Grey600,

    // Secondary colors
    secondary = ColorAccent.copy(alpha = 0.8f),
    onSecondary = Grey0,
    secondaryContainer = ColorAccent.copy(alpha = 0.25f),
    onSecondaryContainer = Grey25,

    // Tertiary colors
    tertiary = Info400,
    onTertiary = Grey900,
    tertiaryContainer = Info800,
    onTertiaryContainer = Info100,

    // Background & Surface - Pure black for AMOLED
    background = Grey1000,
    onBackground = Grey50,
    surface = Grey1000,
    onSurface = Grey50,
    surfaceVariant = Grey950,
    onSurfaceVariant = Grey200,
    surfaceTint = ColorAccent.copy(alpha = 0.15f),
    inverseSurface = Grey200,
    inverseOnSurface = Grey1000,

    // Surface Container roles (Material3 1.3.0+)
    surfaceContainer = Grey950,
    surfaceContainerLow = Grey900,
    surfaceContainerLowest = Grey1000,
    surfaceContainerHigh = Grey1000,
    surfaceContainerHighest = Grey1000,

    // Surface Bright/Dim (Material3 1.4.0+)
    surfaceBright = Grey1000,
    surfaceDim = Grey950,

    // Fixed color roles (Material3 1.4.0+)
    primaryFixed = ColorAccent.copy(alpha = 0.15f),
    primaryFixedDim = ColorAccent.copy(alpha = 0.3f),
    onPrimaryFixed = Grey25,
    onPrimaryFixedVariant = ColorAccent.copy(alpha = 0.75f),
    secondaryFixed = Success700,
    secondaryFixedDim = Success600,
    onSecondaryFixed = Grey25,
    onSecondaryFixedVariant = Success300,
    tertiaryFixed = Info700,
    tertiaryFixedDim = Info600,
    onTertiaryFixed = Grey25,
    onTertiaryFixedVariant = Info300,

    // Error colors
    error = Error400,
    onError = Grey0,
    errorContainer = Error800,
    onErrorContainer = Error100,

    // Outline colors - Minimal for pure black theme
    outline = Grey500,
    outlineVariant = Grey800,

    // Scrim
    scrim = Grey1000.copy(alpha = 0.5f),
)
