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
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.Basemap
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.toolkit.geoviewcompose.MapView
import com.arcgismaps.toolkit.geoviewcompose.MapViewProxy
import com.example.jetcompose.components.BottomBar
import com.example.jetcompose.components.LayerManagement
import com.example.jetcompose.components.MenuView
import com.example.jetcompose.components.Panel
import com.example.jetcompose.components.RightTool
import com.example.jetcompose.components.UserSetting
import com.example.jetcompose.untils.AppGlobalState
import com.example.jetcompose.untils.LocaleUtils
import com.example.jetcompose.untils.MChildren
import com.example.jetcompose.untils.MapToolsUntil
import com.example.jetcompose.untils.TianDiTuLayer
import com.example.jetcompose.untils.stringResourceByName
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class BaseMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleUtils.setLanguage(this, LocaleUtils.currentLang)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initMapConfig()
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
        Log.d("License", "授权结果: $res")
    }
}

@Composable
fun BaseMapView() {
    val nowCurrent = LocalContext.current
    val activeMenu = remember { mutableStateOf<MChildren<*>?>(null) }
    val contactIsShow = remember { mutableStateOf(false) }
    val layerListIsShow = remember { mutableStateOf(false) }
    val mySetting = remember { mutableStateOf(false) }
    val mapType = remember { mutableStateOf("vec") }

    // ========================
    // 🔥 正确创建地图（必须初始化 Basemap）
    // ========================
    val arcGISMap = remember {
        ArcGISMap(Basemap()).apply {
            initialViewpoint = Viewpoint(
                Point(114.1694, 22.3193, SpatialReference.wgs84()),
                80000.0
            )
        }
    }

    val mapViewProxy = remember { MapViewProxy() }
    // 🔥 正确切换底图
    // ========================
    LaunchedEffect(mapType.value) {
        val basemap = arcGISMap.basemap
        basemap.value?.baseLayers?.clear()
//        basemap.value?.referenceLayers?.clear()
        MapToolsUntil.bind(mapViewProxy)
        val baseLayer = when (mapType.value) {
            "vec" -> TianDiTuLayer.getHongKongVecLayer()
            "img" -> TianDiTuLayer.getHongKongImageryLayer()
            else -> TianDiTuLayer.getHongKongVecLayer()
        }

        basemap.value?.baseLayers?.add(baseLayer)
    }

    // ========================
    // 🔥 正确切换语言注记
    // ========================
    LaunchedEffect(AppGlobalState.currentLang.value) {
        val lang = AppGlobalState.currentLang.value
        val basemap = arcGISMap.basemap
        basemap.value?.referenceLayers?.clear()
        basemap.value?.referenceLayers?.add(TianDiTuLayer.getHongKongLabelLayer(lang))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MapView(
            arcGISMap = arcGISMap,
            modifier = Modifier.fillMaxSize(),
            mapViewProxy = mapViewProxy,
            isAttributionBarVisible = false,
            onTwoPointerTap = { p ->
                Log.d("onTwoPointerTap", "$p")
            },

            onSingleTapConfirmed = { singleTapConfirmedEvent ->
                val x = singleTapConfirmedEvent.screenCoordinate.x
                val y = singleTapConfirmedEvent.screenCoordinate.y
                val p = Point(x, y, SpatialReference.wgs84())
                Log.i("onSingleTapConfirmed", "Single tap at ${p.x}-${p.y}")
            },
            onMapScaleChanged = { scale ->
//                currentMapScale.value = scale // 更新状态
                MapToolsUntil.currentScale = scale
                AppGlobalState.currentScale.value = scale.toInt()
            },
            onSpatialReferenceChanged = { s ->
                Log.d("毁掉1", "$s")
            },
            onUnitsPerDipChanged = { p ->
                Log.d("毁掉2", "${p.dp}")
            },
            onViewpointChangedForCenterAndScale = { v ->
                Log.d("毁掉3", "${v.targetScale}")
            },

            )
        // -------------------- UI 不变 --------------------
        MenuView { menuItem, type ->
            if (type == "menu") {
                activeMenu.value = menuItem as? MChildren<*>
            } else {
                mySetting.value = !mySetting.value
            }
        }

        // 先缓存状态到局部变量，避免并发修改
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
                when (k) {
                    "tuceng" -> layerListIsShow.value = !layerListIsShow.value
                    "fangda" -> MapToolsUntil.zoomIn()
                    "suoxiao" -> MapToolsUntil.zoomOut()
                }
            }
        )

        if (layerListIsShow.value) {
            LayerManagement(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 50.dp)
                    .freeDrag(),
                { t -> mapType.value = t },
                { layerListIsShow.value = false }
            )
        }

        if (contactIsShow.value) {
            Contact(modifier = Modifier.align(Alignment.Center)) { contactIsShow.value = false }
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