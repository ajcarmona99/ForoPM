package com.example.gdi_p1.interfaces

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RememberPermissionState(permission: String): Pair<Boolean, ()-> Unit>{
    val context = LocalContext.current
    var granted by remember {
        mutableStateOf(
            value = ContextCompat.checkSelfPermission(context, permission)== PackageManager.PERMISSION_GRANTED
        )
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> granted = isGranted }
    val request = {launcher.launch(permission)}
    return Pair(granted, request)
}
@Composable
fun rememberAudioPermissionState(): Pair<Boolean, ()-> Unit>{
     return RememberPermissionState(android.Manifest.permission.RECORD_AUDIO)
}
