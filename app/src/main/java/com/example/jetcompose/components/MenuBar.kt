    package com.example.jetcompose.components

    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.offset
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.layout.widthIn
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Icon
    import androidx.compose.material3.Text
    import androidx.compose.material3.VerticalDivider
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.jetcompose.R
    import es.dmoral.toasty.Toasty
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Brush
    import com.example.jetcompose.untils.MChildren
    import com.example.jetcompose.untils.MenuItem


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
                        R.drawable.ic_search,
                        listOf(
                            MChildren("Advance Search", "1-1"),
                            MChildren("Valve", "1-2"),
                            MChildren("Mains", "1-3")
                        )
                    ),
                    MenuItem(
                        "gis", 2, R.drawable.ic_menu, listOf(
                            MChildren("Change Symbology", "2-1"),
                            MChildren("Highlight", "2-2")
                        )
                    ),
                    MenuItem("Task", 3, R.drawable.ic_briefcase, listOf(MChildren("VICP", "3-1"))),
                )
            )
        }

        val childMenus = remember { mutableStateOf<List<MChildren<*>>>(emptyList()) }

        val isActive = remember { mutableStateOf(0) }

        Box(
            modifier = Modifier.padding(20.dp)

        ) {
            Column(
                modifier = Modifier.clip(shape = RoundedCornerShape(25.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.background(Color.White)) {
                    // 1. 圆形头像
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart) // 靠左
                            .size(50.dp)
                            .clickable() {
                                onMenuChange("个人中心 ", "my")
                            }
                            .background(brush = Brush.radialGradient(
                                0.5f to Color(0xFFE8F2FA),
                                0.2f to Color(0xFFe6f4ff)
                            ), CircleShape),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "JACK He",
                            fontSize = 8.sp,
                            color = Color(0xFF091224),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "WSD/dd,dd",
                            color = Color(0xFF1E4A94),
                            fontSize = 5.sp
                        )
                    }
                    // 2. 菜单条
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .align(Alignment.CenterStart) // 靠左
                            .padding(start = 50.dp)
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        menuList.value.forEachIndexed { index, m ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                                    .clickable() {
                                        println("index===${index + 1}")
                                        isActive.value = if (isActive.value == m.id) 0 else m.id
                                        childMenus.value = m.children
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(m.icon),
                                    contentDescription = m.label
                                )
                            }
                        }
                    }
    //                VerticalDivider(color = Color.Gray, modifier = Modifier.height(40.dp))
                    val atEnd by remember { mutableStateOf(false) }
                }

            }


        }
        Row(modifier = Modifier.padding(top = 80.dp, start = 30.dp)) {
            if (isActive.value == 0) null else Column(
                modifier = Modifier.background(
                    Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
            ) {
                childMenus.value.forEachIndexed { index, c ->
                    Row(modifier = Modifier.widthIn(100.dp, 200.dp)) {
                        Text(
                            text = c.label,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 10.dp)
                                .clickable() {
                                    onMenuChange(c, "menu")
                                    isActive.value = 0
                                })
                    }
                }
            }
        }

    }

    @Preview
    @Composable
    fun MenuViewToPreview() {
        MenuView{d,a->}
    }