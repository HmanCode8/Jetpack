package com.example.jetcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetcompose.R

@Composable
fun RightTool(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(3.dp, 13.dp)
                .background(Color.White, shape = RoundedCornerShape(2.dp))
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "1",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
            Icon(
                Icons.Default.Home,
                contentDescription = "1",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
            Icon(
                Icons.Default.Settings,
                contentDescription = "1",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
            Icon(
                Icons.Default.Person,
                contentDescription = "1",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
            Icon(
                Icons.Default.Person,
                contentDescription = "1",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
            Icon(
                painter = painterResource(R.drawable.menu_layer),
                contentDescription = "图层",
                modifier = Modifier.padding(3.dp, 5.dp)
            )
        }
        Column() {
            Row(
                modifier = Modifier
                    .padding(6.dp, 5.dp)
                    .background(Color.White, shape = RoundedCornerShape(2.dp))
            ) {
                Icon(
                    Icons.Default.Face,
                    contentDescription = "11",
                    modifier = Modifier.padding(3.dp, 5.dp)
                    )
            }
            Row(
                modifier = Modifier
                    .padding(6.dp, 5.dp)
                    .background(Color.White, shape = RoundedCornerShape(2.dp))
            )
            {
                Icon(
                    Icons.Default.Face,
                    contentDescription = "11",
                    modifier = Modifier.padding(3.dp, 5.dp)
                    )
            }
            Row(
                modifier = Modifier
                    .padding(6.dp, 5.dp)
                    .background(Color.White, shape = RoundedCornerShape(2.dp))
            ) {
                Icon(
                    Icons.Default.Face,
                    contentDescription = "11",
                    modifier = Modifier.padding(3.dp, 5.dp)
                    )
            }

        }

    }
}