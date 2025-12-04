package com.example.codecraftjr

import android.content.ClipData
import android.content.ClipDescription
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codecraftjr.ui.theme.CodeCraftJrTheme
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val codebar = remember { mutableStateListOf<String>() }
    val level = remember { mutableStateListOf(
        mutableStateListOf(0,0,0,0,0,0,0,0),
        mutableStateListOf(1,1,1,0,0,0,0,0),
        mutableStateListOf(0,0,1,0,0,0,0,0),
        mutableStateListOf(0,0,1,1,1,1,1,1),
        mutableStateListOf(0,0,0,0,0,0,0,0),
        mutableStateListOf(0,0,0,0,0,0,0,0),
    )}
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
        for (i in (0..7)) {
            Column() {
                for (j in 0..5) {
                    var blockId: Int = R.drawable.dirt
                    when (level[j][i]) {
                        1 -> blockId = R.drawable.stone
                    }
                    Image(
                        painter = painterResource(id = blockId),
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
        modifier = Modifier.fillMaxSize()
            .dragAndDropTarget(
                shouldStartDragAndDrop = {event -> event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)},
                target = remember {
                    object: DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            val text: String = event.toAndroidDragEvent().clipData?.getItemAt(0)?.text as String
                            if (codebar.size<8) {
                                codebar.add(text)
                            }
                            return true
                        }
                    }
                }
            )
    ) {
        for (i in 0..7) {
            var blockId: Int = R.drawable.stone
            if (i < codebar.size) {
                when (codebar[i]) {
                    "right_arrow" -> blockId = R.drawable.right
                }
            }
            Image(
                painter = painterResource(id = blockId),
                contentDescription = "stone",
                modifier = Modifier.size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
            )
        }
    }
    Column(horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(64.dp)) {
        Image(
            painter = painterResource(id = R.drawable.right),
            contentDescription = "right block",
            modifier = Modifier
                .size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
                .dragAndDropSource{
                    detectTapGestures(
                        onLongPress = { offset ->
                            startTransfer(
                                transferData = DragAndDropTransferData(
                                    clipData = ClipData.newPlainText("codeblock", "right_arrow")
                                )
                            )
                        }
                    )
                }
        )
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