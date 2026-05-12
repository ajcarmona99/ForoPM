package com.example.mab_p2.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object ImageStorage{


    fun loadBitmap(file: File): Bitmap?{
        if(!file.exists()) return null
        return BitmapFactory.decodeFile(file.absolutePath)
    }
    fun saveBitmap(bitmap: Bitmap, file: File, format: Bitmap.CompressFormat) {
        FileOutputStream(file).use { out ->
            bitmap.compress(format, 100, out)
        }
    }

}