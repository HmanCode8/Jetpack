package com.example.jetcompose.untils

import android.util.Log
import com.arcgismaps.mapping.view.MapView
import java.lang.ref.WeakReference

object MapToolsUntil {

    // 弱引用，不内存泄漏，不报黄
    private var mapViewRef: WeakReference<MapView>? = null

    // 🔥 你要的：地图初始化时绑定一次
    fun bindMapView(mapView: MapView) {
    }

}