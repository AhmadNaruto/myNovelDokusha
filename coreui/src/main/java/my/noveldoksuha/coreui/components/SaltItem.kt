package my.noveldoksuha.coreui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import my.noveldoksuha.coreui.modifiers.bounceOnPressed

/**
 * SaltUI-inspired Item component built with Material 3.
 * Clean, modern list item for settings and navigation.
 * 
 * Features:
 * - Smooth press animation with scale effect
 * - Clean visual hierarchy
 * - Flexible content slots
 * - Material 3 theming
 */
@Composable
fun SaltItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    headline: @Composable () -> Unit,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "itemScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "itemAlpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null && enabled) {
                    Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .bounceOnPressed(interactionSource)
                        .toggleable(
                            value = false,
                            onValueChange = { onClick() },
                            role = Role.Button,
                            interactionSource = interactionSource,
                            indication = null
                        )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .then(
                if (isPressed && enabled) {
                    Modifier
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Leading content (icon)
        if (leadingContent != null) {
            leadingContent()
        }
        
        // Headline
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            headline()
        }
        
        // Trailing content
        if (trailingContent != null) {
            trailingContent()
        }
    }
}

/**
 * Simple SaltItem with text headline.
 */
@Composable
fun SaltItem(
    headline: String,
    subheadline: String? = null,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    SaltItem(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        headline = {
            Column {
                Text(
                    text = headline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (subheadline != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subheadline,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        leadingContent = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else null,
        trailingContent = if (trailingIcon != null) {
            {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (onTrailingIconClick != null) {
                                Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .bounceOnPressed(remember { MutableInteractionSource() })
                                    .clickable(
                                        onClick = onTrailingIconClick
                                    )
                            } else {
                                Modifier
                            }
                        ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else null
    )
}

/**
 * SaltItem with Switcher (toggle).
 */
@Composable
fun SaltItemSwitcher(
    headline: String,
    subheadline: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    SaltItem(
        modifier = modifier,
        onClick = { onCheckedChange(!checked) },
        enabled = enabled,
        headline = {
            Column {
                Text(
                    text = headline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (subheadline != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subheadline,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        leadingContent = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else null,
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                    uncheckedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
        }
    )
}

/**
 * SaltItem with value text (key-value pair display).
 */
@Composable
fun SaltItemValue(
    headline: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    SaltItem(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        headline = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = headline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelLarge,
                    color = valueColor,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        },
        leadingContent = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else null
    )
}

/**
 * SaltUI-inspired RoundedColumn container for grouping Items.
 * Creates visually clustered blocks with proper spacing.
 */
@Composable
fun SaltRoundedColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        content()
    }
}

/**
 * Section title for SaltUI-style settings.
 */
@Composable
fun SaltSectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

/**
 * Spacer for SaltUI layouts.
 */
@Composable
fun SaltSpacer(
    height: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier.height(height)
    )
}
