package my.noveldokusha.coreui.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.TileMode
import my.noveldokusha.coreui.theme.ColorAccent

/**
 * Gradient utilities for creating modern, eye-catching UI elements.
 * Use sparingly for maximum impact on important elements.
 */
object GradientUtils {
    
    /**
     * Primary accent gradient - subtle blue gradient for buttons, cards, and highlights.
     */
    fun primaryGradient(
        alpha: Float = 1f,
        direction: GradientDirection = GradientDirection.Horizontal
    ): Brush {
        val colors = when (direction) {
            GradientDirection.Horizontal -> listOf(
                ColorAccent.copy(alpha = alpha),
                ColorAccent.copy(alpha = alpha * 0.85f)
            )
            GradientDirection.Vertical -> listOf(
                ColorAccent.copy(alpha = alpha),
                ColorAccent.copy(alpha = alpha * 0.7f)
            )
            GradientDirection.Diagonal -> listOf(
                ColorAccent.copy(alpha = alpha),
                ColorAccent.copy(alpha = alpha * 0.8f)
            )
        }
        return Brush.linearGradient(
            colors = colors,
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Warm accent gradient - orange/amber for warnings, highlights, or CTAs.
     */
    fun warmGradient(
        alpha: Float = 1f,
        direction: GradientDirection = GradientDirection.Horizontal
    ): Brush {
        return Brush.linearGradient(
            colors = when (direction) {
                GradientDirection.Horizontal -> listOf(
                    Color(0xFFFF8A65).copy(alpha = alpha),
                    Color(0xFFFFB74D).copy(alpha = alpha)
                )
                GradientDirection.Vertical -> listOf(
                    Color(0xFFFF8A65).copy(alpha = alpha),
                    Color(0xFFFFD54F).copy(alpha = alpha)
                )
                GradientDirection.Diagonal -> listOf(
                    Color(0xFFFF8A65).copy(alpha = alpha),
                    Color(0xFFFFCA28).copy(alpha = alpha)
                )
            },
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Cool accent gradient - teal/green for success states or secondary actions.
     */
    fun coolGradient(
        alpha: Float = 1f,
        direction: GradientDirection = GradientDirection.Horizontal
    ): Brush {
        return Brush.linearGradient(
            colors = when (direction) {
                GradientDirection.Horizontal -> listOf(
                    Color(0xFF4DB6AC).copy(alpha = alpha),
                    Color(0xFF81C784).copy(alpha = alpha)
                )
                GradientDirection.Vertical -> listOf(
                    Color(0xFF26A69A).copy(alpha = alpha),
                    Color(0xFF66BB6A).copy(alpha = alpha)
                )
                GradientDirection.Diagonal -> listOf(
                    Color(0xFF4DB6AC).copy(alpha = alpha),
                    Color(0xFF81C784).copy(alpha = alpha)
                )
            },
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Premium gold gradient - for special features or premium content.
     */
    fun goldGradient(
        alpha: Float = 1f,
        direction: GradientDirection = GradientDirection.Horizontal
    ): Brush {
        return Brush.linearGradient(
            colors = when (direction) {
                GradientDirection.Horizontal -> listOf(
                    Color(0xFFFFD700).copy(alpha = alpha),
                    Color(0xFFFFECB3).copy(alpha = alpha * 0.8f),
                    Color(0xFFFFD700).copy(alpha = alpha)
                )
                GradientDirection.Vertical -> listOf(
                    Color(0xFFFFECB3).copy(alpha = alpha),
                    Color(0xFFFFD700).copy(alpha = alpha),
                    Color(0xFFFFC107).copy(alpha = alpha)
                )
                GradientDirection.Diagonal -> listOf(
                    Color(0xFFFFD700).copy(alpha = alpha),
                    Color(0xFFFFECB3).copy(alpha = alpha * 0.7f),
                    Color(0xFFFFD700).copy(alpha = alpha)
                )
            },
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Subtle surface gradient - for card backgrounds with depth.
     */
    fun surfaceGradient(
        lightColor: Color,
        darkColor: Color,
        direction: GradientDirection = GradientDirection.Vertical
    ): Brush {
        return Brush.linearGradient(
            colors = when (direction) {
                GradientDirection.Horizontal -> listOf(lightColor, darkColor)
                GradientDirection.Vertical -> listOf(lightColor, darkColor)
                GradientDirection.Diagonal -> listOf(lightColor, darkColor)
            },
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Radial gradient for spotlight effects or focus points.
     */
    fun radialGradient(
        centerColor: Color,
        edgeColor: Color,
        radius: Float = 1f
    ): Brush {
        return Brush.radialGradient(
            colors = listOf(
                centerColor.copy(alpha = centerColor.alpha * radius),
                edgeColor.copy(alpha = edgeColor.alpha * radius * 0.3f)
            ),
            tileMode = TileMode.Clamp
        )
    }
    
    /**
     * Animated shimmer gradient for loading states.
     */
    fun shimmerGradient(
        baseColor: Color,
        highlightColor: Color,
        progress: Float
    ): Brush {
        return Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to baseColor,
                progress.coerceIn(0f, 1f) to highlightColor,
                1.0f to baseColor
            )
        )
    }
}

/**
 * Gradient direction for linear gradients.
 */
enum class GradientDirection {
    Horizontal,
    Vertical,
    Diagonal
}

/**
 * Extension function to create gradient background modifier.
 */
// Note: Actual Modifier extension would require compose.foundation dependency
// This is a utility helper for brush creation
