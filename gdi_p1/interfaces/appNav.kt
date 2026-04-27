package com.example.gdi_p1.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object Routes{
    const val HOME = "home"
    const val REC = "rec"

}

@Composable

fun appNav(navController: NavHostController){
    NavHost(navController=navController, startDestination = Routes.HOME){
        composable(Routes.HOME){homeScreen(navController)}
        composable(Routes.REC){audioRecorder(navController)}
    }
}