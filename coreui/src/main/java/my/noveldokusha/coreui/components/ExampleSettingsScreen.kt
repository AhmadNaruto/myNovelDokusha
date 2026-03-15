package my.noveldokusha.coreui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DataUsage
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Example Settings Screen demonstrating SaltUI-inspired pattern
 * with Material 3 foundation and enhanced animations.
 * 
 * This showcases Option C: Material 3 + SaltUI patterns + Enhanced animations
 */
@Composable
fun ExampleSettingsScreen(
    onNavigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        // TitleBar
        SaltTitleBar(
            title = "Settings",
            showBackButton = true,
            onBackClick = onNavigateBack
        )
        
        SaltSpacer()
        
        // Appearance Section
        SaltSectionTitle(text = "Appearance")
        SaltRoundedColumn {
            var darkTheme by remember { mutableStateOf(false) }
            
            SaltItemSwitcher(
                headline = "Dark Theme",
                subheadline = "Use dark mode for the app interface",
                checked = darkTheme,
                onCheckedChange = { darkTheme = it },
                leadingIcon = Icons.Outlined.Palette
            )
            
            SaltItem(
                headline = "Accent Color",
                subheadline = "Customize the app's color scheme",
                onClick = { /* Open color picker */ },
                leadingIcon = Icons.Outlined.ColorLens
            )
        }
        
        SaltSpacer()
        
        // Content Section
        SaltSectionTitle(text = "Content")
        SaltRoundedColumn {
            var autoUpdate by remember { mutableStateOf(true) }
            
            SaltItemSwitcher(
                headline = "Auto Update Library",
                subheadline = "Automatically check for new chapters",
                checked = autoUpdate,
                onCheckedChange = { autoUpdate = it },
                leadingIcon = Icons.Outlined.Notifications
            )
            
            SaltItemValue(
                headline = "Download Location",
                value = "Internal Storage",
                onClick = { /* Change location */ },
                leadingIcon = Icons.Outlined.DataUsage
            )
        }
        
        SaltSpacer()
        
        // Language Section
        SaltSectionTitle(text = "Language")
        SaltRoundedColumn {
            SaltItem(
                headline = "App Language",
                subheadline = "English",
                onClick = { /* Open language selector */ },
                leadingIcon = Icons.Outlined.Language
            )
            
            SaltItem(
                headline = "Translation Source",
                subheadline = "ML Kit (Offline)",
                onClick = { /* Configure translation */ },
                leadingIcon = Icons.Outlined.Language
            )
        }
        
        SaltSpacer()
        
        // Privacy & Security Section
        SaltSectionTitle(text = "Privacy & Security")
        SaltRoundedColumn {
            SaltItem(
                headline = "Privacy Policy",
                onClick = { /* Show privacy policy */ },
                leadingIcon = Icons.Outlined.Security
            )
            
            SaltItem(
                headline = "About",
                subheadline = "Version 1.0.0",
                onClick = { /* Show about dialog */ },
                leadingIcon = Icons.Outlined.Info
            )
        }
        
        SaltSpacer()
        
        // Footer
        SaltSectionTitle(
            text = "Made with ❤️ using Material 3 + SaltUI patterns",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
