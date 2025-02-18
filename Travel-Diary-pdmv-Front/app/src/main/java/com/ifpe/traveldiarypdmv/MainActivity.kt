package com.ifpe.traveldiarypdmv

import CreateDiaryScreen
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
import com.ifpe.traveldiarypdmv.ui.screen.resetpassword.ResetPasswordScreen
import com.ifpe.traveldiarypdmv.ui.screen.splash.SplashScreen
import com.ifpe.traveldiarypdmv.ui.theme.TravelDiaryPDMVTheme
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ifpe.traveldiarypdmv.data.model.DiaryManual
import com.ifpe.traveldiarypdmv.data.network.ApiService
import com.ifpe.traveldiarypdmv.data.network.RetrofitClient
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.route.Camera
import com.ifpe.traveldiarypdmv.ui.route.RecoverPassword
import com.ifpe.traveldiarypdmv.ui.screen.camera.CameraScreen
import com.ifpe.traveldiarypdmv.ui.screen.favorite.FavoriteScreen

import com.ifpe.traveldiarypdmv.ui.screen.settings.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val viewModelLogin: LoginViewModel by viewModels()
    private val viewModelRegister: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestLocationPermission() // Solicita a permissão ao iniciar

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
                            BottomNavigationBar(navController = navController,  onCameraClick = {
                                navController.navigate(Camera.route)
                            }
                            )
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
                                navController = navController, // ✅ Adicionado
                                userId = userId,
                                repository = diaryRepository,
                                onLogout = {
                                    viewModelLogin.onEvent(LoginUiEvent.OnResetError)
                                    navController.navigate(Splash.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onNavigateToDetails = { diaryId ->
                                    navController.navigate("details/$diaryId")
                                },
                                onCreateDiaryClick = {
                                    navController.navigate("createDiary")
                                }
                            )
                        }

                        // Details Screen
                        composable(
                            route = "${Details.route}/{diaryId}",
                            arguments = listOf(navArgument("diaryId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val token = uiState.token ?: ""
                            val diaryId = backStackEntry.arguments?.getString("diaryId") ?: ""
                            DetailsScreen(navController = navController, diaryId = diaryId, token = token, userId = userId)
                        }

                        composable(Camera.route) {
                            val token = uiState.token ?: ""
                            CameraScreen(
                                navController = navController,
                                userId = userId,
                                token = token
                            )
                        }

                        composable(BottomNavItem.Profile.route) {
                            val token = uiState.token ?: ""
                            if (userId.isNotBlank()) {
                                ProfileScreen(userId = userId, token = token, navController = navController)
                            } else {
                                Text(text = "Carregando Perfil...", modifier = Modifier.padding(16.dp))
                            }
                        }

                        composable("settings") {
                            SettingsScreen(onBackClick = { navController.popBackStack() })
                        }

                        composable("createDiary/{id}") { backStackEntry ->
                            CreateDiaryScreen(
                                navController = navController,
                                userId = userId,
                                onSaveClick = { name, description, latitude, longitude ->
                                    try {
                                        val latitudeDouble = latitude.toDouble()
                                        val longitudeDouble = longitude.toDouble()

                                        val diaryManual = DiaryManual(
                                            name = name,
                                            description = description,
                                            latitude = latitudeDouble,
                                            longitude = longitudeDouble,
                                            id = userId
                                        )

                                        // Aqui, você deve chamar a função de rede dentro de uma coroutine
                                        CoroutineScope(Dispatchers.IO).launch {
                                            val response = DiaryRepository(RetrofitClient.apiService).createDiary(diaryManual)

                                            if (response.isSuccessful) {
                                                Log.d("CreateDiary", "Diário salvo: ${diaryManual.name}")
                                            } else {
                                                Log.e("CreateDiary", "Erro ao salvar diário: ${response.code()} ${response.message()}")
                                            }
                                        }
                                    } catch (e: NumberFormatException) {
                                        Log.e("CreateDiary", "Erro ao converter latitude/longitude: ${e.message}")
                                    }
                                }
                            )
                        }

                        // Map Screen
                        composable(BottomNavItem.Map.route) {
                            if (userId.isNotBlank()) {
                                MapScreen(
                                    userId = userId,
                                    onNavigateToDetails = { diaryId ->
                                        navController.navigate("details/$diaryId")
                                    }
                                )
                            } else {
                                Text(text = "Carregando mapa...", modifier = Modifier.padding(16.dp))
                            }
                        }

                        // Favorite Screen (placeholder)
                        composable(BottomNavItem.Favorite.route) {
                            FavoriteScreen(
                                userId = userId,
                                repository = diaryRepository,
                                onNavigateToDetails = { diaryId ->
                                    navController.navigate("details/$diaryId")
                                }
                            )
                        }

                        // Reset Password Screen
                        composable("resetpassword/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email")
                            if (email != null) {
                                ResetPasswordScreen(navController, email)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun requestLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!allPermissionsGranted) {
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val granted = permissions.entries.all { it.value }
                if (!granted) {
                    Toast.makeText(this, "Permissão de localização necessária", Toast.LENGTH_SHORT).show()
                }
            }.launch(permissions)
        }
    }
}
