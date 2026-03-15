package my.noveldokusha.settings.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.DataArray
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.components.SaltItem
import my.noveldokusha.coreui.components.SaltItemValue
import my.noveldokusha.coreui.components.SaltRoundedColumn
import my.noveldokusha.coreui.components.SaltSectionTitle
import my.noveldokusha.coreui.components.SaltSpacer
import my.noveldokusha.settings.R

/**
 * Enhanced Settings Data section with SaltUI-inspired pattern.
 */
@Composable
internal fun SettingsData(
    databaseSize: String,
    imagesFolderSize: String,
    onCleanDatabase: () -> Unit,
    onCleanImageFolder: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Header
        SaltSectionTitle(text = stringResource(id = R.string.data))
        
        SaltRoundedColumn {
            // Clean Database
            SaltItemValue(
                headline = stringResource(R.string.clean_database),
                value = databaseSize,
                onClick = onCleanDatabase,
                leadingIcon = Icons.Outlined.DataArray,
                valueColor = MaterialTheme.colorScheme.primary
            )
            
            // Clean Images Folder
            SaltItem(
                headline = stringResource(R.string.clean_images_folder),
                subheadline = "${stringResource(id = R.string.size)}: $imagesFolderSize",
                onClick = onCleanImageFolder,
                leadingIcon = Icons.Outlined.Image
            )
        }
        
        SaltSpacer()
    }
}