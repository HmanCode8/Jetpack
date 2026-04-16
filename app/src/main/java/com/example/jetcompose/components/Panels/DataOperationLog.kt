package com.example.jetcompose.components.Panels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.stringResourceByName

@Composable
fun DataOperationLog() {

    Column(modifier = Modifier.fillMaxWidth().padding(5.dp,0.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {
            Text(text = "Search")
        }
        Row() {
            MyDropdown(modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "开始时间", fontSize = 10.sp) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(2.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "4") },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = {}) {
                Text(text = stringResourceByName("menu_dataLog"))
            }
            Button(onClick = {}) {
                Text(text = stringResourceByName("btn_ok"))
            }
        }
    }
}

// ✅ 正确的下拉框
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdown(modifier: Modifier = Modifier) {
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
            label = { Text("content") },
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
                        expanded = false
                    }
                )
            }
        }
    }
}