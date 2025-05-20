package com.arise.network.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arise.network.screens.Logout.LogoutScreen
import com.arise.network.screens.Packages
import com.arise.network.screens.aboutus.About
import com.arise.network.screens.dashboard.DashboardScreen
import com.arise.network.screens.profile.ProfileScreen
import com.arise.network.screens.services.AddServices
import com.arise.network.screens.services.MyServices

object Routes {
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val PROFILE = "ProfileScreen"
    const val PACKAGE = "packages"
    const val HELP = "helpcenter"
    const val UPDATE = "UpdateCredentialsScreen"
    const val ABOUT_US = "aboutus"
    const val LOG_OUT = "Logout"
    const val SERVICES = "AddServices"
    const val MY_SERVICES = "MyServices"
}

@Composable
fun NavigationGraph(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.DASHBOARD
    ) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(navController)
        }
        composable(Routes.PACKAGE) {
            Packages(navController)
        }
        composable(Routes.ABOUT_US) {
            About(navController)
        }
        composable(Routes.LOG_OUT) {
            LogoutScreen(navController)
        }
        composable(Routes.SERVICES) {
            AddServices(navController)
        }
        composable(Routes.MY_SERVICES) {
            MyServices(navController)
            }
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }
    }
}
