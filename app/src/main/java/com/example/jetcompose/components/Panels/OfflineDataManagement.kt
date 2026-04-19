package com.example.jetcompose.components.Panels

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.services.MockData
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty

data class ResultItem(
    val regionName: String?,
    val scale: String?,
    val DateTime: String?,
    val selected: Boolean = false // 改回 val！！！
)

@Composable
fun OfflineDataManagement() {
    val regionData = MockData.regionData
    val activeParent = remember { mutableStateOf("") }
    val activeChild = remember { mutableStateOf("") }
    val isResult = remember { mutableStateOf(false) }

    // 🔥 正确写法：用 mutableStateList + 不可变 data class
    val resultData = remember {
        mutableStateListOf(
            ResultItem("HK(D1)", "1:1000", "2024/3/4 10:12:00"),
            ResultItem("HK(D2)", "1:1000", "2024/3/4 10:12:00"),
            ResultItem("HK(D3)", "1:1000", "2024/3/4 10:12:00"),
        )
    }

    Box(Modifier.fillMaxSize()) {
        val currentNow = LocalContext.current

        if (isResult.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(text = "Currently User Offline Data")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    resultData.forEachIndexed { index, r ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp)
                                .background(Color.White, shape = RoundedCornerShape(3.dp))
                                .padding(3.dp, 0.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RadioButton(
                                    selected = r.selected,
                                    onClick = {
                                        // 🔥 正确刷新方式：替换对象
                                        val newItem = r.copy(selected = !r.selected)
                                        resultData[index] = newItem
                                    }
                                )
                                Text(
                                    text = r.regionName ?: "--",
                                    fontSize = 10.sp,
                                    modifier = Modifier.background(Color(0xFFb3d0f0))
                                )
                                Text(
                                    text = r.scale ?: "--",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(start = 3.dp)
                                )
                            }
                            Text(text = r.DateTime ?: "--", fontSize = 10.sp)
                        }
                    }
                }
            }
        } else {
//            LaunchedEffect(activeParent.value) {
//                Log.d("change", "${activeParent.value}")
//                activeChild.value = ""
//            }
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(text = "Select the Download Region")
                }
                regionData.forEachIndexed { index, group ->
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .background(
                                    Color(if (activeParent.value == group.groupId) 0xFF3f8fe3 else 0xFFf4f6f9),
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .padding(10.dp)
                                .clickable {
                                    activeParent.value = group.groupId
                                }
                        ) {
                            Text(
                                text = "${group.groupName}(${group.children.size})",
                                color = if (activeParent.value == group.groupId) Color.White else Color.Gray
                            )
                        }

                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            group.children.forEach { c ->
                                Row(
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .background(
                                            Color(
                                                if (activeParent.value == "") {
                                                    0xFFf4f5f6
                                                }else{
                                                    if (activeChild.value == "${c.parentGroup}_${c.id}") {
                                                        0xFF3172b6
                                                    } else {
                                                        0xFFc3c5c7
                                                    }
                                                }

                                            ),
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .padding(10.dp)
                                        .clickable {
                                            if (activeParent.value == "") {
                                                Toasty.info(currentNow, "please choose region")
                                                    .show()
                                            } else {
                                                if (c.parentGroup != activeParent.value){
                                                    activeParent.value = c.parentGroup
                                                }
                                                activeChild.value = "${c.parentGroup}_${c.id}"

                                            }
                                        }
                                ) {
//                                    RadioButton(selected = c.selected, onClick = {})
                                    Text(
                                        text = "(${c.label})",
                                        color = if (activeChild.value == "${c.parentGroup}_${c.id}") Color.White else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            TextButton(onClick = {
                isResult.value = false
            }) {
                Text(text = stringResourceByName(if (isResult.value) "btn_back" else "btn_cancel"))
            }
            Row {
                if (isResult.value) {
                    TextButton(onClick = {}) {
                        Text(text = "Delete")
                    }
                }
                Button(onClick = {
                    isResult.value = true
                }) {
                    Text(text = if (isResult.value) "Re-load" else stringResourceByName("btn_ok"))
                }
            }
        }
    }
}

@Preview
@Composable
fun OnPreview1() {
    OfflineDataManagement()
}