package com.example.basicui_2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.basicui_2026.ui.theme.BasicUI_2026Theme
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.annotation.RawRes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicUI_2026Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    // 💡 1. どの動画を表示しているかを保持する状態（初期値は R.raw.love）
    var currentVideoRes by remember { mutableStateOf(R.raw.love) }

    Box(modifier = modifier.fillMaxSize()) {
        ColumnExample(modifier)
        TextFieldExample(modifier)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 💡 2. 選択されている動画を再生するプレーヤー
            SimpleVideoPlayer(videoRes = currentVideoRes)

            Spacer(modifier = Modifier.height(8.dp))

            // 💡 3. ボタンを押して動画を切り替える
            Button(onClick = {
                // ここで別の動画IDに切り替えます（例：handpan という動画ファイルがある場合）
                // もしくは別の動画リソース名に変更してください
                currentVideoRes = if (currentVideoRes == R.raw.love) {
                    R.raw.handpan // 👈 もう一つの動画の res/raw 名（例: R.raw.handpan）
                } else {
                    R.raw.love
                }
            }) {
                Text(text = if (currentVideoRes == R.raw.love) "切換至手碟影片" else "切換至Love影片")
            }

            Spacer(modifier = Modifier.height(8.dp))
            LazyRowExample(modifier)
        }
    }
}

//@Composable
//fun LazyColumnExample(modifier: Modifier = Modifier) {
//    LazyColumn (modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        // Add a single item
//        item {
//            Text(text = "Header")
//        }
//
//        // Add 50 items
//        items(50) { index ->
//            Text(text = "$index")
//        }
//
//        // Add another single item
//        item {
//            Text(text = "Footer")
//        }
//    }
//}

@Composable
fun ColumnExample(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x8AE0BBE4))
            .pointerInput(Unit) {
                // 💡 強行消費（攔截）該區域內所有的觸控與滑動手勢
                detectTapGestures { }
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("行動應用軟體開發：基本元件")
        Text("作者：佐藤瑠瑠花")

        Spacer(modifier = Modifier.height(8.dp))
        RowExample(modifier)
    }
}

@Composable
fun RowExample(modifier: Modifier = Modifier) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Android icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Yellow),
            alpha = 0.6f,
        )

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Compose icon",
            modifier = Modifier.size(100.dp)
        )
    }
}

data class Animal(
    val name: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun LazyRowExample(modifier: Modifier = Modifier) {
    val animals = listOf(
        Animal("鴨子", R.drawable.animal0),
        Animal("企鵝", R.drawable.animal1),
        Animal("青蛙", R.drawable.animal2),
        Animal("貓頭鷹", R.drawable.animal3),
        Animal("海豚", R.drawable.animal4),
        Animal("牛", R.drawable.animal5),
        Animal("無尾熊", R.drawable.animal6),
        Animal("獅子", R.drawable.animal7),
        Animal("狐狸", R.drawable.animal8),
        Animal("小雞", R.drawable.animal9)
    )
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 直接傳入 animals 清單，每個迴圈拿到一個 animal 物件
        itemsIndexed(animals) { index, animal ->
            Card(
                modifier = Modifier.fillParentMaxWidth(1.0f)
                    .height(260.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD0BCFF)
                )
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = animal.imageRes),
                        contentDescription = animal.name,
                        modifier = Modifier.size(150.dp)
                            .clickable {
                                // 點擊時跳出 Toast 訊息
                                Toast.makeText(
                                    context,
                                    "你點選的是 ${animal.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$index ${animal.name}"
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldExample(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("請輸入文字") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (inputText.isBlank()) "尚未輸入內容" else "您輸入的是：$inputText"
        )
    }
}

@Composable
fun SimpleVideoPlayer(
    @RawRes videoRes: Int
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    // 💡 videoRes が変わるたびに、プレイヤーの再生ソースを新しくセットし直す
    LaunchedEffect(videoRes) {
        val uri = "android.resource://${context.packageName}/$videoRes"
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}