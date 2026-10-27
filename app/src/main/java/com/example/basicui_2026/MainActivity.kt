package com.example.basicui_2026

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.basicui_2026.ui.theme.BasicUI_2026Theme

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
    Box {
        LazyColumnExample(modifier)
        ColumnExample(modifier)
        TextFieldExample(modifier)
        // 修正した3つ並べるパーツを配置
        ThreeAnimalsRow(modifier)
    }
}

@Composable
fun LazyColumnExample(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(text = "Header")
        }
        items(50) { index ->
            Text(text = "$index")
        }
        item {
            Text(text = "Footer")
        }
    }
}

@Composable
fun ThreeAnimalsRow(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // 任意の動物を3つ選ぶ（例：鴨子、企鵝、青蛙）
    val animals = listOf(
        Animal("鴨子", R.drawable.animal0),
        Animal("企鵝", R.drawable.animal1),
        Animal("青蛙", R.drawable.animal2)
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // 画面の真ん中に配置
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // 3つを綺麗に等間隔で配置
            verticalAlignment = Alignment.CenterVertically
        ) {
            animals.forEachIndexed { index, animal ->
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFD0BCFF)
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "這是 ${animal.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    ) {
                        Image(
                            painter = painterResource(id = animal.imageRes),
                            contentDescription = animal.name,
                            modifier = Modifier.size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$index ${animal.name}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnExample(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x8AE0BBE4))
            .pointerInput(Unit) {
                detectTapGestures { }
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("行動應用軟體開發：基本元件")
        Text("作者：佐藤瑠瑠花")

        Spacer(modifier = Modifier.height(8.dp))
        RowExample()
    }
}

@Composable
fun RowExample(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
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

data class Animal(
    val name: String,
    @DrawableRes val imageRes: Int
)