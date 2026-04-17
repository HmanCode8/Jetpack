package com.example.jetcompose.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
fun YutuDropdown(modifier: Modifier = Modifier, onChange: (key: String) -> Unit) {
    val options = listOf("选项 1", "选项 2", "选项 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOptionText,
            onValueChange = {},
            readOnly = true,
            label = { Text("content", fontSize = 10.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // ✅ 必须加！
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        selectedOptionText = it
                        onChange(it)
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
    onDateSelected: (String) -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) }
    val startDate = remember { mutableStateOf("") }

    val dateState = rememberDatePickerState()
    val now = LocalContext.current
    OutlinedTextField(
        value = startDate.value,
        onValueChange = {},
        readOnly = true,
        label = { Text("选择日期", fontSize = 10.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    Toasty.info(now, "ddad").show()
                    showDialog.value = true
                }
            }

    )

    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                Text(
                    text = "确定",
                    modifier = Modifier.clickable {
                        val selectedDate = dateState.selectedDateMillis
                        selectedDate?.let {
                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            startDate.value = sdf.format(Date(it))
                            onDateSelected(startDate.value)
                        }
                        showDialog.value = false
                    }
                )
            },
            dismissButton = {
                Text(text = "取消", modifier = Modifier.clickable { showDialog.value = false })
            }
        ) {
            DatePicker(state = dateState)
        }
    }
}
