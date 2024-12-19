package com.ifpe.traveldiarypdmv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ifpe.traveldiarypdmv.ui.route.Home
import com.ifpe.traveldiarypdmv.ui.route.Login
import com.ifpe.traveldiarypdmv.ui.route.Register
import com.ifpe.traveldiarypdmv.ui.route.Splash
import com.ifpe.traveldiarypdmv.ui.screen.home.HomeScreen
import com.ifpe.traveldiarypdmv.ui.screen.login.LoginScreen
import com.ifpe.traveldiarypdmv.ui.screen.login.LoginViewModel
import com.ifpe.traveldiarypdmv.ui.screen.recoverpassword.RecoverPasswordScreen
import com.ifpe.traveldiarypdmv.ui.screen.register.RegisterScreen
import com.ifpe.traveldiarypdmv.ui.screen.splash.SplashScreen
import com.ifpe.traveldiarypdmv.ui.theme.TravelDiaryPDMVTheme

class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelDiaryPDMVTheme {
                val navController = rememberNavController()


                NavHost(
                    navController = navController,
                    startDestination = Login
                ) {
                    // Splash Screen
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToLogin = {
                                navController.navigate(Login)
                            }
                        )
                    }


                    // Login Screen
                    composable<Login> {
                        LoginScreen(
                            viewModel = viewModel,
                            onNavigateToHome = {
                                navController.navigate(Home)
                            },
                            onNavigateToRegister = {
                                navController.navigate(Register)
                            }
                        )
                    }

                    // Register Screen
                    composable<Register> {
                        RegisterScreen(
                            navController = navController
                        )
                    }

                    // Tela Home
                    composable<Home> {
                        HomeScreen(
                            onLogout = {
                                navController.navigate(Login) {
                                    popUpTo(Splash) {
                                        inclusive = true
                                    } // Redefine o back stack para ir de volta à Splash
                                }
                            }
                        )
                    }


                }
            }
        }
    }
}


