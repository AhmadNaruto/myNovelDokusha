package my.noveldokusha.coreui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Enhanced Icon component with background container and modern effects.
 */
@Composable
fun EnhancedIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    elevation: Dp = 2.dp,
    animateOnHover: Boolean = false,
    isHovered: Boolean = false,
    contentDescription: String? = null
) {
    val scale by animateFloatAsState(
        targetValue = if (isHovered && animateOnHover) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "iconScale"
    )
    
    val containerAlpha by animateFloatAsState(
        targetValue = if (isHovered && animateOnHover) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "iconContainerAlpha"
    )

    Box(
        modifier = modifier
            .size(containerSize)
            .scale(scale)
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
                ambientColor = containerColor.copy(alpha = 0.3f * containerAlpha),
                spotColor = containerColor.copy(alpha = 0.5f * containerAlpha)
            )
            .clip(shape)
            .background(
                color = containerColor.copy(alpha = containerAlpha)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

/**
 * Circular Icon variant.
 */
@Composable
fun CircularIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerSize: Dp = 56.dp,
    iconSize: Dp = 28.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    elevation: Dp = 4.dp,
    isHovered: Boolean = false,
    contentDescription: String? = null
) {
    EnhancedIcon(
        icon = icon,
        modifier = modifier,
        containerSize = containerSize,
        iconSize = iconSize,
        containerColor = containerColor,
        iconColor = iconColor,
        shape = CircleShape,
        elevation = elevation,
        animateOnHover = true,
        isHovered = isHovered,
        contentDescription = contentDescription
    )
}

/**
 * Icon with gradient background.
 */
@Composable
fun GradientIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    ),
    iconColor: Color = Color.White,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    elevation: Dp = 3.dp,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
                ambientColor = gradientColors.first().copy(alpha = 0.3f),
                spotColor = gradientColors.last().copy(alpha = 0.5f)
            )
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

/**
 * Outlined Icon variant.
 */
@Composable
fun OutlinedIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    containerColor: Color = Color.Transparent,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    borderWidth: Dp = 1.5.dp,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .clip(shape)
            .background(containerColor)
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(
                        width = borderWidth,
                        color = borderColor,
                        shape = shape
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}
