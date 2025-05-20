package com.arise.network.screens
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arise.network.navigation.Routes

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = false,
            onClick = { navController.navigate(Routes.DASHBOARD) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Packages") },
            label = { Text("Packages") },
            selected = false,
            onClick = { navController.navigate(Routes.PACKAGE) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About Us") },
            label = { Text("About Us") },
            selected = false,
            onClick = { navController.navigate(Routes.ABOUT_US) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
            label = { Text("Logout") },
            selected = false,
            onClick = { navController.navigate(Routes.LOG_OUT) }
        )
    }
}
