package com.example.mab_p2.interfaces


import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object Routes{
    const val HOME = "home"
    const val AUD = "audio"
    const val PHO = "photo"
    const val VID = "video"
    const val CAM = "camara"

}

@Composable

fun AppNav(navController: NavHostController){
    NavHost(navController=navController, startDestination = Routes.HOME){
        composable(Routes.HOME){HomeScreen(navController)}
        composable(Routes.AUD){AudioView(navController)}
        composable(Routes.PHO){PhotoView(navController)}
        composable(Routes.VID){ VideoView(navController)}
        composable(Routes.CAM){ CamaraScreen(navController)}

    }
}