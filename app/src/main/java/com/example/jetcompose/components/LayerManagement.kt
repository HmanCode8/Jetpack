package com.example.jetcompose.components

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.R
import com.example.jetcompose.services.Http
import com.example.jetcompose.services.MockData
import com.example.jetcompose.untils.BaseResponse
import com.example.jetcompose.untils.LayerItem
import com.example.jetcompose.untils.LayerResponse
import com.example.jetcompose.untils.LayerWrap
import com.example.jetcompose.untils.getDrawable
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty
import okhttp3.Response

// 最外层响应

@Composable
fun LayerManagement(modifier: Modifier = Modifier, onClose: () -> Unit) {
    val searchKey = remember { mutableStateOf("") }
    val baseMaps = listOf(Icons.Default.Search, Icons.Default.Info, Icons.Default.Home)
    val currentContent = LocalContext.current
    val params = emptyMap<String, Any>()

    // 定义一个空列表，用来装图层数据
    val layerList = remember {
        mutableStateOf<List<LayerItem>>(emptyList())
    }
    Http.post(url = "/api/map/catalogue-by-creator", payLoad = params, callback = { isOk, data ->
        (currentContent as Activity).runOnUiThread {
            if (isOk) {
                // 直接解析成 LayerResponse，而不是 BaseResponse<Any>
                val d = Http.parseJson<LayerResponse>(data)
                Log.d("data", "${d?.data}")

                // 直接赋值，类型完全匹配
                layerList.value = d?.data ?: emptyList()
                Toasty.success(currentContent, "获取成功").show()
            } else {
                layerList.value = MockData.layerData
                Toasty.warning(currentContent, "请求失败").show()
            }
        }
    })
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .width(250.dp)
                .height(400.dp)
                .background(
                    brush = Brush.verticalGradient(
                        0.5f to Color(0xFFf2f8fd),
                        0.5f to Color(0xFFf2f8fd)
                    ),
                    shape = RoundedCornerShape(3.dp)
                )
                .padding(5.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResourceByName("basemap_title"), fontSize = 10.sp)
                Icon(
                    Icons.Default.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onClose()
                        }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                val ac = remember { mutableStateOf(0) }
                baseMaps.forEachIndexed { index, b ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                    Row(
                        modifier = Modifier
                            .size(35.dp).clickable{
ac.value = index
                            }
                    ) {
                        Image(
                            painter = painterResource(getDrawable("type${index + 1}")),
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .border(if (ac.value == index) 2.dp else 1.dp, if (ac.value == index) Color(0xFF3f8fe3) else Color.LightGray),
                            contentDescription = "map${index + 1}"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResourceByName("basemap_layer_list"), fontSize = 10.sp)
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "fresh",
                    modifier = Modifier.size(15.dp)
                )
            }
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
                val newList = layerList.value.map {
                    LayerWrap(it, isChecked = false, isExpanded = false)
                }
                LayerTreeView(newList)

            }
        }
    }
}


// 树形列表（根节点）
@Composable
fun LayerTreeView(layerList: List<LayerWrap>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        layerList.forEach { item ->
            TreeItemNode(item)
        }
    }
}

// 树形节点（支持递归）
@Composable
fun TreeItemNode(
    layerWrap: LayerWrap,
    depth: Int = 0 // 缩进层级
) {
    // 控制展开/收起
    val expanded = remember { mutableStateOf(false) }
    val item = layerWrap.item
    val hasChildren = !item.children.isNullOrEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (hasChildren) expanded.value = !expanded.value
            }
            .padding(
                start = (depth * 8).dp, // 缩进
                top = 8.dp,
                bottom = 8.dp,
                end = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 展开/收起箭头
        if (hasChildren) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Color(0xFFf4f6f9))
                    .padding(0.dp, 3.dp)
            ) {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.resource),
                    contentDescription = "resource",
                    tint = Color(0xFFffcc66),
                    modifier = Modifier.size(20.dp)
                )

            }
        } else {
            Spacer(modifier = Modifier.size(16.dp))
        }

        Spacer(modifier = Modifier.width(6.dp))

        // 图层名称
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFf4f6f9))
                .padding(0.dp, 3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (!hasChildren) {
                    Icon(
                        painter = painterResource(R.drawable.tool_tuceng),
                        contentDescription = "layer",
                        tint = Color(0xFF0692ea),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = item.name ?: "未知",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
            Checkbox(checked = layerWrap.isChecked, onCheckedChange = {
                Log.d("chek", "${item}")
            }, modifier = Modifier.size(16.dp))
        }
    }

    // 递归渲染子节点
    if (expanded.value && hasChildren) {
        item.children!!.forEach { child ->
            TreeItemNode(LayerWrap(child), depth + 1)
        }
    }
}

@Composable
@Preview
fun LayerManagementPre() {
    LayerManagement(modifier = Modifier, {})
}