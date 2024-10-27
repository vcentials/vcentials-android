package com.example.vcentials_android

import HomeScreen
import LoginScreen
import MenuScreen
import SignupScreen
import StartupScreen
import AddTempScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "startup") {
        composable("startup") { StartupScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("add_temp") { AddTempScreen(navController) }
        composable("menu") { MenuScreen(navController) }
    }
}


