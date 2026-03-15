package my.noveldokusha.settings.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.noveldoksuha.coreui.modifiers.bounceOnPressed
import my.noveldoksuha.coreui.theme.ColorAccent
import my.noveldoksuha.coreui.theme.Themes
import my.noveldoksuha.coreui.theme.textPadding
import my.noveldokusha.settings.R

/**
 * Modern Settings Theme section with Material 3 design principles.
 * Features clean spacing, tonal colors, and smooth interactions.
 */
@OptIn(ExperimentalLayoutApi::class)
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
        // Section Title
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            color = ColorAccent,
        )
        
        // Follow system theme
        ListItem(
            modifier = Modifier
                .bounceOnPressed(remember { MutableInteractionSource() })
                .clickable { onFollowSystemChange(!currentFollowSystem) },
            headlineContent = {
                Text(
                    text = stringResource(id = R.string.follow_system),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingContent = {
                Switch(
                    checked = currentFollowSystem,
                    onCheckedChange = onFollowSystemChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = ColorAccent,
                        checkedTrackColor = ColorAccent.copy(alpha = 0.5f),
                        checkedBorderColor = ColorAccent.copy(alpha = 0.5f),
                        uncheckedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    )
                )
            },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        
        // Themes selection
        ListItem(
            headlineContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Select Theme",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Themes.entries.forEach { theme ->
                            FilterChip(
                                selected = theme == currentTheme,
                                onClick = { onCurrentThemeChange(theme) },
                                label = { 
                                    Text(
                                        text = stringResource(id = theme.nameId),
                                        style = MaterialTheme.typography.labelMedium
                                    ) 
                                },
                                modifier = Modifier.bounceOnPressed(remember { MutableInteractionSource() })
                            )
                        }
                    }
                }
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.ColorLens,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}