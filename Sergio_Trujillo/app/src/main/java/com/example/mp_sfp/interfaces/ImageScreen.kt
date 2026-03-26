package com.example.mp_sfp.interfaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ImageScreen() {
    Column(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Image Screen")
    }
}