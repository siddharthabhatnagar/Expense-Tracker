package com.project.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.expensetracker.screens.MainScreen
import com.project.expensetracker.screens.MonthyScreen
import com.project.expensetracker.screens.SplashScreen

@Composable
fun NavGraph(navHostController: NavHostController,modifier: Modifier = Modifier) {
    NavHost(navHostController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(navHostController)
        }
        composable<Main> {
            MainScreen(navHostController)

        }
        composable<Monthly> {navbackStack->
            MonthyScreen(hiltViewModel(navbackStack))
        }
    }
}