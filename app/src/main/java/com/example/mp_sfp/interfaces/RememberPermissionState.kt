package com.example.mp_sfp.interfaces

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat

import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import android.content.pm.PackageManager


@Composable
fun rememberPermissionState(permission: String): Pair<Boolean, () -> Unit>{
    val context = LocalContext.current
    var granted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> granted = isGranted }

    val request = {launcher.launch(permission)}
    return Pair(granted, request)
}


@Composable
fun rememberAudioPermission() =
    rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)


@Composable
fun rememberCameraPermission() =
    rememberPermissionState(android.Manifest.permission.CAMERA)
