package com.example.mab_p2.interfaces

import com.example.mab_p2.R

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController

@Composable
fun VideoView(navController: NavHostController){
    var status by remember {mutableStateOf("Listo")}
    var context= LocalContext.current

    val player = remember{
        ExoPlayer.Builder(context).build()
    }
    DisposableEffect(Unit) {
        val listener=object: Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                status = when (playbackState) {
                    Player.STATE_IDLE -> "Listo"
                    Player.STATE_BUFFERING -> "Bufferizando"
                    Player.STATE_READY -> "Preparado"
                    else -> "?"
                }
            }
        }
        player.addListener(listener)

        onDispose{
            player.removeListener(listener)
            player.release()
        }
    }

    //Interfaz
    Column(
        Modifier.fillMaxSize().padding(40.dp).verticalScroll(rememberScrollState()),
        verticalArrangement= Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("NAVEGACIÓN")
        androidx.compose.material3.Button(onClick={navController.navigate(Routes.HOME)}){Text("Volver")}
        Divider()
        Text("REPRODUCTOR DE VIDEO")
        Text("Estado: ${status}")

        AndroidView(
            modifier=Modifier.fillMaxWidth().weight(1f),
            factory={
                PlayerView(it).apply{
                    this.player=player;
                }
            }
        )

        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){
            Button(onClick={player.play();
                status="Reproduciendo"}){Text("Play")}
            Button(onClick={player.pause()
                status="Pausado"}){Text("Pause")}
            Button(onClick={player.seekTo(0)
                status="Reiniciado"}){Text("Rewind")}
        }
        Button(onClick = {
            val uri= Uri.parse("android.resource://${context.packageName}/${R.raw.lago}")

            val item= MediaItem.fromUri(uri)
            player.setMediaItem(item)
            player.prepare()


        }){Text("Cargar video")}

    }
}