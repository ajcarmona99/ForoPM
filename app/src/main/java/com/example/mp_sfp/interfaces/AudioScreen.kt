package com.example.mp_sfp.interfaces

import com.example.mp_sfp.media.SimpleAudioPlayer
import com.example.mp_sfp.storage.AppFiles

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier


@Composable
fun AudioScreen(){
    val context = LocalContext.current
    val player = remember { SimpleAudioPlayer() }
    val audioFile = remember { AppFiles.audioFile(context) }
    var status by remember { mutableStateOf("Listo") }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            status = "Preparando..."
            player.prepareFromFile(
                file = audioFile,
                onCompleted = {status = "Terminado"},
                onError = {msg -> status = msg}
            )
        }) { Text("Preparar")}
        Button(onClick = {
            player.play { status = it }
            if (status == "Preparado") status = "Reproduciendo..."
        }) {Text("Play") }

        Button(
            onClick = {
                player.pause()
                status = "Pausado"
            }
        ) {Text("Pausado") }

        Button(onClick = {
            player.stop()
            status = "Parado"
        }) { Text("Stop") }

        Text(text = status)
    }
}