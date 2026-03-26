package com.example.mp_sfp.interfaces

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AudioScreen() {
    Column(Modifier.fillMaxSize().padding(24.dp, 48.dp, 0.dp, 0.dp)) {
        Text("Audio Screen")
    }
}