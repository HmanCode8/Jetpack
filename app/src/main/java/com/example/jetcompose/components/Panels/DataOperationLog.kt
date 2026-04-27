package com.example.jetcompose.components.Panels

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.R
import com.example.jetcompose.components.common.YutuDatePicker
import com.example.jetcompose.components.common.YutuDropdown
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty

@Composable
fun DataOperationLog() {
    val context = LocalContext.current
    val content = remember { mutableStateOf("") }
    val contentID = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val searchKey = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // 搜索区域
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Search Options",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    YutuDropdown(
                        modifier = Modifier.weight(1f),
                        onChange = { content.value = it } // 必须和定义的参数名一致
                    )
                    OutlinedTextField(
                        value = contentID.value,
                        onValueChange = { contentID.value = it },
                        label = { Text("ID", fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(fontSize = 12.sp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 修复后的YutuDatePicker调用
                    YutuDatePicker(
                        modifier = Modifier.weight(1f),
                        onDateSelected = { startDate.value = it }
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    YutuDatePicker(
                        modifier = Modifier.weight(1f),
                        onDateSelected = { endDate.value = it }
                    )
                }

                OutlinedTextField(
                    value = searchKey.value,
                    onValueChange = { searchKey.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search Keyword", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, null, Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { searchKey.value = "" }) {
                            Icon(
                                painter = painterResource(R.drawable.tool_qingchu),
                                null,
                                Modifier.size(18.dp)
                            )
                        }
                    },
                    textStyle = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 表格（修复：限制最大高度，避免UI重叠）
            YutuTable(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // 关键：占满剩余空间，不会顶飞底部按钮
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // 底部按钮（固定在底部，不会被遮挡）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {}) {
                Text(stringResourceByName("btn_cancel"))
            }
            Button(onClick = {
                val params = mapOf(
                    "content" to content.value,
                    "id" to contentID.value, // 修复：这里必须加 .value
                    "startDate" to startDate.value,
                    "endDate" to endDate.value,
                    "key" to searchKey.value
                )
                Toasty.success(context, "Params: $params").show()
            }) {
                Text(stringResourceByName("btn_ok"))
            }
        }
    }
}

// ==================== 表格实体 ====================
data class TableHeader(
    val label: String,
    val key: String
)

data class LogData(
    val date: String,
    val time: String,
    val userId: String,
    val ip: String,
    val content: String
)

fun Any.getFieldValue(fieldName: String): String {
    return try {
        val field = this::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        field.get(this)?.toString() ?: ""
    } catch (e: Exception) {
        ""
    }
}

// ==================== 美化版表格 ====================
@Composable
fun YutuTable(modifier: Modifier = Modifier) {
    val headers = remember {
        listOf(
            TableHeader("Date", "date"),
            TableHeader("Time", "time"),
            TableHeader("User ID", "userId"), // 修复拼写
            TableHeader("IP", "ip"),
            TableHeader("Content", "content")
        )
    }

    val logs = remember {
        listOf(
            LogData("2025-09-08", "18:00:00", "Jane", "10.11.223.95", "Delete"),
            LogData("2025-09-07", "11:00:00", "Emily", "12.00.206.34", "Upload"),
            LogData("2025-09-06", "11:00:00", "Emily", "12.00.206.34", "Delete"),
            LogData("2025-08-17", "14:30:00", "Tom", "12.00.206.44", "Search"),
            LogData("2025-08-15", "14:30:00", "Tom", "12.00.206.44", "Search")
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .verticalScroll(rememberScrollState())
    ) {
        // 表头
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            headers.forEach {
                Text(
                    text = it.label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // 表格内容
        logs.forEach { log ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                headers.forEach {
                    Text(
                        text = log.getFieldValue(it.key),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}