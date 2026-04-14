package com.example.jetcompose


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.ui.viewinterop.AndroidView
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import com.example.jetcompose.components.BottomBar
import com.example.jetcompose.components.MenuView
import com.example.jetcompose.components.Panel
import com.example.jetcompose.components.RightTool
import com.example.jetcompose.components.UserSetting
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.TianDiTuLayer
import es.dmoral.toasty.Toasty
import es.dmoral.toasty.Toasty.LENGTH_SHORT


class BaseMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
    android.util.Log.d("License", "授权结果: ${licenseResult.licenseStatus}")
}

@Composable
fun BaseMapView() {
    val currentContent = LocalContext.current
    val activeMenu = remember { mutableStateOf<MChildren<*>?>(null) }
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
                    // 初始化天地图
                    val vecLayer = TianDiTuLayer.createImgLayer()
                    val cvaLayer = TianDiTuLayer.createCvaLayer()
                    val basemap = Basemap(vecLayer)
                    basemap.referenceLayers.add(cvaLayer)
                    val map = ArcGISMap(basemap)
                    Toasty.success(context, "初始化成功", Toast.LENGTH_SHORT).show()
                    this.map = map
                    // 定位到 Hk
                    this.setViewpoint(Viewpoint(22.3193, 114.1694, 100000.0))
                    this.isAttributionTextVisible = false
                }
            },
            modifier = Modifier.fillMaxSize()
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
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
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
                .fillMaxWidth()
        )
        RightTool(
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterEnd)
        )
        if (mySetting.value) UserSetting { data ->
            val currentMenu = data as? MChildren<*>
            activeMenu.value = currentMenu
            mySetting.value = false
        }

    }
}

@Preview
@Composable
fun Preview() {
    BaseMapView()
}