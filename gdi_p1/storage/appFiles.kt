package com.example.gdi_p1.storage

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object appFiles{
    var fecha = fechaHora()

    fun listaArchivos(ruta: File): List<File>{
        val directorio = ruta
        return directorio.listFiles()?.filter { it.isFile }?: emptyList()
    }
    fun audioFile(context: Context): File=
        File(context.filesDir, "Idea-${fecha}.m4a")
}
fun fechaHora(): String {
    val ts = SimpleDateFormat("dd-MM-yyyy_HH-mm", Locale.getDefault()).format(Date())
    return ts
}