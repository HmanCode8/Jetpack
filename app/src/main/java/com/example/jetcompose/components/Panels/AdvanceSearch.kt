package com.example.jetcompose.components.Panels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class SearchItem(
    val name: String,
    val id: String
)

@Composable
fun AdvanceSearch() {

    val searchs = remember {
        mutableStateListOf(
            SearchItem("Surver Sheet No.(SSn) ", "1"),
            SearchItem("Street Name ", "2"),
            SearchItem("Building Name ", "3"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
            SearchItem("Slope Number ", "4"),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = "Search By")
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            searchs.forEach { s ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(Color.White, shape = RoundedCornerShape(3.dp))
                        .border(1.dp,Color.LightGray)
                        .padding(10.dp)
                ) {
                    Text(text = s.name, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
@Preview
fun OnPriew() {
    AdvanceSearch()
}