package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.UserViewModel
import com.example.myapplication.ui.theme.screen.HomeScreen
import com.example.myapplication.ui.theme.screen.LoginScreen
import com.example.myapplication.ui.theme.screen.TransactionHistoryScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Transactions : Screen("transactions")
}

@Composable
fun AppRouting(viewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(Screen.Transactions.route) {
            TransactionHistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}