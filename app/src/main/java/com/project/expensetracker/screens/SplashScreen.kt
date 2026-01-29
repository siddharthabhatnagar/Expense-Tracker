package com.project.expensetracker.screens

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.expensetracker.R
import com.project.expensetracker.navigation.Main
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController,modifier: Modifier = Modifier) {
    LaunchedEffect(Unit){
        delay(2000)
        navHostController.navigate(Main)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo",modifier=Modifier.size(80.dp))
    }
}