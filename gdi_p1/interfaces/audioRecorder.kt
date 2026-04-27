package com.example.gdi_p1.interfaces

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.gdi_p1.media.SimpleAudioRecorder
import com.example.gdi_p1.storage.appFiles


@Composable
fun audioRecorder(navController: NavHostController){
    val context = LocalContext.current
    val (audioGranted, requestAudio)= RememberPermissionState(android.Manifest.permission.RECORD_AUDIO)


    val audioFile = remember { appFiles.audioFile(context) }
    val (hasAudioPerm, requestAudioPerm)= rememberAudioPermissionState()

    var status by remember { mutableStateOf("Listo") }


    var recorder = remember { SimpleAudioRecorder() }

    DisposableEffect(Unit) {
        onDispose {
            recorder.stop()
        }
    }

    Column(
        Modifier.fillMaxSize().padding(25.dp),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.HOME)}){Text("Volver")}
        Divider()
        if(!hasAudioPerm){
            Text("¡¡CONCEDE EL PERMISO!!")
            Text("Sin permiso de audio no podemos grabar, mi rey.")
            Text("Una vez aceptados los permisos vuelve a la pantalla principal y carga esta d enuevo para acceder a la grabadora.")

            Button(onClick = {requestAudio()}){
                Text("Solicitar Audio")
            }
        }else{
                Text("GRABADORA")
                Text("Estado: ${status}")

                Button(
                    onClick = {
                        recorder.start(audioFile){
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
                        val file = appFiles.audioFile(context)
                        Toast.makeText(context, "Audio grabado en: \n ${file.absolutePath}", Toast.LENGTH_LONG).show()
                    },
                    //enabled = recorder.isRecordirng()
                ){Text("Parar grabación")}

        }
        Divider()



    }
}