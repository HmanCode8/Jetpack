package com.example.jetcompose


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.viewinterop.AndroidView
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import com.example.jetcompose.components.BottomBar
import com.example.jetcompose.components.LayerManagement
import com.example.jetcompose.components.MenuView
import com.example.jetcompose.components.Panel
import com.example.jetcompose.components.RightTool
import com.example.jetcompose.components.UserSetting
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.MapToolsUntil
import com.example.jetcompose.untils.TianDiTuLayer
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty
import es.dmoral.toasty.Toasty.LENGTH_SHORT
import kotlin.math.roundToInt


class BaseMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleUtils.setLanguage(this, LocaleUtils.currentLang)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseMapView()
        }
    }
}

private const val API_KEY =
    "AAPTaeyxRC4r65S02fl0iMoOJ3g..9Nt98Scjqtsy1BXmwIA8UMQUAsg6hmEt0AaFJgNkKF9bDb1100ItziAxR1gd_NyUt_fCB5nvQ9kJ-A56Wf5dltxunojZzcQRtZrz9KUwt2ROBOuGGfjQ3A5P48FYm1QyUOYMQWt7wbpg4p7G_gg89IRJkmg9l1DimLqHc2AfygzALYxkBWCgyRWW1zYJVTdj6E660C1cca8dtEsB0l3xNh6YWuIibxEBM-_hQzxgdfsorkPQ0ezjCt0pAT1_d97cjBaP"

fun initMapConfig() {
    ArcGISRuntimeEnvironment.setApiKey(API_KEY)
    // 可选：关闭开发水印
    // https://developers.arcgis.com/kotlin/license-and-deployment/get-a-license/ → Dashboard → Licenses

    val licenseResult = ArcGISRuntimeEnvironment.setLicense(
        "runtimelite,1000,rud9878094479,none,9TJC7XLS1MM0J9HSX236"  // 替换成你的 License Key
    )
    Log.d("License", "授权结果: ${licenseResult.licenseStatus}")
}

@Composable
fun BaseMapView() {
    val currentContent = LocalContext.current
    val activeMenu = remember { mutableStateOf<MChildren<*>?>(null) }
    val contactIshShow = remember { mutableStateOf(false) }
    val layerListIsShow =remember { mutableStateOf(false) }
    val mySetting = remember { mutableStateOf(false) }
    initMapConfig()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // ✅ Compose 中正确使用 MapView
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    // 你的原有逻辑 ↓↓↓ 完全不动
                    val vecLayer = TianDiTuLayer.createImgLayer()
                    val cvaLayer = TianDiTuLayer.createCvaLayer()
//                    val vecLayer = TianDiTuLayer.createHKBaseMapLayer()
                    val basemap = Basemap(vecLayer)
                    basemap.referenceLayers.add(cvaLayer)
                    val map = ArcGISMap(basemap)
                    this.map = map
                    this.setViewpoint(Viewpoint(22.3193, 114.1694, 100000.0))
                    this.isAttributionTextVisible = false

                    // ==============================================
                    // 🔥🔥🔥 就在这里绑定！初始化地图时自动绑定
                    // ==============================================
                    MapToolsUntil.bindMapView(this)
                }
            },
            // 页面销毁时自动清空
            onRelease = {
                MapToolsUntil.clearMapView()
            }, modifier = Modifier.fillMaxSize()
        )
        //左上角菜单栏
        MenuView { menuItem, type ->
            if (type == "menu") {
                val currentMenu = menuItem as? MChildren<*>
                activeMenu.value = currentMenu
            } else {
                mySetting.value = !mySetting.value
            }

            Toasty.info(currentContent, "菜单：${menuItem}，类型：$type", LENGTH_SHORT).show()
        }
        //左边菜单面板
        // 先缓存状态到局部变量，避免并发修改
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .freeDrag()
        ) {
            activeMenu.value?.let { currentMenu ->
                Panel(
                    menuValue = currentMenu, // 直接传缓存的局部变量
                    {
                        activeMenu.value = null
                    },
                )
            }
        }

        BottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .fillMaxWidth()
        )
        RightTool(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            {k->
                if (k == "tuceng"){
                layerListIsShow.value = !layerListIsShow.value
            }
            }
        )
        if (layerListIsShow.value)LayerManagement(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 50.dp)
                .freeDrag(),
            {
                layerListIsShow.value = false
            }
        )
        if (contactIshShow.value) Contact(Modifier.align(Alignment.Center), {
            contactIshShow.value = false
        })
        if (mySetting.value) UserSetting { data ->
            Log.d("oooo", "${data}")
            val currentMenu = data as? MChildren<*>
            if (currentMenu?.isMenu ?: false) {
                activeMenu.value = currentMenu
                mySetting.value = false
            } else {
                contactIshShow.value = ! contactIshShow.value
            }

        }
    }
}

@Composable
fun Contact(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Column(
        modifier
            .background(Color.White, shape = RoundedCornerShape(3.dp))
            .padding(5.dp)
            .width(200.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResourceByName("menu_contact"))
            Icon(
                Icons.Default.Close,
                contentDescription = "close",
                Modifier
                    .size(15.dp)
                    .background(Color.LightGray, CircleShape)
                    .padding(2.dp)
                    .clickable {
                        onClose()
                    }
            )
        }
        Row(Modifier.padding(0.dp,10.dp)) {
            Text(
                text = "if you have any question please call 234442 or 134ee@.com", fontSize = 12.sp
            )
        }
    }
}

// 自由拖动 Modifier
@Composable
fun Modifier.freeDrag(): Modifier = this.then(
    remember {
        val offsetX = mutableStateOf(0f)
        val offsetY = mutableStateOf(0f)

        Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            }
    })


@Preview
@Composable
fun Preview() {
//    BaseMapView()
    Contact(Modifier,{})
}