package com.example.mab_p2.storage

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object appFiles{
    var fecha = fechaHora()

    fun listaArchivos(ruta: File, prefijo: String): List<File>{
        val directorio = ruta
        return directorio.listFiles()?.filter { it.isFile && it.name.startsWith(prefijo) }?: emptyList()
    }
    fun audioFile(context: Context): File=
        File(context.filesDir, "Audio-${fecha}.m4a")
    fun photoFile(context: Context): File=
        File(context.filesDir, "Foto-${fecha}.m4a")
    fun cambiarExt(file: File, newExt: String): File{
        val name = file.nameWithoutExtension
        return File(file.parent, "$name.$newExt")
    }
}
fun fechaHora(): String {
    val ts = SimpleDateFormat("dd-MM-yyyy_HH-mm", Locale.getDefault()).format(Date())
    return ts
}