package my.noveldokusha.coreui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp


/**
 * Modern Material 3 Shapes with larger corner radii for a polished, contemporary look.
 * Following the principle of using 16dp+ for a softer, more modern appearance.
 */
val shapes = Shapes(
    small = RoundedCornerShape(12.dp),   // Increased from 8dp for softer feel
    medium = RoundedCornerShape(16.dp),  // Increased from 12dp
    large = RoundedCornerShape(24.dp),   // Increased from 16dp for modern cards
    extraLarge = RoundedCornerShape(28.dp) // For dialogs and prominent surfaces
)

val ImageBorderShape = shapes.medium

val selectableMinHeight = 56.dp // Increased from 48dp for better touch targets
