package com.ifpe.traveldiarypdmv.ui.component.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ifpe.traveldiarypdmv.ui.component.bottom_navigation.BottomNavItem
import com.ifpe.traveldiarypdmv.ui.screen.home.HomeScreen

@Composable
fun BottomNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable (BottomNavItem.Home.route) {
            HomeScreen (onLogout = {
               navController.navigate("login") { // Ajuste para a rota correta
                    popUpTo("bottom_navigation") { inclusive = true }
                }
            })
        }
        composable(BottomNavItem.Profile.route) {
            //ProfileScreen() // Tela de perfil
        }
        composable(BottomNavItem.Settings.route) {
            //SettingsScreen() // Tela de configurações
        }
    }
}