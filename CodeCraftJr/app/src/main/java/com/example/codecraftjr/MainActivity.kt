package com.example.codecraftjr

import android.content.ClipData
import android.content.ClipDescription
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.codecraftjr.ui.theme.CodeCraftJrTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()
        setContent {
            CodeCraftJrTheme {
                //Game(levelTheme="caveLevel",level=caveLevel)
                MainMenu()
            }
        }
    }

var plays = mutableStateMapOf("Andrew" to 0, "Jason" to 0, "Charlotte" to 0)

@Composable
fun MainMenu() {
    val mp = MediaPlayer.create(this,R.raw.click)
    //Background
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        //Text(text="CodeCraft Jr.", fontSize = 48.sp)
        Image(
            painter = painterResource(R.drawable.title),
            contentDescription = "title logo",
            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp/2,LocalConfiguration.current.screenHeightDp.dp/3)
        )
        Row(modifier = Modifier.padding(0.dp)) {
            Button(content = {Text("Parents", fontSize = 36.sp)},modifier = Modifier.padding(20.dp,0.dp),
                onClick = {
                    setContent{
                        CodeCraftJrTheme {
                            AdultLoginRegister()
                        }
                    }
                    mp.start()
                })
            Button(content = {Text("  Kids  ", fontSize = 36.sp)}, modifier = Modifier.padding(20.dp,0.dp),
                onClick = {
                    setContent{
                        CodeCraftJrTheme {
                            KidsProfiles()
                        }
                    }
                    mp.start()
                })
        }
    }
}

@Composable
fun AdultLoginRegister() {
    val mp = MediaPlayer.create(this,R.raw.click)
    var firstname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var invalidLogin by remember { mutableStateOf(false) }
    //Background
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(0.dp,24.dp)) {
        Text(text="Adult Portal", fontSize = 48.sp)
        TextField(
            value = firstname,
            onValueChange = { firstname = it },
            label = { Text("First Name") },
            modifier = Modifier.padding(0.dp,16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(0.dp,16.dp),
        )
        Row(modifier = Modifier.padding(0.dp)) {
            /*Button(content = {Text("Register", fontSize = 24.sp)},modifier = Modifier.padding(20.dp,24.dp),
                onClick = {
                    //save login and show popup
                })*/
            Button(content = {Text("  Log in  ", fontSize = 24.sp)}, modifier = Modifier.padding(20.dp,24.dp),
                onClick = {
                    mp.start()
                    if (firstname=="sample" && password=="sample")  {
                        setContent{
                            CodeCraftJrTheme {
                                AdultPortal()
                            }
                        }
                    } else {
                        invalidLogin=true
                    }
                })
        }
    }
    if (invalidLogin) {
        AlertDialog(onDismissRequest = {invalidLogin=false},
            title = { Text("Invalid Login") },
            text = { Text("Temporary login is name: sample password: sample") },
            confirmButton = {
                TextButton(onClick = {invalidLogin=false}) {
                    Text("OK")
                }
            })
    }
}

@Composable
fun AdultPortal() {
    val mp = MediaPlayer.create(this,R.raw.click)
    //Background
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(0.dp,24.dp)) {
        Text(text="Games played", fontSize = 48.sp, modifier = Modifier.padding(0.dp,24.dp))
        for (i in 0..<plays.size) {
            Text("${plays.keys.elementAt(i)}: ${plays.values.elementAt(i)}", fontSize = 36.sp)
        }
        Button(content = {Text("Sign Out", fontSize = 24.sp)}, modifier = Modifier.padding(20.dp,24.dp),
        onClick = {
            mp.start()
            setContent{
                CodeCraftJrTheme {
                    MainMenu()
                }
            }
        })
        }
}

@Composable
fun KidsProfiles() {
    val mp = MediaPlayer.create(this,R.raw.click)
    //Background
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(0.dp,32.dp)) {
        Text("Hi! Pick a profile.", fontSize = 36.sp)
        for (i in 0..<plays.size) {
            Button(content = {Text(plays.keys.elementAt(i), fontSize = 36.sp)},modifier = Modifier.padding(0.dp,16.dp).width(LocalConfiguration.current.screenWidthDp.dp/3),
                onClick = {
                    mp.start()
                    setContent { KidMenu(plays.keys.elementAt(i)) }
                })
        }
    }
}
    val overLevel = mutableStateListOf(
        mutableStateListOf(0,0,0,0,0,0,0,0),
        mutableStateListOf(1,1,1,0,0,0,0,0),
        mutableStateListOf(0,0,1,0,0,0,0,0),
        mutableStateListOf(0,0,1,1,1,1,1,1),
        mutableStateListOf(0,0,0,0,0,0,0,0),
        mutableStateListOf(0,0,0,0,0,0,0,0),
    )
    val caveLevel = mutableStateListOf(
        mutableStateListOf(0,1,1,1,1,0,0,0),
        mutableStateListOf(0,1,0,0,1,0,0,0),
        mutableStateListOf(0,1,0,1,1,0,0,0),
        mutableStateListOf(1,1,0,1,0,0,1,1),
        mutableStateListOf(0,0,0,1,1,1,1,0),
        mutableStateListOf(0,0,0,0,0,0,0,0),
    )
@Composable
fun KidMenu(name: String) {
    val mp = MediaPlayer.create(this,R.raw.click)
    Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = "background",
        modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
    )
    Button(content = {Text("Sign Out", fontSize = 24.sp)}, modifier = Modifier.padding(20.dp,24.dp), onClick = {mp.start(); setContent { MainMenu() }})

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(0.dp,32.dp)) {
        Text("Hi, ${name}! Pick a level.", fontSize = 36.sp)
        Button(content = {Text("Overworld", fontSize = 36.sp)},modifier = Modifier.padding(0.dp,LocalConfiguration.current.screenWidthDp.dp/50).width(LocalConfiguration.current.screenWidthDp.dp/3),
            onClick = {
                mp.start(); setContent { Game(levelTheme="overLevel",level=overLevel,user=name) }
            })
        Button(content = {Text("Caves", fontSize = 36.sp)},modifier = Modifier.padding(0.dp,LocalConfiguration.current.screenWidthDp.dp/50).width(LocalConfiguration.current.screenWidthDp.dp/3),
            onClick = {
                mp.start(); setContent { Game(levelTheme="caveLevel",level=caveLevel, user=name) }
            })
        Text("Random Level", fontSize = 24.sp)
        Row(modifier = Modifier.padding(0.dp)) {
            Button(content = {Text("Normal", fontSize = 28.sp)},modifier = Modifier.padding(16.dp,LocalConfiguration.current.screenWidthDp.dp/50).width(LocalConfiguration.current.screenWidthDp.dp/6),
                onClick = {
                    val lg = LevelGenerator()
                    mp.start(); setContent { Game(levelTheme="overLevel",level=lg.generateLevel(),user=name) }
                })
            Button(content = {Text("Hard", fontSize = 28.sp)},modifier = Modifier.padding(16.dp,LocalConfiguration.current.screenWidthDp.dp/50).width(LocalConfiguration.current.screenWidthDp.dp/6),
                onClick = {
                    val lg = LevelGenerator()
                    mp.start(); setContent { Game(levelTheme="caveLevel",level=lg.generateLevel(hard_mode=true), user=name) }
                })
        }

    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Game(modifier: Modifier = Modifier,levelTheme: String, level:SnapshotStateList<SnapshotStateList<Int>>,user:String="user") {
    val mp = MediaPlayer.create(this,R.raw.click)
    val codebar = remember { mutableStateListOf<String>() }

    //Steve positioning
    val steveStart = remember {mutableListOf(0,0)} //(x,y)
    for(i in 0..5) { //determine starting position
        if (level[i][0] == 1) {steveStart[1]=i}
    }
    val stevePos = remember {mutableStateListOf(steveStart[0],steveStart[1])}
    //Background
    if (levelTheme=="caveLevel") {
        Scaffold(containerColor = Color.hsl(0F, 0F,.165F)) { }
        Image(
            painter = painterResource(R.drawable.backgroundcave),
            contentDescription = "background",
            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
        )
    } else {
        Scaffold(containerColor = Color.hsl(216F, .84F,.902F)) { }
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp)
        )
    }


    //Game board
    Row(horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxSize()) {
        for (i in (0..7)) {
            Column() {
                for (j in 0..5) {
                    var blockId: Int = R.drawable.dirt
                    if (levelTheme=="caveLevel") {
                        blockId = R.drawable.stone
                        when (level[j][i]) {
                            1 -> blockId = R.drawable.stone_dark
                        }
                    } else {
                        when (level[j][i]) {
                            1 -> blockId = R.drawable.stone
                        }
                    }
                    Box(modifier = Modifier.size(calcBlockSize().dp)) {
                        Image(
                            painter = painterResource(id = blockId),
                            contentDescription = "block",
                            modifier = Modifier.border(0.5.dp, color = Color.Gray)
                        )
                        if (stevePos[0]==i && stevePos[1]==j) {
                            Image(
                                painter = painterResource(id = R.drawable.steve_walk),
                                contentDescription = "steve",
                            )
                        }
                    }
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
                            if (codebar.size<9) {
                                codebar.add(text)
                            }
                            return true
                        }
                    }
                }
            )
    ) {
        for (i in 0..8) {
            var blockId: Int = R.drawable.stone
            if (i < codebar.size) {
                when (codebar[i]) {
                    "right_arrow" -> blockId = R.drawable.right
                    "left_arrow" -> blockId = R.drawable.left
                    "up_arrow" -> blockId = R.drawable.up
                    "down_arrow" -> blockId = R.drawable.down
                }
            }
            Image(
                painter = painterResource(id = blockId),
                contentDescription = "stone",
                modifier = Modifier.size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
            )
        }
    }
    //draggable blocks
    Column(horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(64.dp)) {
        val draggableBlockImages = listOf(R.drawable.right,R.drawable.left,R.drawable.up,R.drawable.down)
        val draggableBlockMessages = listOf<String>("right_arrow","left_arrow","up_arrow","down_arrow")
        for(i in (0..3)) {
            Image(
                painter = painterResource(id = draggableBlockImages[i]),
                contentDescription = draggableBlockMessages[i],
                modifier = Modifier
                    .size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
                    .dragAndDropSource {
                        detectTapGestures(
                            onLongPress = { offset ->
                                startTransfer(
                                    transferData = DragAndDropTransferData(
                                        clipData = ClipData.newPlainText("codeblock", draggableBlockMessages[i])
                                    )
                                )
                            }
                        )
                    }
            )
        }
    }
    //ui buttons
    val coroutineScope = rememberCoroutineScope()
    val win = remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(64.dp)) {
            TextButton(content = {
                Image(
                    painter = painterResource(id = R.drawable.go),
                    contentDescription = "go button",
                    modifier = Modifier
                        .size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
                )
            }, onClick = { //steve movement
                mp.start()
                stevePos[0]=steveStart[0]; stevePos[1]=steveStart[1]
                coroutineScope.launch {
                    for (i in 0..<codebar.size) {
                        if (codebar[i] == "right_arrow") {
                            while(stevePos[0]<7) {
                                if (level[stevePos[1]][stevePos[0]+1]==1) {
                                    delay(300)
                                    stevePos[0]+=1
                                } else {break}
                            }
                        } else if (codebar[i] == "left_arrow") {
                            while(stevePos[0]>0) {
                                if (level[stevePos[1]][stevePos[0]-1]==1) {
                                    delay(300)
                                    stevePos[0]-=1
                                } else {break}
                            }
                        } else if (codebar[i] == "up_arrow") {
                            while(stevePos[1]>0) {
                                if (level[stevePos[1]-1][stevePos[0]]==1) {
                                    delay(300)
                                    stevePos[1]-=1
                                } else {break}
                            }
                        } else if (codebar[i] == "down_arrow") {
                            while(stevePos[1]<5) {
                                if (level[stevePos[1]+1][stevePos[0]]==1) {
                                    delay(300)
                                    stevePos[1]+=1
                                } else {break}
                            }
                        }
                        if (stevePos[0]==7) {
                            win.value = true
                            break
                        }
                    }
                }

            })
            TextButton(content = {
                Image(
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = "exit button",
                    modifier = Modifier
                        .size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
                )
            }, onClick = {mp.start(); setContent { KidMenu(name=user) }})
        TextButton(content = {
            Image(
                painter = painterResource(id = R.drawable.clear),
                contentDescription = "clear button",
                modifier = Modifier
                    .size(calcBlockSize().dp).border(0.5.dp, color = Color.Black)
            )
        }, onClick = {mp.start(); codebar.clear()})
    }
    if (win.value) {
        //val previousPlays: Int? = plays[user]
        plays.set(user,1)
        Dialog(content = {
            Card() { Text("Great work!\n\nPress anywhere to continue",modifier=Modifier.padding(24.dp,24.dp), fontSize = 28.sp)}
        },onDismissRequest = {win.value = false})
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
fun Preview() {
    CodeCraftJrTheme {
        //Game(levelTheme = "caveLevel", level = caveLevel)
        KidMenu("test")
        //KidsProfiles()
    }
}
}