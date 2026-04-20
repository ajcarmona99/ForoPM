package com.example.mp_sfp.interfaces

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.Composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController


@Composable
fun HomeScreen(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Proyecto PM")

        Button(onClick = {navController.navigate(Routes.AUDIO)}) {Text("Audio") }
        Button(onClick = {navController.navigate(Routes.CAMERA)}) {Text("Camara") }
        Button(onClick = {navController.navigate(Routes.IMAGE)}) {Text("Imagen") }
        Button(onClick = {navController.navigate(Routes.VIDEO)}) {Text("Video") }
    }
}