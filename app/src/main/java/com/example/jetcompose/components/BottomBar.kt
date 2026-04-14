package com.example.jetcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetcompose.R

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(imageVector = Icons.Default.Place, contentDescription = "system_name")
            Text(text = stringResource(R.string.app_name), modifier = Modifier.padding(5.dp, 0.dp))
            Text(text = "UAT",
                modifier = Modifier
                    .background(Color.Yellow, shape = RoundedCornerShape(4.dp))
                    .padding(2.dp)
            )
            Text(text = "v1.0.0", color = Color.Gray,modifier= Modifier.padding(3.dp,0.dp))
        }
        Row() {
            Text(text = stringResource(R.string.app_name))
            Icon(imageVector = Icons.Default.Place, contentDescription = "system_name")
        }
    }
}