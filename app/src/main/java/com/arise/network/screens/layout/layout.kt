@file:OptIn(ExperimentalMaterial3Api::class)

package com.arise.enetwork.ui.theme.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arise.network.navigation.Routes

// --- DATA CLASS ---
data class NavItem(
    val href: String,
    val label: String,
    val icon: String // Icon name as string
)

// --- MOCK NAV ITEMS ---
val navItems = listOf(
    NavItem("/dashboard", "Dashboard", "LayoutDashboard"),
    NavItem("/isps", "ISPs", "Router"),
    NavItem("/packages", "Packages", "Package"),
    NavItem("/profile", "Profile", "UserCircle")
)

// --- MAIN LAYOUT COMPOSABLE ---
@Composable
fun AppLayout(
    content: @Composable () -> Unit
) {
    SidebarProvider(defaultOpen = true) {
        Row(Modifier.fillMaxSize()) {
            Sidebar(navItems = navItems)
            SidebarInset {
                Column(Modifier.fillMaxSize()) {
                    TopAppBarContent(rememberNavController())
                    MainContent(content)
                }
            }
        }
    }
}



@Composable
fun SidebarProvider(
    defaultOpen: Boolean = true,
    content: @Composable () -> Unit
) {
    content() // In Compose, no special provider needed unless managing sidebar state globally
}


@Composable
fun SidebarInset(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}


@Composable
fun SidebarTrigger(
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { /* TODO: open/close sidebar */ },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu"
        )
    }
}


@Composable
fun Sidebar(
    navItems: List<NavItem>
) {
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        navItems.forEach { item ->
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
@Composable
fun Skeleton(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {}
}

@Composable
fun TopAppBarContent(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Arise Network") },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.PROFILE) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            }
            IconButton(onClick = { navController.navigate(Routes.HELP) }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Customer Care"
                )
            }
            IconButton(onClick = { navController.navigate(Routes.ABOUT_US) }) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Customer Care"
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

/*sealed class BottomNavItem(val route: String, val label: String) {
    object Dashboard : BottomNavItem("dashboard", "Dashboard")
    object Packages : BottomNavItem("packages", "Packages")
    object AboutUs : BottomNavItem("about", "About Us")
    object Logout : BottomNavItem("logout", "Logout")
}*/


@Composable
fun MainContent(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface,
            content = content
        )
    }
}

// --- SKELETON COMPOSABLES ---

@Composable
fun PageSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Skeleton(
            modifier = Modifier
                .height(40.dp)
                .width(120.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(3) {
                Skeleton(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                )
            }
        }

        Skeleton(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun AppLayoutPreview() {
    AppLayout {(rememberNavController())
}}