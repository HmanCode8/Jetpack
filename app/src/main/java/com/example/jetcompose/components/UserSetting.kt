package com.example.jetcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.untils.MChildren

data class Mode(
    val label: String,
    val id: Int
)

@Composable
fun UserSetting(
    onSettingChange: (Any?) -> Unit
) {
    val languages = remember { mutableStateOf(listOf("En", "简", "繁")) }
    val languageActive = remember { mutableStateOf("En") }
    val modeList = remember { mutableStateOf(listOf(Mode("online", 1), Mode("offline", 2))) }
    val menus = remember {
        mutableStateOf(
            listOf(
                MChildren("Data Operation", 1, true),
                MChildren("User-Defined Layer", 2, true),
                MChildren("Offline Data Management ", 3, true),
                MChildren("Contact", 4, false)
            )
        )
    }
    val fontSizeActive = remember { mutableStateOf(1) }
    val modeActive = remember { mutableStateOf(1) }
    Box(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 80.dp, start = 20.dp)
            .background(
                brush = Brush.verticalGradient(
                    0.01f to Color(0xFFe2f3fd), // 主色
                    0.5f to Color(0xFFf9fcff), // 主色
                    0.2f to Color(0xFFFFFFFF), // 主色

                ), shape = RoundedCornerShape(4.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp, 5.dp)
                .verticalScroll(rememberScrollState()),

            ) {
            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                Text("Hi Admin", color = Color(0xFF616161), fontSize = 10.sp)
            }
            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                Text("My Setting", color = Color(0xFF616161), fontSize = 10.sp)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row() {
                    languages.value.forEachIndexed { index, v ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(if (index == 1) 2.dp else 0.dp, 0.dp)
                                .width(20.dp)
                                .height(20.dp)
                                .background(
                                    color = Color(
                                        if (languageActive.value != v) 0xFFF4F6F9 else 0xFF0091ea
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable() {
                                    languageActive.value = v
                                }
                        ) {
                            Text(
                                text = v,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                color = if (languageActive.value == v) Color.White else Color.Black
                            )
                        }

                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
                    listOf("A", "A", "A").forEachIndexed { index, f ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(2.dp, 0.dp)
                                .width(20.dp)
                                .height(20.dp)
                                .background(
                                    color = Color(
                                        if (fontSizeActive.value != index + 1) 0xFFF4F6F9 else 0xFF0091ea
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable {
                                    fontSizeActive.value = index + 1
                                }
                        ) {
                            Text(
                                text = f,
                                textAlign = TextAlign.Center,
                                color = if (fontSizeActive.value == index + 1) Color.White else Color.Black,
                                fontSize = 12.sp,

                                )
                        }

                    }
                }
            }
            Row() {
                Text(
                    text = "Mode Switch",
                    color = Color(0xFF616161),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(0.dp, 5.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                modeList.value.forEachIndexed { index, mode ->
                    if (index != 0) Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = mode.label,
                        color = if (modeActive.value == mode.id) Color.White else Color.Black,
                        modifier = Modifier
                            .background(
                                Color(if (modeActive.value == mode.id) 0xFF0091ea else 0xFFF4F6F9),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(5.dp)
                            .clickable {
                                modeActive.value = mode.id
                            }
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            menus.value.forEachIndexed { index, m ->
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF4F6F9), shape = RoundedCornerShape(3.dp))
                        .padding(3.dp)
                        .clickable {
                            onSettingChange(m)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = m.label, fontSize = 10.sp, modifier = Modifier.padding(0.dp, 5.dp))
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "in",
                        modifier = Modifier.size(8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "log out",
                textAlign = TextAlign.Center,
                color = Color(0xFFDE5E5E),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFDE5E5E), shape = RoundedCornerShape(3.dp))
                    .padding(0.dp, 5.dp)


            )

        }
    }
}

@Composable
@Preview
fun PreviewUIser() {
    UserSetting({})
}