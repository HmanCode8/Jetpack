package com.example.jetcompose.untils

import android.util.Log
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.mapping.Viewpoint
import java.lang.ref.WeakReference

object MapToolsUntil {

    // 弱引用，不内存泄漏，不报黄
    private var mapViewRef: WeakReference<MapView>? = null

    // 🔥 你要的：地图初始化时绑定一次
    fun bindMapView(mapView: MapView) {
        mapViewRef = WeakReference(mapView)
        mapView.addMapScaleChangedListener {
            val s = mapView.mapScale.toInt()
            AppGlobalState.currentScale.value = s
        }
        // 可选：默认打开定位蓝点
//        mapView.locationDisplay.isEnabled = true
    }

    // 页面销毁时清空
    fun clearMapView() {
        mapViewRef?.clear()
        mapViewRef = null
    }

    // ===================== 常用地图工具 =====================
    // 放大
    fun zoomIn() {
        mapViewRef?.get()?.let {
            it.setViewpointScaleAsync(it.mapScale / 1.5)
        }
    }

    // 缩小
    fun zoomOut() {
        mapViewRef?.get()?.let {
            it.setViewpointScaleAsync(it.mapScale * 1.5)
        }
    }

    // 定位到当前位置
    fun locateToCurrentLocation() {
        val location = mapViewRef?.get()?.locationDisplay?.location
        location?.position?.let { point ->
            mapViewRef?.get()?.setViewpointAsync(
                Viewpoint(point, 10000.0),
                0.5f
            )
        }
    }

    // 指北（旋转归零）
    fun resetNorth() {
        mapViewRef?.get()?.setViewpointRotationAsync(0.0)
    }
}