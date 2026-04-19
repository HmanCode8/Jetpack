package com.example.jetcompose.components.Panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.components.LayerTreeView
import com.example.jetcompose.services.MockData
import com.example.jetcompose.untils.LayerWrap

@Composable
fun UserDefinedLayer() {
    val searchKey = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(3.dp)) {
        Row() {
            OutlinedTextField(
                value = searchKey.value,
                onValueChange = { v ->
                    searchKey.value = v
                },
                placeholder = { Text(text = "请输入") },
                label = { Text(text = "搜索", fontSize = 10.sp) },
                modifier = Modifier.weight(1f),

                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "搜索")

                }

            )
        }
        Row() {
            val newList = MockData.layerData.map {
                LayerWrap(it, isChecked = false, isExpanded = false)
            }
            LayerTreeView(newList)
        }
    }

}

@Preview
@Composable
fun OnPreview() {
    UserDefinedLayer()
}