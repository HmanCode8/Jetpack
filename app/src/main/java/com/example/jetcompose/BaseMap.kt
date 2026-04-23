package com.example.jetcompose

import android.graphics.Camera
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.LicenseKey
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.tasks.offlinemaptask.OfflineMapTask
import com.arcgismaps.toolkit.geoviewcompose.MapView
import com.arcgismaps.toolkit.geoviewcompose.SceneView
import com.example.jetcompose.components.BottomBar
import com.example.jetcompose.components.LayerManagement
import com.example.jetcompose.components.MenuView
import com.example.jetcompose.components.Panel
import com.example.jetcompose.components.RightTool
import com.example.jetcompose.components.UserSetting
import com.example.jetcompose.untils.AppGlobalState
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.TianDiTuLayer
import com.example.jetcompose.untils.stringResourceByName
import kotlin.math.roundToInt

class BaseMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleUtils.setLanguage(this, LocaleUtils.currentLang)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initMapConfig()
        Log.d("map11", "ddd")
        setContent {
            BaseMapView()
        }
    }
}

private const val API_KEY =
    "AAPTaeyxRC4r65S02fl0iMoOJ3g..9Nt98Scjqtsy1BXmwIA8UMQUAsg6hmEt0AaFJgNkKF9bDb1100ItziAxR1gd_NyUt_fCB5nvQ9kJ-A56Wf5dltxunojZzcQRtZrz9KUwt2ROBOuGGfjQ3A5P48FYm1QyUOYMQWt7wbpg4p7G_gg89IRJkmg9l1DimLqHc2AfygzALYxkBWCgyRWW1zYJVTdj6E660C1cca8dtEsB0l3xNh6YWuIibxEBM-_hQzxgdfsorkPQ0ezjCt0pAT1_d97cjBaP"

fun initMapConfig() {
    ArcGISEnvironment.apiKey = ApiKey.create(API_KEY)
    val licenseKey = LicenseKey.create("runtimelite,1000,rud9878094479,none,9TJC7XLS1MM0J9HSX236")
    licenseKey?.let {
        val res = ArcGISEnvironment.setLicense(licenseKey)
        Log.d("License", "授权结果: ${res}")
    }
}

@Composable
fun BaseMapView() {
    val currentContent = LocalContext.current
    val activeMenu = remember { mutableStateOf<MChildren<*>?>(null) }
    val contactIsShow = remember { mutableStateOf(false) }
    val layerListIsShow = remember { mutableStateOf(false) }
    val mySetting = remember { mutableStateOf(false) }
    val mapType = remember { mutableStateOf("vec") }
    val scope = rememberCoroutineScope()
    var arcGISMap = remember {
        TianDiTuLayer.createHongKongImageryMap().apply {
            // 重点修复：初始缩放！！！
            // 官方WGS84低层级(0-6)无瓦片！必须初始放大到香港本地可见层级
            initialViewpoint = Viewpoint(
                Point(114.1694, 22.3193, SpatialReference.wgs84()),
                10000.0   // 缩放scale缩小，地图放大，进入服务有数据的高层级区域
            )
        }

    }

    // 🔥 加上 remember，3D 场景只创建一次
    val scene = remember {
        TianDiTuLayer.createScene().apply {
            initialViewpoint = Viewpoint(
                Point(114.1694, 22.3193, SpatialReference.wgs84()),
                80000.0,
//                camera =
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(mapType) {
            arcGISMap = TianDiTuLayer.createTdtImgMap().apply {
                initialViewpoint = Viewpoint(
                    Point(114.1694, 22.3193, SpatialReference.wgs84()),
                    80000.0
                )
            }
        }
        if (mapType.value != "scene") {
            MapView(
                arcGISMap = arcGISMap,
                modifier = Modifier.fillMaxSize(),
                isAttributionBarVisible = false,
                onMapScaleChanged = { scale ->
                    AppGlobalState.currentScale.value = scale.toInt()
                },
                onSingleTapConfirmed = { sing ->
//                    val mapPoint = this.screenToLocation(sing.screenCoordinate)
//                    mapPoint?.let {
//                        Log.d("经纬度", "lon=${it.x}, lat=${it.y}")
//                    }
                }
            )
        } else {
            SceneView(
                arcGISScene = scene,
                modifier = Modifier.fillMaxSize(),
                isAttributionBarVisible = false
            )
        }

        // -------------------- 下面 UI 不变 --------------------
        MenuView { menuItem, type ->
            if (type == "menu") {
                activeMenu.value = menuItem as? MChildren<*>
            } else {
                mySetting.value = !mySetting.value
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .freeDrag()
        ) {
            activeMenu.value?.let {
                Panel(it) { activeMenu.value = null }
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
            { k ->
                if (k == "tuceng") {
                    layerListIsShow.value = !layerListIsShow.value
                }
            }
        )

        if (layerListIsShow.value) {
            LayerManagement(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 50.dp)
                    .freeDrag(),
                { t ->
                    mapType.value = t
                },
                { layerListIsShow.value = false }
            )
        }

        if (contactIsShow.value) {
            Contact { contactIsShow.value = false }
        }

        if (mySetting.value) {
            UserSetting { data ->
                val currentMenu = data as? MChildren<*>
                if (currentMenu?.isMenu == true) {
                    activeMenu.value = currentMenu
                    mySetting.value = false
                } else {
                    contactIsShow.value = true
                }
            }
        }
    }
}

@Composable
fun Contact(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Column(
        modifier
            .background(Color.White, RoundedCornerShape(3.dp))
            .padding(5.dp)
            .width(200.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Text(stringResourceByName("menu_contact"))
            Icon(
                Icons.Default.Close, "",
                Modifier
                    .size(15.dp)
                    .background(Color.LightGray, CircleShape)
                    .clickable { onClose() }
            )
        }
        Text(
            "if you have any question please call 234442 or 134ee@.com",
            fontSize = 12.sp, modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun Modifier.freeDrag(): Modifier = this.then(
    remember {
        val offsetX = mutableStateOf(0f)
        val offsetY = mutableStateOf(0f)
        Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { _, drag ->
                    offsetX.value += drag.x
                    offsetY.value += drag.y
                }
            }
    }
)