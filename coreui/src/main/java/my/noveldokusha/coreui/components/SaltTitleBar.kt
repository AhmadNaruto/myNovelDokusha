package my.noveldokusha.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.modifiers.bounceOnPressed

/**
 * SaltUI-inspired TitleBar component.
 * Clean, modern top app bar with optional back navigation and actions.
 * 
 * Features:
 * - Smooth press animations
 * - Clean visual hierarchy
 * - Optional back button with bounce effect
 * - Action items support
 * - Material 3 theming
 */
@Composable
fun SaltTitleBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
    containerColor: Color = Color.Transparent,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Leading: Back button or spacer
            if (showBackButton) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .bounceOnPressed(remember { MutableInteractionSource() })
                        .clickable(
                            enabled = onBackClick != null,
                            onClick = onBackClick ?: {}
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp),
                        tint = titleColor
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(40.dp))
            }
            
            // Center: Title and optional subtitle
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = titleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = subtitleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            // Trailing: Actions or spacer
            if (actions == {}) {
                Spacer(modifier = Modifier.width(40.dp))
            } else {
                Box(
                    modifier = Modifier.padding(end = 4.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    actions()
                }
            }
        }
    }
}

/**
 * Simple SaltTitleBar with just title and back button.
 */
@Composable
fun SaltTitleBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit,
    containerColor: Color = Color.Transparent
) {
    SaltTitleBar(
        title = title,
        modifier = modifier,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        containerColor = containerColor
    )
}

/**
 * SaltTitleBar with action menu.
 */
@Composable
fun SaltTitleBarWithMenu(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onMenuClick: () -> Unit,
    containerColor: Color = Color.Transparent
) {
    SaltTitleBar(
        title = title,
        modifier = modifier,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        actions = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .bounceOnPressed(remember { MutableInteractionSource() })
                    .clickable(onClick = onMenuClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        containerColor = containerColor
    )
}
