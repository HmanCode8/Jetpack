package com.example.jetcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jetcompose.R
import com.example.jetcompose.untils.AppGlobalState
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Mode(
    val label: String,
    val id: Int
)

data class Languages(
    val name: String,
    val key: String
)

@Composable
fun UserSetting(
    onSettingChange: (Any?) -> Unit
) {
    val nowContent = LocalContext.current
    val languages = remember {
        mutableStateOf(
            listOf(
                Languages("en", "en"),
                Languages("简", "zh-CN"),
                Languages("繁", "zh-TW"),
            )
        )
    }
    val languageActive = remember { mutableStateOf(LocaleUtils.currentLang) }
    val modeList = listOf(
        Mode("mode_online", 1),
        Mode("mode_offline", 2)
    )
    val menus = remember {
        mutableStateOf(
            listOf(
                MChildren("menu_dataLog", 1, true),
                MChildren("menu_userLayer", 2, true),
                MChildren("menu_offlineData", 3, true),
                MChildren("menu_contact", 4, false)
            )
        )
    }

    val showDialog = remember { mutableStateOf(false) }
    val fontSizeActive = remember { mutableIntStateOf(1) }
    Box(
        modifier = Modifier
            .width(250.dp)
            .padding(top = 80.dp, start = 20.dp)
            .background(
                brush = Brush.verticalGradient(
                    0.01f to Color(0xFFe2f3fd), // 主色
                    0.5f to Color(0xFFf9fcff), // 主色
                    0.2f to Color(0xFFFFFFFF), // 主色
                ), shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp, 5.dp)
                .verticalScroll(rememberScrollState()),

            ) {
            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                Text("Hi Admin", color = Color(0xFF616161), fontSize = 10.sp)
            }
            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                Text("My Setting", color = Color(0xFF616161), fontSize = 10.sp)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row() {
                    languages.value.forEachIndexed { index, v ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(if (index == 1) 2.dp else 0.dp, 0.dp)
                                .width(25.dp)
                                .height(25.dp)
                                .background(
                                    color = Color(
                                        if (languageActive.value == v.key) 0xFF0091ea else 0xFFa9aaaa
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable() {
                                    LocaleUtils.setLanguage(nowContent, v.key)
                                    languageActive.value = v.key
                                }
                        ) {
                            Text(
                                text = v.name,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                color = if (languageActive.value == v.key) Color.White else Color.Black
                            )
                        }

                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
                    listOf("A", "A", "A").forEachIndexed { index, f ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(2.dp, 0.dp)
                                .width(25.dp)
                                .height(25.dp)
                                .background(
                                    color = Color(
                                        if (fontSizeActive.value == index + 1) 0xFF0091ea else 0xFFa9aaaa
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable {
                                    fontSizeActive.value = index + 1
                                }
                        ) {
                            Text(
                                text = f,
                                textAlign = TextAlign.Center,
                                color = if (fontSizeActive.value == index + 1) Color.White else Color.Black,
                                fontSize = 12.sp,

                                )
                        }

                    }
                }
            }
            Row() {
                Text(
                    text = stringResource(R.string.mode_title),
                    color = Color(0xFF616161),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(0.dp, 5.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                modeList.forEachIndexed { index, mode ->
                    if (index != 0) Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResourceByName(mode.label),
                        color = if ( AppGlobalState.currentMode.value == mode.label) Color.White else Color.Black,
                        modifier = Modifier
                            .background(
                                Color(if ( AppGlobalState.currentMode.value == mode.label) 0xFF0091ea else 0xFFF4F6F9),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(5.dp)
                            .clickable {
                                if (mode.label == "mode_offline"){
                                    showDialog.value = true
                                }else{
                                    AppGlobalState.currentMode.value = mode.label
                                }
                            }
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            menus.value.forEach { m ->
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF4F6F9), shape = RoundedCornerShape(3.dp))
                        .padding(5.dp, 3.dp)
                        .clickable {
                            onSettingChange(m)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResourceByName(m.label),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(0.dp, 5.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.menu_in),
                        contentDescription = "in",
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.menu_logout),
                textAlign = TextAlign.Center,
                color = Color(0xFFDE5E5E),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFDE5E5E), shape = RoundedCornerShape(3.dp))
                    .padding(0.dp, 5.dp)


            )
            if (showDialog.value) Modal(
                {
                    showDialog.value = false
                },
                { data ->
                    showDialog.value = false
                    Toasty.success(nowContent, "offline success").show()
                    AppGlobalState.currentMode.value = "mode_offline"
                    println("data--" + data)
                }
            )
        }
    }
}

data class RegionItem(
    val label: String,          // 显示文本：如 D1)
    val id: String,             // 唯一ID：如 D1)
    val isSelected: Boolean = false, // 是否选中
    val parentGroup: String      // 所属组（如 HKI），方便关联
)

data class RegionGroup(
    val groupName: String,       // 显示名称：如 HKI、NTE、NTW、K
    val groupId: String,         // 组ID：如 "HKI"
    val count: Int,             // 数量：如 4、5
    val isExpanded: Boolean = true, // 是否展开（可选）
    val children: List<RegionItem> // 子项列表
)

@Composable
fun Modal(
    onClose: () -> Unit,
    success: (Any?) -> Unit,
) {

    val regionData = remember {
        mutableStateOf(
            listOf(
                // 第一组：HKI
                RegionGroup(
                    groupName = "HKI",
                    groupId = "HKI",
                    count = 4,
                    children = listOf(
                        RegionItem("D1", "D1", parentGroup = "HKI"),
                        RegionItem("D2", "D2", parentGroup = "HKI"),
                        RegionItem("D3", "D3", parentGroup = "HKI"),
                        RegionItem("D4", "D4", parentGroup = "HKI"),
                        RegionItem("D5", "D5", parentGroup = "HKI")
                    )
                ),

                // 第二组：NTE
                RegionGroup(
                    groupName = "NTE",
                    groupId = "NTE",
                    count = 4,
                    children = listOf(
                        RegionItem("D1", "D1", parentGroup = "NTE"),
                        RegionItem("D2", "D2", parentGroup = "NTE"),
                        RegionItem("D3", "D3", parentGroup = "NTE"),
                        RegionItem("D4", "D4", parentGroup = "NTE")
                    )
                ),

                // 第三组：NTW
                RegionGroup(
                    groupName = "NTW",
                    groupId = "NTW",
                    count = 5,
                    children = listOf(
                        RegionItem("D1", "D1", parentGroup = "NTW"),
                        RegionItem("D2", "D2", parentGroup = "NTW"),
                        RegionItem("D3", "D3", parentGroup = "NTW"),
                        RegionItem("D4", "D4", parentGroup = "NTW"),
                        RegionItem("D5", "D5", parentGroup = "NTW")
                    )
                ),

                // 第四组：K
                RegionGroup(
                    groupName = "K",
                    groupId = "K",
                    count = 4,
                    children = listOf(
                        RegionItem("D1", "D1", parentGroup = "K"),
                        RegionItem("D2", "D2", parentGroup = "K"),
                        RegionItem("D3", "D3", parentGroup = "K"),
                        RegionItem("D4", "D4", parentGroup = "K")
                    )
                )
            )
        )
    }
    Dialog(onDismissRequest = { onClose() }) {
        Column(
            modifier = Modifier
                .height(220.dp)
                .width(400.dp)
                .background(
                    Color.White, shape = RoundedCornerShape(10.dp)
                )
        ) {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = "Switch To Offline Mode",
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Available offline map regions",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )

                Column(modifier = Modifier.padding(5.dp)) {
                    regionData.value.forEachIndexed { index, group ->
                        Spacer(modifier = Modifier.padding(top = 5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0F3F3), shape = RoundedCornerShape(3.dp))
                                .padding(5.dp, 3.dp)

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.resource),
                                contentDescription = "wen",
                                tint = Color(0xFF4e9be0),
                                modifier = Modifier.size(20.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(5.dp, 0.dp)
                                ) {
                                    Text(text = group.groupName, fontSize = 12.sp)
                                    Text(text = "(")
                                    group.children.forEachIndexed { index, c ->
                                        if (index != 0) Text(text = ",")
                                        Text(
                                            text = c.label,
                                            fontSize = 10.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    Text(text = ")")
                                }
                                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val time = sdf.format(Date())
                                Text(text = time, fontSize = 10.sp, color = Color(0xFF989696))
                            }
                        }
                    }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onClose()
                }) {
                    Text(text = stringResourceByName("btn_cancel"))
                }
                TextButton(onClick = {
                    success(false)
                }) {
                    Text(text = stringResourceByName("btn_ok"))
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewUIser() {
//    UserSetting({})
    val showDialog = remember { mutableStateOf(true) }
    Modal(
        {
            showDialog.value = false

        },
        { data ->
            showDialog.value = false
            println("data--" + data)
        }
    )
}