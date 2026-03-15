package my.noveldokusha.coreui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Enhanced Section component with Material 3 tonal elevation and modern depth effects.
 */
@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String? = null,
    elevation: Float = 1f,
    accentGlow: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val baseElevation = 2.dp
    val animatedElevation by animateDpAsState(
        targetValue = baseElevation * elevation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "sectionElevation"
    )
    
    val shadowOpacity by animateFloatAsState(
        targetValue = 0.08f * elevation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "sectionShadowOpacity"
    )
    
    // Capture colors outside drawWithContent
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
    val tertiaryColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)

    Card(
        modifier = modifier.then(
            if (accentGlow) {
                Modifier.drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.linearGradient(
                            colorStops = arrayOf(
                                0.0f to primaryColor,
                                0.5f to secondaryColor,
                                1.0f to tertiaryColor
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, size.height)
                        ),
                        alpha = shadowOpacity
                    )
                }
            } else {
                Modifier
            }
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = animatedElevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        }
        content()
    }
}

/**
 * Elevated Section variant with stronger visual presence.
 */
@Composable
fun ElevatedSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Section(
        modifier = modifier,
        title = title,
        elevation = 1.5f,
        accentGlow = true,
        content = content
    )
}
