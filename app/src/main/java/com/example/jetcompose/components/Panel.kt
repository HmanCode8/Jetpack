package com.example.jetcompose.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.R
import com.example.jetcompose.components.Panels.AdvanceSearch
import com.example.jetcompose.components.Panels.DataOperationLog
import com.example.jetcompose.components.Panels.OfflineDataManagement
import com.example.jetcompose.components.Panels.UserDefinedLayer
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty


@Composable
fun Panel(
    menuValue: MChildren<*>,
    onClose: () -> Unit,
) {
    val isExpand = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .widthIn(0.dp, 350.dp)
            .height(500.dp)
            .padding(start = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(if (isExpand.value) 12f else 1f)
                    .background(
                        brush = Brush.verticalGradient(
                            0.5f to Color(0xFFe2f1ff),
                            0.3f to Color(0xFFe2f1ff),
                        ), shape = RoundedCornerShape(5.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(5.dp, 0.dp)
                ) {
                    Text(
                        text = stringResourceByName(menuValue.label),
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "关闭",
                        modifier = Modifier
                            .background(Color(0xFFf4f9ff), CircleShape)
                            .size(20.dp)
                            .align(Alignment.CenterEnd)
                            .padding(3.dp)
                            .clickable {
                                onClose()
                            })
                }
                when (stringResourceByName(menuValue.label)) {
                    stringResource(R.string.menu_dataLog) -> DataOperationLog()
                    stringResource(R.string.menu_userLayer) -> UserDefinedLayer()
                    stringResource(R.string.menu_offlineData) -> OfflineDataManagement()
                    stringResource(R.string.panel_advanced) -> AdvanceSearch()
                    else -> Text("未知面板", modifier = Modifier.padding(16.dp), fontSize = 14.sp)
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        isExpand.value = !isExpand.value
                    }) {
                Icon(
                    painter = painterResource(id = R.drawable.expand),
                    contentDescription = "展开/收起",
                    modifier = Modifier
                        .size(50.dp, 70.dp) // 动态调整大小
                        .clickable {
                            isExpand.value = !isExpand.value
                        },
                    tint = Color(0xFF2196F3) // 动态修改背景色
                )
            }
        }
    }
}

@Preview
@Composable
fun PanelPreview() {
    Panel(MChildren("menu_userLayer", "1121"), {})
}