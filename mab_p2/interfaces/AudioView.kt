package com.example.mab_p2.interfaces

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mab_p2.media.SimpleAudioPlayer
import com.example.mab_p2.media.SimpleAudioRecorder
import com.example.mab_p2.storage.appFiles
import com.example.mab_p2.storage.appFiles.audioFile
import com.example.mab_p2.storage.fechaHora

@Composable
fun AudioView(navController: NavHostController){

    val context = LocalContext.current
    //Variables de gabadora

    val (audioGranted, requestAudio)= RememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    var status by remember { mutableStateOf("Listo") }
    var recorder = remember { SimpleAudioRecorder() }

    //Variables del reproductor
    var playerAudioFile = remember { appFiles.audioFile(context) }

    var playerStatus by remember {mutableStateOf("Listo")}
    var actFile by remember { mutableStateOf("¡Carga un archivo!") }
    var player = remember{ SimpleAudioPlayer() }
    var grabaciones by remember { mutableStateOf(appFiles.listaArchivos(context.filesDir, "Audio-")) }


    DisposableEffect(Unit) {
        onDispose {
            recorder.stop()
            player.release()

        }
    }


    //Interfaz
    Column(
        Modifier.fillMaxSize().padding(40.dp).verticalScroll(rememberScrollState()),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.HOME)}){Text("Volver")}
        Divider()
        //___________GRABADORA
        Text("GRABAR NUEVO AUDIO")
        if(!audioGranted){
            Text("¡¡CONCEDE EL PERMISO!!")
            Text("Sin permiso de audio no podemos grabar, mi rey.")

            Button(onClick = {requestAudio()}){
                Text("Solicitar Audio")
            }
        }else{
            Text("GRABADORA")
            Text("Estado: ${status}")

            Button(
                onClick = {
                    appFiles.fecha = fechaHora()

                    val nuevoaudio=appFiles.audioFile(context)

                    recorder.start(nuevoaudio){
                        status=it
                    }
                    status="Grabando..."
                },
                //enabled = !recorder.isRecordirng()
            ) {
                Text("Grabar audio")
            }

            Button (onClick = {
                recorder.stop()
                status="Grabación guardada"

                grabaciones=appFiles.listaArchivos(context.filesDir, "Audio-")

                val file = appFiles.audioFile(context)
                Toast.makeText(context, "Audio grabado en: \n ${file.absolutePath}", Toast.LENGTH_LONG).show()
            },
                //enabled = recorder.isRecordirng()
            ){Text("Parar grabación")}

        }
        Divider()
        Text("REPRODUCTOR")
        Text(actFile)
        Text("Estado: ${playerStatus}")


        Button(onClick = {
            player.play{playerStatus=it}
            if (playerStatus!="No está preparado") playerStatus = "Reproduciendo..."
        }) { Text("Play")}
        Button(onClick={
            player.pause()
            playerStatus="Pausado"
        }){Text("Pause")}
        Button(onClick={
            player.stop()
            playerStatus="Parado"
            actFile="¡Carga un archivo!"
        }) { Text("Stop") }
        Divider()
        Text("¡IDEAS ANTERIORES!")
        Column(

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            grabaciones.forEach{grabacion->
                    Button(
                        onClick ={
                            Toast.makeText(context, "Listo patrón", Toast.LENGTH_LONG).show()
                            playerStatus= "Preparando"
                            playerAudioFile=grabacion
                            actFile="¡Archivo ${playerAudioFile.name} listo!"
                            player.prepareFromFile(
                                file = playerAudioFile,
                                onCompleted = {playerStatus= "Terminado"},
                                onError = {msg->playerStatus=msg}
                            )
                        }
                    ) {
                        Text(grabacion.name)
                    }


            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        

    }
}