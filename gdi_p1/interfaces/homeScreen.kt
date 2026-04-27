package com.example.gdi_p1.interfaces

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gdi_p1.media.SimpleAudioPlayer
import com.example.gdi_p1.storage.appFiles

@Composable
fun homeScreen(navController: NavHostController){
    var context= LocalContext.current
    var audioFile = remember {appFiles.audioFile(context)}
    var status by remember {mutableStateOf("Listo")}
    var actFile by remember { mutableStateOf("¡Carga un archivo!") }
    var player = remember{ SimpleAudioPlayer()}
    val grabaciones = remember { appFiles.listaArchivos(context.filesDir) }
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(25.dp),
        verticalArrangement= Arrangement.spacedBy(12.dp)
    ) {
        Text("NUEVA GRABACIÓN")
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.REC)}){Text("Grabar nuevo audio")}
        Divider()
        Text("REPRODUCTOR")
        Text(actFile)
        Text("Estado: ${status}")
        /*
        Button(onClick ={
            status= "Preparando"
            player.prepareFromFile(
                file = audioFile,
                onCompleted = {status= "Terminado"},
                onError = {msg->status=msg}
            )
        }) { Text("Preparar") }
        */

        Button(onClick = {
            player.play{status=it}
            if (status!="No está preparado") status = "Reproduciendo..."
        }) { Text("Play")}
        Button(onClick={
            player.pause()
            status="Pausado"
        }){Text("Pause")}
        Button(onClick={
            player.stop()
            status="Parado"
            actFile="¡Carga un archivo!"
        }) { Text("Stop") }
        Divider()
        Text("¡IDEAS ANTERIORES!")
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(grabaciones){grabacion->
                Button(
                    onClick ={
                        Toast.makeText(context, "Listo patrón", Toast.LENGTH_LONG).show()
                        status= "Preparando"
                        audioFile=grabacion
                        actFile="¡Archivo ${audioFile.name} listo!"
                        player.prepareFromFile(
                            file = audioFile,
                            onCompleted = {status= "Terminado"},
                            onError = {msg->status=msg}
                        )
                    }
                ) {
                    Text(grabacion.name)
                }
            }
        }

    }
}




