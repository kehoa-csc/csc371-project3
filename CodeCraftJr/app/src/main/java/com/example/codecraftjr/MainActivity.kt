package com.example.codecraftjr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.codecraftjr.ui.theme.CodeCraftJrTheme
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()
        setContent {
            CodeCraftJrTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    //Background
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )

    //Game board
    Row(horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxSize()) {
        for (i in 1..8) {
            Column() {
                for (j in 1..6) {
                    Image(
                        painter = painterResource(id = R.drawable.dirt),
                        contentDescription = "dirt",
                        modifier = Modifier.size(calcBlockSize().dp).border(0.5.dp, color = Color.Gray)
                    )
                }
            }
        }
    }
    //Bottom code bar
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxSize())
        {
            for (i in 1..8) {
                Image(
                    painter = painterResource(id = R.drawable.stone),
                    contentDescription = "stone",
                    modifier = Modifier.size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
                )
            }
    }
}

@Composable
fun calcBlockSize(): Int {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp
    if (height < width) {
        return height/7
    }
    return width/9
}

@Preview(showBackground = true, device="spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
@Composable
fun GreetingPreview() {
    CodeCraftJrTheme {
        Greeting()
    }
}