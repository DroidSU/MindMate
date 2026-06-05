package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun MindMateBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.BottomNavItem(
            "home",
            Icons.Rounded.Home,
            "Home"
        ),
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.BottomNavItem(
            "journal",
            Icons.AutoMirrored.Rounded.LibraryBooks,
            "Journal"
        ),
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.BottomNavItem(
            "insights",
            Icons.Rounded.BarChart,
            "Insights"
        ),
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.BottomNavItem(
            "coach",
            Icons.Rounded.ChatBubbleOutline,
            "Coach"
        ),
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.BottomNavItem(
            "profile",
            Icons.Rounded.PersonOutline,
            "Profile"
        )
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
