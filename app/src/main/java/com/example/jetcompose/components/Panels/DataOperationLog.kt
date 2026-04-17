package com.example.jetcompose.components.Panels

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.esri.arcgisruntime.internal.jni.fa
import com.example.jetcompose.R
import com.example.jetcompose.components.common.YutuDatePicker
import com.example.jetcompose.components.common.YutuDropdown
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DataOperationLog() {
    val contentNow = LocalContext.current
    val content = remember { mutableStateOf("") }
    val contentID = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val searchKey = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(text = "Search")
            }
            Row() {
                Row() {
                    YutuDropdown(modifier = Modifier.weight(1f), { k ->
                        content.value = k
                    })
                    Spacer(modifier = Modifier.width(2.dp))
                    OutlinedTextField(
                        value = contentID.value,
                        onValueChange = { v ->
                            contentID.value = v
                        },
                        label = { Text(text = "ID", fontSize = 10.sp) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    YutuDatePicker({ time ->
                        startDate.value = time
                    })
                }
                Spacer(modifier = Modifier.width(2.dp))
                Row(modifier = Modifier.weight(1f)) {
                    YutuDatePicker({ time ->
                        endDate.value = time
                    })
                }

            }
            Row() {
                OutlinedTextField(
                    value = searchKey.value,
                    onValueChange = { v ->
                        searchKey.value = v
                    },
                    placeholder = { Text(text = "请输入") },
                    label = { Text(text = "搜索", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "搜索")
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.tool_qingchu),
                            contentDescription = "clear",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    searchKey.value = ""
                                }
                        )
                    }

                )
            }
            Row() {
                YutuTable()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp, 0.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                },
            ) {
                Text(text = stringResourceByName("btn_cancel"))
            }
            Button(onClick = {
                val params = mapOf(
                    "content" to content.value,
                    "id" to contentID,
                    "startDate" to startDate.value,
                    "endDate" to endDate.value,
                    "key" to searchKey.value
                )
                Toasty.success(contentNow, "params+${params}").show()
            }) {
                Text(text = stringResourceByName("btn_ok"))
            }
        }
    }
}

// 表头：显示名 + 字段key
data class TableHeader(
    val label: String,
    val key: String
)

// 日志数据：和截图字段一一对应
data class LogData(
    val date: String,
    val time: String,
    val userId: String,
    val ip: String,
    val content: String
)

// 自动根据 字段名(key) 取对象的值
fun Any.getFieldValue(fieldName: String): String {
    return try {
        val field = this::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        field.get(this)?.toString() ?: ""
    } catch (e: Exception) {
        ""
    }
}

@Composable
fun YutuTable() {
    val tableHear = remember {
        listOf(
            TableHeader("Data", "date"),
            TableHeader("Time", "time"),
            TableHeader("Uaer ID", "userId"), // 截图里的拼写，这里保持一致
            TableHeader("IP", "ip"),
            TableHeader("Content", "content")
        )
    }

    val mockLogs = listOf(
        LogData("2025-9-8", "18:00:00", "Jane", "10.11.223.95", "Delet"),
        LogData("2025-9-7", "11:00:00", "Emily", "12.00.206.34", "Upload"),
        LogData("2025-9-6", "11:00:00", "Emily", "12.00.206.34", "Delet"),
        LogData("2025-8-17", "14:30:00", "Tom", "12.00.206.44", "Search"),
        LogData("2025-8-15", "14:30:00", "Tom", "12.00.206.44", "Search")
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tableHear.forEachIndexed { index, header ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp, 0.dp)
                ) {
                    Text(text = header.label, textAlign = TextAlign.Center)
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            mockLogs.forEach { t ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)) {
                    tableHear.forEach { h ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = t.getFieldValue(h.key), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}