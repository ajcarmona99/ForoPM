package com.giovanna.medialab.ui

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("MediaLab - Proyecto Multimedia")

        Button(onClick = {navController.navigate(Routes.AUDIO)}) {Text("Audio")}
        Button(onClick = {navController.navigate(Routes.CAMERA)}) { Text("Camera")}
        Button(onClick = {navController.navigate(Routes.IMAGE)}) { Text("Imagen")}
        Button(onClick = {navController.navigate(Routes.VIDEO)}) { Text("Video")}
    }
}