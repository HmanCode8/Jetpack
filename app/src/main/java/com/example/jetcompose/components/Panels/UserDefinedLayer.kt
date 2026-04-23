package com.example.jetcompose.components.Panels

import android.app.Activity
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcompose.components.LayerTreeView
import com.example.jetcompose.components.YutuInput
import com.example.jetcompose.components.common.Tree
import com.example.jetcompose.services.Http
import com.example.jetcompose.services.MockData
import com.example.jetcompose.untils.LayerItem
import com.example.jetcompose.untils.LayerResponse
import es.dmoral.toasty.Toasty

@Composable
fun UserDefinedLayer() {
    val currentContent = LocalContext.current
    val layerList = remember {
        mutableStateOf(MockData.layerData)
    }
    val params = emptyMap<String, Any>()
    val searchKey = remember { mutableStateOf("") }
    Http.post(url = "/api/map/catalogue", payLoad = params, callback = { isOk, data ->
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
//    /map/catalogue-detail
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(3.dp)) {
        Row() {
            YutuInput("",{})
        }
        Row() {
            Tree(
                layerList = layerList.value,
                onSelectedChange = { selectedNodes ->
                    // 🔥 这里就是你要的：所有选中的节点（父+子+孙）
                    // 像 Element UI 一样！
                    Log.d("最终选中", selectedNodes.toString())
                }
            )
        }
    }

}

@Preview
@Composable
fun OnPreview() {
    UserDefinedLayer()
}