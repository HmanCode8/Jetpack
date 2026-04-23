package com.example.jetcompose.components

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.R
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.MenuItem
import com.example.jetcompose.untils.getDrawable
import com.example.jetcompose.untils.stringResourceByName


@Composable
fun MenuView(
    onMenuChange: (Any?, String) -> Unit
) {

    val menuList = remember {
        mutableStateOf(
            listOf(
                MenuItem(
                    "查询",
                    1,
                    "menu_query",
                    listOf(
                        MChildren("panel_advanced", "1-1"),
                        MChildren("panel_valve", "1-2"),
                        MChildren("panel_water_mains", "1-3")
                    )
                ),
                MenuItem(
                    "gis", 2, "menu_gis", listOf(
                        MChildren("panel_change_symbology", "2-1"),
                        MChildren("panel_highlight", "2-2")
                    )
                ),
                MenuItem(
                    "Task",
                    3,
                    "menu_task",
                    listOf(
                        MChildren("panel_vicp", "3-1"),
                        MChildren("panel_pipeline_tracing", "3-1"),
                        MChildren("panel_population_analysis", "3-1")
                    )
                ),
            )
        )
    }

    val childMenus = remember {
        mutableStateOf<List<MChildren<*>>>(emptyList())
    }

    val isActive = remember { mutableIntStateOf(0) }
    val isActiveChild = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(20.dp)
            .width(220.dp)
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(25.dp))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            // 1. 圆形头像 + 半透明背景（叠层布局）
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .border(2.dp,Color(0xFF94c3f4), shape = CircleShape)
                    .size(50.dp)
                    .clickable {
                        onMenuChange("个人中心 ", "my")
                    }
            ) {
                // 头像（中间层）
                Image(
                    painter = painterResource(getDrawable("avatar")),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .border(1.dp,Color.Gray)
                        .fillMaxSize()
                )

                // 文字（最上层）
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            brush = Brush.radialGradient(
                                1f to Color(0xFF2a5aa2).copy(alpha = 0.8f),
                                0.2f to Color(0xFF2D4456).copy(alpha = 0.8f)
                            ),
                            shape = CircleShape
                        ).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "JACK He",
                        fontSize = 10.sp,
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "PC/AM,WSD",
                        color = Color(0xFFFFFFFF),
                        fontSize = 6.sp
                    )
                }
            }

            // 2. 菜单条（完全不动）
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .padding(start = 50.dp)
            ) {
                menuList.value.forEachIndexed { index, m ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(45.dp)
                            .width(50.dp)
                            .padding(2.dp)
                            .background(
                                brush = if (isActive.intValue == m.id) {
                                    Brush.verticalGradient(
                                        0.2f to Color(0xFFebf5fe),
                                        0.4f to Color(0xFFebf5fe),
                                        0.4f to Color(0xFFf3f8fc)
                                    )
                                } else {
                                    SolidColor(Color.White)
                                },
                                shape = RoundedCornerShape(size = 2.dp)
                            )
                            .clickable {
                                isActive.intValue = if (isActive.intValue == m.id) 0 else m.id
                                childMenus.value = m.children
                            }
                    ) {
                        Icon(
                            tint = Color(if (isActive.intValue == m.id) 0xFF0092e0 else 0xFF7289a6),
                            painter = painterResource(getDrawable(m.icon)),
                            contentDescription = m.label,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 80.dp, start = 30.dp)
            .background(Color.White, shape = RoundedCornerShape(3.dp))
    ) {
        if (isActive.intValue != 0) Column(
            modifier = Modifier
        ) {
            childMenus.value.forEach { c ->
                Row(
                    modifier = Modifier
                        .widthIn(100.dp, 200.dp)
                        .padding(10.dp)
                        .clickable() {
                            isActiveChild.value = c.label
                            onMenuChange(c, "menu")
//                                isActive.value = 0
                        }
                ) {

                    Text(
                        text = stringResourceByName(c.label),
                        color = Color(if (isActiveChild.value == c.label) 0xFF0092e0 else 0xFF7289a6),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun MenuViewToPreview() {
    MenuView { d, a -> }
}