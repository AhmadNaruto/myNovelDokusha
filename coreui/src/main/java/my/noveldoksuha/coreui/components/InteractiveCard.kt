package my.noveldoksuha.coreui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import my.noveldoksuha.coreui.theme.ColorAccent
import my.noveldoksuha.coreui.theme.InternalTheme
import my.noveldoksuha.coreui.theme.Themes

/**
 * Enhanced Interactive Card with Material 3 design and modern micro-interactions.
 * Features:
 * - Hover effect with elevation change
 * - Press animation with scale and opacity
 * - Dynamic shadow with color-based tint
 * - Smooth spring-based transitions
 * - Optional icon with background container
 * - Customizable gradient border
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    baseElevation: Dp = 2.dp,
    hoverElevationDelta: Dp = 4.dp,
    pressElevationDelta: Dp = (-1).dp,
    animateBorder: Boolean = false,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.() -> Unit
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation based on interaction state
    val targetElevation = when {
        !enabled -> baseElevation
        isPressed -> baseElevation + pressElevationDelta
        isHovered -> baseElevation + hoverElevationDelta
        else -> baseElevation
    }
    
    val animatedElevation by animateDpAsState(
        targetValue = targetElevation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardElevation"
    )
    
    // Animate scale for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )
    
    // Animate opacity for subtle feedback
    val alpha by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardAlpha"
    )
    
    // Animate border color if enabled
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isHovered && enabled) borderColor.copy(alpha = 0.5f) else borderColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardBorderColor"
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
                this.alpha = alpha
            }
            .shadow(
                elevation = animatedElevation,
                shape = shape,
                clip = false,
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            )
            .clip(shape)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null, // Custom indication via animations
                enabled = enabled,
                role = Role.Button,
                onClick = onClick ?: {},
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp, // We use custom shadow
            hoveredElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp
        )
    ) {
        Box(
            modifier = if (animateBorder && borderWidth > 0.dp) {
                Modifier
                    .fillMaxWidth()
                    .padding(borderWidth)
            } else {
                Modifier.fillMaxWidth()
            }
        ) {
            content(this)
        }
    }
}

/**
 * Card with icon header for feature cards or navigation items.
 */
@Composable
fun IconHeaderCard(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    iconContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 40.dp,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    InteractiveCard(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        baseElevation = 1.dp,
        hoverElevationDelta = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Icon with background container
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(
                        elevation = 2.dp,
                        shape = MaterialTheme.shapes.medium,
                        ambientColor = iconContainerColor.copy(alpha = 0.3f),
                        spotColor = iconContainerColor.copy(alpha = 0.5f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        tint = iconColor
                    )
                }
            }
            
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Subtitle
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun InteractiveCardPreview() {
    Column {
        InternalTheme(Themes.LIGHT) {
            InteractiveCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {},
                baseElevation = 2.dp,
                hoverElevationDelta = 4.dp,
                borderColor = ColorAccent.copy(alpha = 0.3f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Interactive Card - Hover & Press me!")
                }
            }
        }
    }
}

@Preview
@Composable
private fun IconHeaderCardPreview() {
    Column {
        InternalTheme(Themes.LIGHT) {
            IconHeaderCard(
                icon = androidx.compose.material.icons.Icons.Filled.Favorite,
                title = "Feature Card",
                subtitle = "This is a subtitle with additional information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {}
            )
        }
    }
}
