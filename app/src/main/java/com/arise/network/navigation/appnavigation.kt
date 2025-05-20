package com.arise.network.navigation

import PaymentScreen
import RegisterForm
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arise.network.screens.Logout.LogoutScreen
import com.arise.network.screens.Packages
import com.arise.network.screens.aboutus.About
import com.arise.network.screens.dashboard.DashboardScreen
import com.arise.network.screens.help.HelpCenter
import com.arise.network.screens.profile.ProfileScreen
import com.arise.network.screens.services.AddServices
import com.arise.network.screens.services.MyServices
import login

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.REGISTER) {
            RegisterForm(navController = navController)
        }

        composable(Routes.LOGIN) {
            login(navController = navController)
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(navController = navController)
        }
composable(Routes.PROFILE) {
    ProfileScreen(navController = navController)
}
        composable(Routes.UPDATE) {
            ProfileScreen(
                navController = navController
            )
        }
        composable(Routes.SERVICES) {
            AddServices(navController = navController)
        }
        composable(Routes.MY_SERVICES) {
            MyServices(
                navController = navController,)
        }


        composable(Routes.PACKAGE) {
            Packages(navController = navController)
        }

        composable(Routes.HELP) {
            HelpCenter(navController = navController)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController = navController)
        }


        // ✅ Fix: Define payment route with parameter
        composable(
            route = "payment/{packageName}",
            arguments = listOf(navArgument("packageName") { type = NavType.StringType })
        ) { backStackEntry ->
            val packageName = backStackEntry.arguments?.getString("packageName") ?: ""
            PaymentScreen(navController = navController, packageName = packageName)
        }
        composable(Routes.ABOUT_US) {
            About(navController = navController)
        }
        composable(Routes.LOG_OUT) {
            LogoutScreen(navController = navController)
        }
    }

}
