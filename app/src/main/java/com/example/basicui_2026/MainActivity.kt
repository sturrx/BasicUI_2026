package com.example.basicui_2026

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun VideoSwitcherScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var currentVideoRes by remember { mutableStateOf<Int>(R.raw.love) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    LaunchedEffect(currentVideoRes) {
        val uri = Uri.parse("android.resource://${context.packageName}/$currentVideoRes")
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            currentVideoRes = if (currentVideoRes == R.raw.love) {
                R.raw.handpan
            } else {
                R.raw.love
            }
        }) {
            Text(text = "切換影片 (切換至 " + if (currentVideoRes == R.raw.love) "handpan" else "love" + ")")
        }
    }
}