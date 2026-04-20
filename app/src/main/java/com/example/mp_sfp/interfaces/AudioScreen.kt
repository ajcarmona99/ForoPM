package com.example.mp_sfp.interfaces

import com.example.mp_sfp.media.SimpleAudioRecorder
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier


@Composable
fun AudioScreen(){
    val context = LocalContext.current
    val (hasAudioPerm, requestAudioPerm) = rememberAudioPermission()

    var status by remember { mutableStateOf("Listo") }

    val audioFile = remember { AppFiles.audioFile(context) }

    val player = remember { SimpleAudioPlayer() }
    val recoder = remember { SimpleAudioRecorder() }

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
        Text("Audio - Grabar y reproducir")
        Text("Permiso microfono: ${if (hasAudioPerm) "OK" else "NO"}")
        Text("Archivo: ${audioFile.name} (${if (audioFile.exists()) "existe" else "no existe"})")
        Text("Estado : ${status}")

        if(!hasAudioPerm){
            Button(onClick = requestAudioPerm) { Text("Pedir permiso de microfono") }

        }else{
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    player.stop()
                    recoder.start(audioFile){status = it}
                    status = "Grabando ..."
                },
                    enabled = !recoder.isRecording()) {
                    Text("REC")
                }

                Button(
                    onClick = {
                        recoder.stop()
                        status = "Grabación guardada"
                    }, enabled = recoder.isRecording()
                ) { Text("STOP REC")}
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    status = "Preparando..."
                    player.prepareFromFile(
                        file = audioFile,
                        onCompleted = { status = "Terminado" },
                        onError = { status = it }
                    )
                }) { Text("Preparar") }
                Button(onClick = {
                    player.play { status = it }
                    if (status == "Preparado") status = "Reproduciendo..."
                }) { Text("Play") }

                Button(
                    onClick = {
                        player.pause()
                        status = "Pausado"
                    }
                ) { Text("Pausado") }

                Button(onClick = {
                    player.stop()
                    status = "Parado"
                }) { Text("Stop") }
        }
        }
    }
}
