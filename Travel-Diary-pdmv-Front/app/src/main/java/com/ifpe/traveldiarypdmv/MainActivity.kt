package com.ifpe.traveldiarypdmv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ifpe.traveldiarypdmv.ui.component.bottom_navigation.BottomNavItem
import com.ifpe.traveldiarypdmv.ui.component.bottom_navigation.BottomNavigationBar
import com.ifpe.traveldiarypdmv.ui.route.Details
import com.ifpe.traveldiarypdmv.ui.route.Home
import com.ifpe.traveldiarypdmv.ui.route.Login
import com.ifpe.traveldiarypdmv.ui.route.RecoverPassword
import com.ifpe.traveldiarypdmv.ui.route.Register
import com.ifpe.traveldiarypdmv.ui.route.Splash
import com.ifpe.traveldiarypdmv.ui.screen.details.DetailsScreen
import com.ifpe.traveldiarypdmv.ui.screen.home.HomeScreen
import com.ifpe.traveldiarypdmv.ui.screen.login.LoginScreen
import com.ifpe.traveldiarypdmv.ui.screen.login.LoginUiEvent
import com.ifpe.traveldiarypdmv.ui.screen.login.LoginViewModel
import com.ifpe.traveldiarypdmv.ui.screen.map.MapScreen
import com.ifpe.traveldiarypdmv.ui.screen.profile.ProfileScreen
import com.ifpe.traveldiarypdmv.ui.screen.recoverpassword.RecoverPasswordScreen
import com.ifpe.traveldiarypdmv.ui.screen.register.RegisterScreen
import com.ifpe.traveldiarypdmv.ui.screen.register.RegisterViewModel
import com.ifpe.traveldiarypdmv.ui.screen.splash.SplashScreen
import com.ifpe.traveldiarypdmv.ui.theme.TravelDiaryPDMVTheme

class MainActivity : ComponentActivity() {
    private val viewModelLogin: LoginViewModel by viewModels()
    private val viewModelRegister: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelDiaryPDMVTheme {
                val navController = rememberNavController()

                // Observar o estado de autenticação
                val uiState by viewModelLogin.uiState.collectAsStateWithLifecycle()
                val isLoggedIn = uiState.isLoggedIn


                Scaffold(
                    bottomBar = {
                        val currentRoute = navController
                            .currentBackStackEntryAsState()
                            .value?.destination?.route
                        if (isLoggedIn && currentRoute in listOf(
                                BottomNavItem.Home.route,
                                BottomNavItem.Map.route,
                                BottomNavItem.Favorite.route,
                                BottomNavItem.Profile.route,

                            )
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { padding -> // padding
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) BottomNavItem.Home.route else Splash.route,
                        modifier = Modifier.padding(padding) // Aplica o padding aqui
                    ) {
                        // Rotas de autenticação
                        composable(Splash.route) {
                            SplashScreen(onNavigateToLogin = {
                                navController.navigate(Login.route) {
                                    popUpTo(Splash.route) { inclusive = true }
                                }
                            })
                        }

                        // Login Screen
                        composable(Login.route) {
                            LoginScreen(
                                viewModel = viewModelLogin,
                                onNavigateToHome = {
                                    navController.navigate(BottomNavItem.Home.route)
                                },
                                onNavigateToRegister = { navController.navigate(Register.route) },
                                onNavigateToRecoverPassword = {navController.navigate(RecoverPassword.route)}
                            )
                        }

                        // Register Screen
                        composable(Register.route)  {
                            RegisterScreen(
                                viewModel = viewModelRegister,
                                navController = navController
                            )
                        }
                        composable(RecoverPassword.route)  {
                            RecoverPasswordScreen(
                            )
                        }
                        composable(Home.route) {
                            HomeScreen(onLogout = {
                                viewModelLogin.onEvent(LoginUiEvent.OnResetError)
                                navController.navigate(Splash.route) {
                                    popUpTo(0) // Limpa a pilha
                                }
                            },
                                onNavigateToDetails = {
                                    navController.navigate(Details.route) // Navegar para a rota de detalhes
                                })
                        }

                        composable(Details.route) {
                            DetailsScreen(navController = navController)
                        }

                        composable(BottomNavItem.Profile.route) { ProfileScreen() }
                        composable(BottomNavItem.Map.route) {
                            val userId = uiState.userId ?: ""
                            if (userId.isNotBlank()) {
                                MapScreen(userId = userId)
                            } else {
                                Text(text = "Carregando mapa...", modifier = Modifier.padding(16.dp))
                            }
                        }
                        composable(BottomNavItem.Favorite.route) { /* SettingsScreen() */ }

                    }
                }
            }
        }
    }
}