package my.noveldokusha.settings.sections

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoMode
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.noveldoksuha.coreui.modifiers.bounceOnPressed
import my.noveldoksuha.coreui.theme.ColorAccent
import my.noveldoksuha.coreui.theme.InternalTheme
import my.noveldokusha.core.domain.AppVersion
import my.noveldokusha.core.domain.RemoteAppVersion
import my.noveldokusha.settings.R
import my.noveldokusha.settings.SettingsScreenState
import my.noveldokusha.settings.views.NewAppUpdateDialog

/**
 * Modern Library Auto Update section with Material 3 design principles.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LibraryAutoUpdate(
    state: SettingsScreenState.LibraryAutoUpdate,
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Title
        Text(
            text = "Library updates",
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
                    state.autoUpdateEnabled.value = !state.autoUpdateEnabled.value
                },
            headlineContent = {
                Text(
                    text = stringResource(R.string.automatically_check_for_library_updates),
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
                    checked = state.autoUpdateEnabled.value,
                    onCheckedChange = {
                        state.autoUpdateEnabled.value = !state.autoUpdateEnabled.value
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
        
        // Library update interval options
        ListItem(
            headlineContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Update Frequency",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        libraryUpdateTimes.forEach { updateInterval ->
                            FilterChip(
                                selected = updateInterval.intervalHours == state.autoUpdateIntervalHours.value,
                                onClick = { state.autoUpdateIntervalHours.value = updateInterval.intervalHours },
                                label = { 
                                    Text(
                                        text = stringResource(id = updateInterval.nameRes),
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
                    Icons.Outlined.Timer,
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

internal val libraryUpdateTimes = listOf(
    UpdateIntervalTimes(intervalHours = 6, nameRes = R.string.library_update_interval_6_h),
    UpdateIntervalTimes(intervalHours = 12, nameRes = R.string.library_update_interval_12_h),
    UpdateIntervalTimes(intervalHours = 24, nameRes = R.string.library_update_interval_1_day),
    UpdateIntervalTimes(intervalHours = 24 * 2, nameRes = R.string.library_update_interval_2_days),
)

internal data class UpdateIntervalTimes(
    val intervalHours: Int,
    @StringRes val nameRes: Int
)

@Preview
@Composable
private fun PreviewView() {
    InternalTheme {
        NewAppUpdateDialog(
            updateApp = SettingsScreenState.UpdateApp(
                currentAppVersion = "1.2.3",
                appUpdateCheckerEnabled = remember { mutableStateOf(true) },
                showNewVersionDialog = remember {
                    mutableStateOf(
                        RemoteAppVersion(
                            sourceUrl = "url",
                            version = AppVersion(1, 4, 5)
                        )
                    )
                },
                checkingForNewVersion = remember { mutableStateOf(true) }
            )
        )
    }
}
