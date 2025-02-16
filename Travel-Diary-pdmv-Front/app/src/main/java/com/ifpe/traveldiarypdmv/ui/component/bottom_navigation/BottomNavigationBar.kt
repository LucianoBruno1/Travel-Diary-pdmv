package com.ifpe.traveldiarypdmv.ui.component.bottom_navigation

<<<<<<< HEAD
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
=======
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
>>>>>>> a6f0088 (feat: add camera, criar diario por foto e adicionar foto em diario existente)
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
<<<<<<< HEAD
import androidx.compose.material3.*
=======
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
>>>>>>> a6f0088 (feat: add camera, criar diario por foto e adicionar foto em diario existente)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Map : BottomNavItem("map", Icons.Default.LocationOn, "Map")
<<<<<<< HEAD
    object Create : BottomNavItem("create", Icons.Default.Add, "Create") // Novo ícone central
=======
    object Camera : BottomNavItem("camera", Icons.Default.AddCircle, "Camera")
>>>>>>> a6f0088 (feat: add camera, criar diario por foto e adicionar foto em diario existente)
    object Favorite : BottomNavItem("favorite", Icons.Default.Favorite, "Favorite")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@Composable
<<<<<<< HEAD
fun BottomNavigationBar(navController: NavController, onCreateClick: () -> Unit) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Map, BottomNavItem.Favorite, BottomNavItem.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar(
            modifier = Modifier.height(95.dp),
            containerColor = GreenBase,
            tonalElevation = 1.dp
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) { // Posição central
                    Spacer(modifier = Modifier.weight(1f))
                }

                val selected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selected) Color.White else Color.Black
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            color = if (selected) Color.White else Color.Black,
                            style = if (selected) Typography.bodyLarge else Typography.bodySmall
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
=======
fun BottomNavigationBar(navController: NavController, onCameraClick: () -> Unit) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Map, BottomNavItem.Camera, BottomNavItem.Favorite, BottomNavItem.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .height(95.dp),
        containerColor = GreenBase,
        tonalElevation = 1.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selected) Color.White else Color.Black
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = if (selected) Color.White else Color.Black,
                        style = if (selected) Typography.bodyLarge else Typography.bodySmall
                    )
                },
                selected = selected,
                onClick = {
                    if (item.route == BottomNavItem.Camera.route) { // Corrigindo a verificação da rota
                        onCameraClick()
                    } else if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
>>>>>>> a6f0088 (feat: add camera, criar diario por foto e adicionar foto em diario existente)
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = onCreateClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -30.dp), // Elevar o botão acima da NavigationBar
            containerColor = GreenLight
        ) {
            Icon(Icons.Default.Add, contentDescription = "Criar Diário", tint = Color.White)
        }
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    val navController = rememberNavController()

    @Suppress("UNREACHABLE_CODE")
    BottomNavigationBar(
        navController = navController,
        onCameraClick = TODO(),
    )
}

