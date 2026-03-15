package my.noveldokusha.settings.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.SettingsBackupRestore
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.modifiers.bounceOnPressed
import my.noveldokusha.coreui.theme.ColorAccent
import my.noveldokusha.settings.R

/**
 * Modern Settings Backup section with Material 3 design principles.
 * Clean layout with proper spacing and subtle interactions.
 */
@Composable
internal fun SettingsBackup(
    onBackupData: () -> Unit = {},
    onRestoreData: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Title
        Text(
            text = stringResource(id = R.string.backup),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            color = ColorAccent
        )
        
        ListItem(
            headlineContent = {
                Column {
                    Text(
                        text = stringResource(R.string.backup_data),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = R.string.opens_the_file_explorer_to_select_the_backup_saving_location),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.Save,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .bounceOnPressed(remember { MutableInteractionSource() })
                .clickable { onBackupData() },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.onSurface,
                supportingColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        
        ListItem(
            headlineContent = {
                Column {
                    Text(
                        text = stringResource(R.string.restore_data),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = R.string.opens_the_file_explorer_to_select_the_backup_file),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.SettingsBackupRestore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .bounceOnPressed(remember { MutableInteractionSource() })
                .clickable { onRestoreData() },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.onSurface,
                supportingColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}