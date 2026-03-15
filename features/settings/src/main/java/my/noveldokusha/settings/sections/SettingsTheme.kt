package my.noveldokusha.settings.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.noveldoksuha.coreui.components.EnhancedIcon
import my.noveldoksuha.coreui.components.SaltItem
import my.noveldoksuha.coreui.components.SaltItemSwitcher
import my.noveldoksuha.coreui.components.SaltRoundedColumn
import my.noveldoksuha.coreui.components.SaltSectionTitle
import my.noveldoksuha.coreui.components.SaltSpacer
import my.noveldoksuha.coreui.modifiers.bounceOnPressed
import my.noveldoksuha.coreui.theme.Themes
import my.noveldokusha.settings.R

/**
 * Enhanced Settings Theme section with SaltUI-inspired pattern.
 */
@Composable
internal fun SettingsTheme(
    currentTheme: Themes,
    currentFollowSystem: Boolean,
    onFollowSystemChange: (Boolean) -> Unit,
    onCurrentThemeChange: (Themes) -> Unit,
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Header
        SaltSectionTitle(text = stringResource(id = R.string.theme))
        
        SaltRoundedColumn {
            // Follow system theme
            SaltItemSwitcher(
                headline = stringResource(id = R.string.follow_system),
                subheadline = "Automatically switch between light and dark themes",
                checked = currentFollowSystem,
                onCheckedChange = onFollowSystemChange,
                leadingIcon = Icons.Outlined.AutoAwesome
            )
            
            // Theme selection with custom content
            SaltItem(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = null,
                enabled = false,
                headline = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Choose your preferred appearance",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        androidx.compose.foundation.layout.FlowRow(
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                        ) {
                            Themes.entries.forEach { theme ->
                                val isSelected = theme == currentTheme
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onCurrentThemeChange(theme) },
                                    label = {
                                        Text(
                                            text = stringResource(id = theme.nameId),
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Outlined.ColorLens,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    } else null,
                                    modifier = Modifier
                                        .bounceOnPressed(remember { androidx.compose.foundation.interaction.MutableInteractionSource() })
                                )
                            }
                        }
                    }
                },
                leadingContent = {
                    EnhancedIcon(
                        icon = Icons.Outlined.ColorLens,
                        containerSize = 40.dp,
                        iconSize = 20.dp,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        elevation = 2.dp
                    )
                }
            )
        }
        
        SaltSpacer()
    }
}