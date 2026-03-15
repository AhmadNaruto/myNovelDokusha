package my.noveldoksuha.coreui.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

/**
 * Enhanced Spring Animation Specifications for Material 3 micro-interactions.
 * These specs provide consistent, delightful animations across the app.
 */

object AnimationSpecs {
    // Quick, snappy animations for immediate feedback
    val quickSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessHigh
    )
    
    // Default spring for most UI transitions
    val defaultSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    // Relaxed spring for larger, more noticeable animations
    val relaxedSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
    
    // Very bouncy spring for playful interactions
    val bouncySpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
    
    // Subtle spring for minimal, refined animations
    val subtleSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    // Tween for predictable, time-based animations
    fun smoothTween(durationMillis: Int = 300) = tween<Float>(
        durationMillis = durationMillis,
        delayMillis = 0
    )
}

/**
 * Animation presets for common UI patterns.
 */
enum class AnimationPreset {
    Quick,
    Default,
    Relaxed,
    Bouncy,
    Subtle
}

/**
 * Get spring animation spec for a preset.
 */
fun AnimationPreset.toSpring() = when (this) {
    AnimationPreset.Quick -> AnimationSpecs.quickSpring
    AnimationPreset.Default -> AnimationSpecs.defaultSpring
    AnimationPreset.Relaxed -> AnimationSpecs.relaxedSpring
    AnimationPreset.Bouncy -> AnimationSpecs.bouncySpring
    AnimationPreset.Subtle -> AnimationSpecs.subtleSpring
}

/**
 * Animate opacity with smooth spring transitions.
 * Perfect for fade-in/fade-out effects on interaction.
 */
@Composable
fun animateOpacity(
    targetValue: Float,
    preset: AnimationPreset = AnimationPreset.Default,
    label: String = "opacity"
): Float {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = preset.toSpring(),
        label = label
    ).value
}

/**
 * Animate scale with smooth spring transitions.
 * Ideal for press effects, zoom animations, and entrance/exit transitions.
 */
@Composable
fun animateScale(
    targetValue: Float,
    preset: AnimationPreset = AnimationPreset.Default,
    label: String = "scale"
): Float {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = preset.toSpring(),
        label = label
    ).value
}

/**
 * Animate rotation with smooth spring transitions.
 * Great for expand/collapse indicators, refresh icons, and directional cues.
 */
@Composable
fun animateRotation(
    targetValue: Float,
    preset: AnimationPreset = AnimationPreset.Default,
    label: String = "rotation"
): Float {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = preset.toSpring(),
        label = label
    ).value
}

/**
 * Animate translation (offset) with smooth spring transitions.
 * Perfect for sliding animations, reveal effects, and positional changes.
 */
@Composable
fun animateTranslation(
    targetValue: Float,
    preset: AnimationPreset = AnimationPreset.Default,
    label: String = "translation"
): Float {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = preset.toSpring(),
        label = label
    ).value
}

/**
 * Staggered animation delay for list items or sequential animations.
 * Use to create cascading effects.
 */
fun staggerDelay(index: Int, delayPerItem: Int = 50): Int {
    return index * delayPerItem
}

/**
 * Calculate spring physics for elevation changes.
 * Returns appropriate elevation delta based on interaction state.
 */
object ElevationAnimations {
    data class ElevationState(
        val base: Float,
        val hovered: Float,
        val pressed: Float,
        val focused: Float
    )
    
    fun default() = ElevationState(
        base = 1f,
        hovered = 4f,
        pressed = 0.5f,
        focused = 2f
    )
    
    fun elevated() = ElevationState(
        base = 4f,
        hovered = 8f,
        pressed = 2f,
        focused = 6f
    )
    
    fun flat() = ElevationState(
        base = 0f,
        hovered = 2f,
        pressed = 0f,
        focused = 1f
    )
}
