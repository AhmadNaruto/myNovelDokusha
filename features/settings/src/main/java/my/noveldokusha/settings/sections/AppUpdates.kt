package my.noveldokusha.settings.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoMode
import androidx.compose.material.icons.outlined.DoubleArrow
import androidx.compose.material3.CircularProgressIndicator
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
import my.noveldokusha.coreui.modifiers.bounceOnPressed
import my.noveldokusha.coreui.theme.ColorAccent
import my.noveldokusha.coreui.theme.debouncedClickable
import my.noveldokusha.settings.R
import my.noveldokusha.settings.SettingsScreenState
import my.noveldokusha.settings.views.NewAppUpdateDialog

/**
 * Modern App Updates section with Material 3 design principles.
 */
@Composable
internal fun AppUpdates(
    state: SettingsScreenState.UpdateApp,
    onCheckForUpdatesManual: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Title
        Text(
            text = stringResource(R.string.app_updates) + " | " + state.currentAppVersion,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            color = ColorAccent
        )
        
        ListItem(
            modifier = Modifier
                .bounceOnPressed(remember { MutableInteractionSource() })
                .clickable {
                    state.appUpdateCheckerEnabled.value = !state.appUpdateCheckerEnabled.value
                },
            headlineContent = {
                Text(
                    text = stringResource(R.string.automatically_check_for_app_updates),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.AutoMode,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingContent = {
                Switch(
                    checked = state.appUpdateCheckerEnabled.value,
                    onCheckedChange = {
                        state.appUpdateCheckerEnabled.value = !state.appUpdateCheckerEnabled.value
                    },
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
        
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(R.string.check_for_app_updates_now),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingContent = {
                Icon(
                    Icons.Outlined.DoubleArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingContent = {
                AnimatedVisibility(visible = state.checkingForNewVersion.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                }
            },
            modifier = Modifier
                .bounceOnPressed(remember { MutableInteractionSource() })
                .debouncedClickable { onCheckForUpdatesManual() },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
    
    NewAppUpdateDialog(
        updateApp = state
    )
}