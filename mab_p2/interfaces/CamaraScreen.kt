package com.example.mab_p2.interfaces

import com.example.mab_p2.storage.appFiles
import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.io.File

@Composable
fun CamaraScreen(navController: NavController){
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current


    val(hasCamPerm, requestCamPerm)= rememberPhotoPermissionState()

    var status by remember {mutableStateOf("Listo")}
    var lastFileName by remember {mutableStateOf("Ninguna")}

    var imageCapture by remember{ mutableStateOf<ImageCapture?>(null)}


    //Interfaz
    Column(
        Modifier.fillMaxSize().padding(25.dp).verticalScroll(rememberScrollState()),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick={navController.navigate(Routes.PHO)}){
            Text("<Atrás")
        }
        Text("Permiso de cámara: ${if (hasCamPerm) "CONCEDIDO" else "DENEGADO"}")
        Text("Estado: ${status}")
        Text("Última foto: ${lastFileName}")
        if(!hasCamPerm){
            Button(onClick={requestCamPerm()}){
                Text("Solicitar permiso")
            }
        }else{
        AndroidView<androidx.camera.view.PreviewView>(
            factory = { ctx ->val previewView = androidx.camera.view.PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Configuramos la vista previa
                    val preview = androidx.camera.core.Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    // Inicializamos el capturador de fotos
                    imageCapture = ImageCapture.Builder().build()

                    try {
                        // Desvinculamos cualquier uso previo y vinculamos al ciclo de vida
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        status = "Error al iniciar cámara: ${e.message}"
                    }
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Button(onClick={
            val capture=imageCapture
            if (capture==null){
                status="Captura de imagen no disponible"
            }

            val file: File =appFiles.photoFile(context)
            val options=ImageCapture.OutputFileOptions.Builder(file).build()

            status="capturado"

            capture?.takePicture(
                options,
                ContextCompat.getMainExecutor(context),
                object: ImageCapture.OnImageSavedCallback{
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        status="Foto guardada en: ${file.absolutePath}"
                        lastFileName=file.name
                    }

                    override fun onError(p0: ImageCaptureException) {
                        status = "Error capturando: ${p0.message}"
                    }
                }
            )
        }
        ) {Text("Hacer foto") }
        Spacer(modifier = Modifier.height(30.dp))

        }

    }
}
