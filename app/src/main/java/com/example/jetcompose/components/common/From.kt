package com.example.jetcompose.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ✅ 正确的下拉框
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YutuDropdown(
    modifier: Modifier = Modifier,
    onChange: (key: String) -> Unit
) {
    val options = listOf("选项 1", "选项 2", "选项 3")
    var selected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("content", fontSize = 10.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.fillMaxWidth()
        )

        //  invisible layer 点击 → 百分百触发！
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        onChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YutuDatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit = {}
) {
    val selectedDateText = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDateText.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("选择日期", fontSize = 10.sp) },
            modifier = Modifier.fillMaxWidth()
        )

        // 透明点击层，百分百触发
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    // 直接调用系统日期选择器，完全兼容所有版本
                    val calendar = java.util.Calendar.getInstance()
                    android.app.DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val date = "$year-${month+1}-$day"
                            selectedDateText.value = date
                            onDateSelected(date)
                        },
                        calendar.get(java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH),
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)
                    ).show()
                }
        )
    }
}
