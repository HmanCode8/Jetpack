package com.example.jetcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetcompose.R
import com.example.jetcompose.untils.MapToolsUntil
import com.example.jetcompose.untils.getDrawable
import es.dmoral.toasty.Toasty


@Composable
fun RightTool(modifier: Modifier = Modifier,callback:(key: String)-> Unit) {
val contentNow = LocalContext.current
    val tools = remember { mutableStateOf(listOf("tuceng","ceju","draw","shuxing","qingchu"))}
    val tools1 = remember { mutableStateOf(listOf("zhibeizhen","shangyibu","xiayibu","fangda","suoxiao","dingwei"))}

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(3.dp, 13.dp)
                .background(Color.White, shape = RoundedCornerShape(2.dp))
        ) {
            tools.value.forEach { t->
                Spacer(modifier= Modifier.height(5.dp))

                Icon(
                    painter =painterResource( getDrawable("tool_${t}")),
                    contentDescription = "layer",
                    tint = Color(0xFF7289a6),
                    modifier= Modifier.size(30.dp).padding(3.dp,5.dp).clickable{
                        callback(t)
                        Toasty.info(contentNow,t).show()
                    },
                )
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            tools1.value.forEach {t->

                Spacer(modifier= Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.White, shape = RoundedCornerShape(2.dp)).clickable{
                           when(t){
                               "fangda"-> MapToolsUntil.zoomIn()
                               "suoxiao"-> MapToolsUntil.zoomOut()
                               "dingwei"-> MapToolsUntil.locateToCurrentLocation()
                           }
                        }
                ) {
                    Icon(
                        painter =painterResource( getDrawable("tool_${t}")),
                        tint = Color(0xFF7289a6),
                        contentDescription = "11",
                        modifier = Modifier.padding(3.dp, 5.dp)
                    )
                }
            }

        }

    }
}

@Composable
@Preview
fun  Preview(){
    RightTool(
        modifier = Modifier
            .padding(end = 10.dp)
        ,{}
    )
}