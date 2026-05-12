package com.example.mab_p2.interfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController){




    //Interfaz
    Column(
        Modifier.fillMaxSize().padding(25.dp),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("NAVEGACIÓN")
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.AUD)}){Text("Audio")}
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.PHO)}){Text("Fotos")}
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.VID)}){Text("Videos")}

    }
}