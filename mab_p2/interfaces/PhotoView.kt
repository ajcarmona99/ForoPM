package com.example.mab_p2.interfaces

import android.graphics.Bitmap
import com.example.mab_p2.storage.ImageStorage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mab_p2.storage.appFiles

@Composable
fun PhotoView(navController: NavHostController){

    val context=LocalContext.current
    var filesList by remember {mutableStateOf(appFiles.listaArchivos(context.filesDir, "Foto-"))}
    var posAct by remember { mutableStateOf(if(filesList.isNotEmpty())filesList.size-1 else 0) }
    var fotoAct = filesList[posAct]
    var mensStore by remember{mutableStateOf("...")}



    //Interfaz
    Column(
        Modifier.fillMaxSize().padding(25.dp).verticalScroll(rememberScrollState()),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
            Button(onClick={navController.navigate(Routes.HOME)}){Text("<Atrás")}
            Button(onClick={navController.navigate(Routes.CAM)}){Text("Nueva foto")}
        }

        Text("GALERÍA")
        Text("${fotoAct.name}")
        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
            fotoAct?.let{
                Column(Modifier.weight(1f)){
                    androidx.compose.foundation.Image(
                        bitmap = ImageStorage.loadBitmap(it)!!.asImageBitmap(),
                        contentDescription = null,
                        modifier=Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
            Button(onClick={
                if(posAct>0) posAct-- else posAct=filesList.size-1
            }){Text("<Anterior")}
            Button(onClick={
                if(posAct<filesList.size-1) posAct++ else posAct=0
            }){Text("Siguiente>")}
        }
        Text("Guardar imagen como:")
        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
            Button(onClick={
                var fileChange=appFiles.cambiarExt(fotoAct, "png")
                var actBitmap=ImageStorage.loadBitmap(fotoAct)
                if (actBitmap!=null){
                    ImageStorage.saveBitmap( actBitmap ,fileChange, Bitmap.CompressFormat.PNG)
                    filesList = appFiles.listaArchivos(context.filesDir, "Foto-")

                    mensStore="PNG guardado: ${fileChange.absolutePath}"
                }else{
                    mensStore="No hay imagen para guardar"
                }
            }){Text("PNG")}
            Button(onClick={
                var fileChange=appFiles.cambiarExt(fotoAct, "jpeg")
                var actBitmap=ImageStorage.loadBitmap(fotoAct)
                if (actBitmap!=null){
                    ImageStorage.saveBitmap( actBitmap ,fileChange, Bitmap.CompressFormat.JPEG)
                    filesList = appFiles.listaArchivos(context.filesDir, "Foto-")

                    mensStore="JPEG guardado: ${fileChange.absolutePath}"
                }else{
                    mensStore="No hay imagen para guardar"
                }
            }){Text("JPEG")}
        }
        Text(mensStore)
        Spacer(modifier = Modifier.height(30.dp))
    }
}