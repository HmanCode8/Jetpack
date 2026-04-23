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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.R
import com.example.jetcompose.components.common.Tree
import com.example.jetcompose.services.Http
import com.example.jetcompose.untils.LayerItem
import com.example.jetcompose.untils.LayerResponse
import com.example.jetcompose.untils.getDrawable
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty


@Composable
fun LayerManagement(modifier: Modifier = Modifier, onMapTypeChange:(type: String)-> Unit, onClose: () -> Unit) {
    val searchKey = remember { mutableStateOf("") }
    val searchText = rememberTextFieldState("12")
    val baseMapTypes = remember { mutableStateListOf("vec","img","scene") }
    val currentContent = LocalContext.current
    val params = emptyMap<String, Any>()

    // 可观察列表，UI刷新基础
    val layerList = remember {
        mutableStateListOf(
            LayerItem(
                id = 1,
                parentId = null,
                type = "root_folder",
                name = "Water Networks",
                code = "water_networks",
                serviceUrl = null,
                sort = 1,
                layerType = "water_main",
                shapeType = "folder",
                layerId = 1,
                dataBase = null,
                tableName = null,
                children = listOf(
                    // ========== Fresh Water ==========
                    LayerItem(
                        id = 2,
                        parentId = 1,
                        type = "folder",
                        name = "Fresh Water",
                        code = "fresh_water",
                        serviceUrl = null,
                        sort = 1,
                        layerType = "fresh_water",
                        shapeType = "folder",
                        layerId = 2,
                        dataBase = "sde",
                        tableName = null,
                        children = listOf(
                            LayerItem(
                                id = 201,
                                parentId = 2,
                                type = "layer",
                                name = "Fresh Water Mains",
                                code = "f_main",
                                serviceUrl = null,
                                sort = 1,
                                layerType = "fresh_water",
                                shapeType = "line",
                                layerId = 201,
                                dataBase = "sde",
                                tableName = "f_main",
                                children = null
                            ),
                            LayerItem(
                                id = 202,
                                parentId = 2,
                                type = "layer",
                                name = "Fresh flow control valve",
                                code = "f_flowctlvalve",
                                serviceUrl = null,
                                sort = 2,
                                layerType = "fresh_water",
                                shapeType = "point",
                                layerId = 202,
                                dataBase = "sde",
                                tableName = "f_flowctlvalve",
                                children = null
                            ),
                            LayerItem(
                                id = 203,
                                parentId = 2,
                                type = "layer",
                                name = "Fresh fitting",
                                code = "f_fitting",
                                serviceUrl = null,
                                sort = 3,
                                layerType = "fresh_water",
                                shapeType = "point",
                                layerId = 203,
                                dataBase = "sde",
                                tableName = "f_fitting",
                                children = null
                            ),
                        )
                    ),

                    // ========== Salt Water ==========
                    LayerItem(
                        id = 3,
                        parentId = 1,
                        type = "folder",
                        name = "Salt Water",
                        code = "salt_water",
                        serviceUrl = null,
                        sort = 2,
                        layerType = "salt_water",
                        shapeType = "folder",
                        layerId = 3,
                        dataBase = "sde",
                        tableName = null,
                        children = listOf(
                            LayerItem(
                                id = 301,
                                parentId = 3,
                                type = "layer",
                                name = "Salt water mains",
                                code = "s_main",
                                serviceUrl = null,
                                sort = 1,
                                layerType = "salt_water",
                                shapeType = "line",
                                layerId = 301,
                                dataBase = "sde",
                                tableName = "s_main",
                                children = null
                            )
                        )
                    ),

                    // ========== Raw Water ==========
                    LayerItem(
                        id = 4,
                        parentId = 1,
                        type = "folder",
                        name = "Raw Water",
                        code = "raw_water",
                        serviceUrl = null,
                        sort = 3,
                        layerType = "raw_water",
                        shapeType = "folder",
                        layerId = 4,
                        dataBase = "sde",
                        tableName = null,
                        children = listOf(
                            LayerItem(
                                id = 401,
                                parentId = 4,
                                type = "layer",
                                name = "Raw water mains",
                                code = "r_main",
                                serviceUrl = null,
                                sort = 1,
                                layerType = "raw_water",
                                shapeType = "line",
                                layerId = 401,
                                dataBase = "sde",
                                tableName = "r_main",
                                children = null
                            )
                        )
                    ),

                    // ========== Recycle Water ==========
                    LayerItem(
                        id = 5,
                        parentId = 1,
                        type = "folder",
                        name = "Recycle Water",
                        code = "recycle_water",
                        serviceUrl = null,
                        sort = 4,
                        layerType = "recycle_water",
                        shapeType = "folder",
                        layerId = 5,
                        dataBase = "sde",
                        tableName = null,
                        children = listOf(
                            LayerItem(
                                id = 501,
                                parentId = 5,
                                type = "layer",
                                name = "Recycle water mains",
                                code = "re_main",
                                serviceUrl = null,
                                sort = 1,
                                layerType = "recycle_water",
                                shapeType = "line",
                                layerId = 501,
                                dataBase = "sde",
                                tableName = "re_main",
                                children = null
                            )
                        )
                    )
                )
            ),

            // ====================== 根节点 2：Other Layers ======================
            LayerItem(
                id = 6,
                parentId = null,
                type = "root_folder",
                name = "Other Layers",
                code = "other_layers",
                serviceUrl = null,
                sort = 2,
                layerType = "other_layer",
                shapeType = "folder",
                layerId = 6,
                dataBase = null,
                tableName = null,
                children = listOf(
                    LayerItem(
                        id = 601,
                        parentId = 6,
                        type = "layer",
                        name = "Lot",
                        code = "lot",
                        serviceUrl = null,
                        sort = 1,
                        layerType = "other_layer",
                        shapeType = "polygon",
                        layerId = 601,
                        dataBase = "othldbsde",
                        tableName = null,
                        children = null
                    ),
                    LayerItem(
                        id = 602,
                        parentId = 6,
                        type = "folder",
                        name = "Lamp Post",
                        code = "lamp_post_folder",
                        serviceUrl = null,
                        sort = 2,
                        layerType = "other_layer",
                        shapeType = "folder",
                        layerId = 602,
                        dataBase = null,
                        tableName = null,
                        children = listOf(
                            LayerItem(
                                id = 6021,
                                parentId = 602,
                                type = "layer",
                                name = "Lamp Post",
                                code = "lamp_post",
                                serviceUrl = null,
                                sort = 1,
                                layerType = "other_layer",
                                shapeType = "point",
                                layerId = 6021,
                                dataBase = "othldbsde",
                                tableName = null,
                                children = null
                            )
                        )
                    ),
                    LayerItem(
                        id = 603,
                        parentId = 6,
                        type = "layer",
                        name = "DMA/PMA boundary",
                        code = "dma_pma",
                        serviceUrl = null,
                        layerId = 603,
                        sort = 3,
                        layerType = "other_layer",
                        shapeType = "polygon",
                        dataBase = "othldbsde",
                        tableName = "DMA_PMA",
                        children = null
                    ),
                    LayerItem(
                        id = 604,
                        parentId = 6,
                        type = "layer",
                        name = "WDA boundary",
                        code = "wda",
                        serviceUrl = null,
                        sort = 4,
                        layerType = "other_layer",
                        shapeType = "polygon",
                        layerId = 604,
                        dataBase = "othldbsde",
                        tableName = "WDA",
                        children = null
                    )
                )
            )
        )
    }

    Http.post(url = "/api/map/catalogue-by-creator", payLoad = params, callback = { isOk, data ->
        (currentContent as Activity).runOnUiThread {
            if (isOk) {
                val d = Http.parseJson<LayerResponse>(data)
                Log.d("data", "${d?.data}")
                layerList.clear()
                layerList.addAll(d?.data ?: emptyList())
                Toasty.success(currentContent, "获取成功").show()
            } else {
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
                        0.1f to Color(0xFFf2f8fd),
                        0.5f to Color(0xFFFFFFFF)
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
                        .size(15.dp)
                        .background(Color.LightGray, CircleShape)
                        .padding(3.dp)
                        .clickable {
                            onClose()
                        }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                val activeBaseMap = remember { mutableIntStateOf(0) }
                baseMapTypes.forEachIndexed { index, b ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                    Row(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                activeBaseMap.intValue = index
                                onMapTypeChange(b)
                            }
                    ) {
                        Image(
                            painter = painterResource(getDrawable("type${index + 1}")),
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .border(
                                    if (activeBaseMap.intValue == index) 2.dp else 1.dp,
                                    if (activeBaseMap.intValue == index) Color(0xFF3f8fe3) else Color.LightGray
                                ),
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
                    tint = Color.LightGray,
                    modifier = Modifier.size(15.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            YutuInput(text = "", { d ->
                Log.d("search", d)
            })
            Row() {
                Tree(
                    layerList = layerList,
                    onSelectedChange = { selectedNodes ->
                        // 🔥 这里就是你要的：所有选中的节点（父+子+孙）
                        selectedNodes.forEach {n->
                            Log.d("最终选中", "$n")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun YutuInput(
    text: String,
    onChage: (value: String) -> Unit
) {
    val contentNow = LocalContext.current
    val searchText = rememberTextFieldState("")
    LaunchedEffect(searchText.text) {
        onChage("${searchText.text}")
    }
    Box(
        modifier = Modifier
            .height(30.dp)
            .background(Color(0xFFf4f6f9), shape = RoundedCornerShape(1.dp))
    ) {

        Row(
            Modifier
                .align(Alignment.Center)
                .onFocusEvent() {
                    searchText.edit {
                        if (this.toString() == "请输入") {
                        }
                    }
                }) {

            BasicTextField(
                state = searchText,
                textStyle = TextStyle(fontSize = 10.sp, color = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color(0xFFf4f6f9), shape = RoundedCornerShape(1.dp))
                    .padding(3.dp)
                    .onFocusEvent() {
                        searchText.edit {
                            if (this.toString() == "请输入") {
                            }
                        }
                    },
            )
        }
        Icon(
            Icons.Default.Search,
            contentDescription = "fresh",
            tint = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(15.dp)
        )
    }
}

// 树形列表（根节点）
@Composable
fun LayerTreeView(layerList: List<LayerItem>, onChecked: (newItem: LayerItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        layerList.forEach { item ->
            TreeItemNode(item, onChecked)
        }
    }
}

// 树形节点（支持递归）
@Composable
fun TreeItemNode(
    item: LayerItem,
    onChecked: (newItem: LayerItem) -> Unit,
    depth: Int = 0 // 缩进层级
) {
    // 控制展开/收起
    val expanded = remember { mutableStateOf(false) }
    val hasChildren = !item.children.isNullOrEmpty()

    Row(
        modifier = Modifier
            .clickable {
                if (hasChildren) expanded.value = !expanded.value
            }
            .padding(
                start = (depth * 8).dp,
                top = 8.dp,
            )
            .background(Color(0xFFF4F6F9)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 展开/收起箭头
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (hasChildren)
                    Icon(
                        imageVector = if (expanded.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                Icon(
                    painter = painterResource(getDrawable(if (hasChildren) "resource" else "tool_tuceng")),
                    contentDescription = "resource",
                    tint = Color(if (hasChildren) 0xFFffcc66 else 0xFF0091ea),
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 3.dp)
                )
                Text(
                    text = item.name ?: "未知",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(2.dp, 0.dp)
                )
            }
            Box(
                Modifier
                    .padding(end = 3.dp)
                    .width(12.dp)
                    .height(12.dp)
                    .background(
                        Color(if (item.isChecked) 0xFF0091ea else 0xFFFFFFFF),
                        shape = RoundedCornerShape(1.dp)
                    )
                    .border(1.dp, Color.LightGray)
                    .clickable {
                        onChecked(item)
                    }
            ) {
                if (item.isChecked) Icon(
                    Icons.Default.Check,
                    contentDescription = "layerChoose",
                    tint = Color(0xFFFFFFFF)
                )
            }
        }
    }

    // 递归渲染子节点（完美透传回调，不再丢失）
    if (expanded.value && hasChildren) {
        item.children!!.forEach { child ->
            TreeItemNode(child, onChecked, depth + 1)
        }
    }
}

@Composable
@Preview
fun LayerManagementPre() {
    LayerManagement(modifier = Modifier, {},{})
}