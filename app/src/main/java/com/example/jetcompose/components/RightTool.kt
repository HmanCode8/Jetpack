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
import es.dmoral.toasty.Toasty

data class IconItem(
    val name: String,
    val id: String
)
@Composable
fun RightTool(modifier: Modifier = Modifier) {
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

                val iconName = contentNow.resources.getIdentifier("tool_${t}","drawable",contentNow.packageName)
                Icon(
                    painter = painterResource(iconName),
                    contentDescription = "layer",
                    tint = Color.Gray,
                    modifier= Modifier.size(30.dp).padding(3.dp,5.dp).clickable{
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

                val iconName = contentNow.resources.getIdentifier(
                    "tool_${t}",
                    "drawable",
                    contentNow.packageName
                )
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
                        painter = painterResource(iconName),
                        tint = Color.Gray,
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
    )
}