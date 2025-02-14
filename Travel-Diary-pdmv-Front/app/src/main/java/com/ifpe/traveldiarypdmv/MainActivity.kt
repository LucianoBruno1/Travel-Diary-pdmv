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
import com.ifpe.traveldiarypdmv.data.network.ApiService
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.component.bottom_navigation.BottomNavItem
import com.ifpe.traveldiarypdmv.ui.component.bottom_navigation.BottomNavigationBar
import com.ifpe.traveldiarypdmv.ui.route.*
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
import com.ifpe.traveldiarypdmv.ui.screen.resetpassword.ResetPasswordScreen
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
                val userId = uiState.userId ?: ""


                val apiService = ApiService.create()
                val diaryRepository = DiaryRepository(apiService)

                Scaffold(
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        if (isLoggedIn && currentRoute in listOf(
                                BottomNavItem.Home.route,
                                BottomNavItem.Map.route,
                                BottomNavItem.Favorite.route,
                                BottomNavItem.Profile.route
                            )
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) BottomNavItem.Home.route else Splash.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        // Splash Screen
                        composable(Splash.route) {
                            SplashScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Login.route) {
                                        popUpTo(Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Login Screen
                        composable(Login.route) {
                            LoginScreen(
                                viewModel = viewModelLogin,
                                onNavigateToHome = {
                                    navController.navigate(BottomNavItem.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = { navController.navigate(Register.route) },
                                onNavigateToRecoverPassword = { navController.navigate(RecoverPassword.route) }
                            )
                        }

                        // Register Screen
                        composable(Register.route) {
                            RegisterScreen(viewModel = viewModelRegister, navController = navController)
                        }

                        // Recover Password Screen
                        composable(RecoverPassword.route) {
                            RecoverPasswordScreen(navController)
                        }



                        composable(Home.route) {
                            HomeScreen(
                                userId = userId,
                                repository = diaryRepository,
                                onLogout = {
                                    viewModelLogin.onEvent(LoginUiEvent.OnResetError)
                                    navController.navigate(Splash.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onNavigateToDetails = {
                                    navController.navigate(Details.route)
                                }
                            )
                        }

                        // Details Screen
                        composable(Details.route) {
                            DetailsScreen(navController = navController)
                        }

                        // Profile Screen
                        composable(BottomNavItem.Profile.route) {
                            ProfileScreen()
                        }

                        // Map Screen
                        composable(BottomNavItem.Map.route) {
                            if (userId.isNotBlank()) {
                                MapScreen(userId = userId)
                            } else {
                                Text(text = "Carregando mapa...", modifier = Modifier.padding(16.dp))
                            }
                        }

                        // Favorite Screen (placeholder)
                        composable(BottomNavItem.Favorite.route) {
                            Text(text = "Tela de Favoritos em construção...", modifier = Modifier.padding(16.dp))
                        }
                        // Reset Password Screen
                        composable("resetpassword/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email")
                            if (email != null) {
                                ResetPasswordScreen(email)
                            }
                        }

                    }
                    }
                }
            }
        }
    }

