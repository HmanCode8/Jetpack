package com.example.jetcompose.untils

import com.arcgismaps.Color
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.Polyline
import com.arcgismaps.mapping.symbology.SimpleLineSymbol
import com.arcgismaps.mapping.symbology.SimpleLineSymbolStyle
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbol
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbolStyle
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.arcgismaps.toolkit.geoviewcompose.MapViewProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MapToolsUntil {

    // 地图代理
    private var mapViewProxy: MapViewProxy? = null
    var currentScale: Double = 80000.0

    // ======================
    // 测量专用
    // ======================
    private val measureOverlay = GraphicsOverlay()
    private val measurePoints = mutableListOf<Point>()
    private var totalDistance = 0.0
    private var tempLinePoint: Point? = null // 拖动临时点

    // 符号
    private val measurePointSymbol = SimpleMarkerSymbol(
        style = SimpleMarkerSymbolStyle.Circle,
        color = Color.red,
        size = 8f
    )
    private val measureLineSymbol = SimpleLineSymbol(
        style = SimpleLineSymbolStyle.Solid,
        color = Color.blue,
        width = 3f
    )
    private val tempLineSymbol = SimpleLineSymbol(
        style = SimpleLineSymbolStyle.Dash,
//        color = Color(0xFFFFF000),
        width = 2f
    )

    var onDistanceUpdated: ((total: Double, segment: Double) -> Unit)? = null

    // ==========================
    // 绑定
    // ==========================
    fun bind(proxy: MapViewProxy) {
        mapViewProxy = proxy
    }

    fun updateScale(scale: Double) {
        currentScale = scale
    }

    // ==========================
    // 缩放
    // ==========================
    fun zoomIn() {
        val proxy = mapViewProxy ?: return
        CoroutineScope(Dispatchers.Main).launch {
            proxy.setViewpointScale(currentScale * 0.8)
        }
    }

    fun zoomOut() {
        val proxy = mapViewProxy ?: return
        CoroutineScope(Dispatchers.Main).launch {
            proxy.setViewpointScale(currentScale * 1.2)
        }
    }

    // ==========================
    // 🔥 核心：你要的测距交互
    // ==========================

    // 1. 开始绘制（按下）
    fun startDraw(point: Point) {
        if (measurePoints.isEmpty()) {
            measurePoints.add(point)
            drawMeasureGraphics()
        }
        tempLinePoint = point
    }

    // 2. 拖动更新（实时线）
    fun updateTempLine(point: Point) {
        if (measurePoints.isNotEmpty()) {
            tempLinePoint = point
            drawMeasureGraphics()

            // 实时距离
            val last = measurePoints.last()
            val dis = last.distanceOrNull(point) ?: 0.0
            onDistanceUpdated?.invoke(totalDistance + dis, dis)
        }
    }

    // 3. 确认点（抬起）
    fun confirmPoint(point: Point) {
        if (measurePoints.isNotEmpty()) {
            val last = measurePoints.last()
            val dis = last.distanceOrNull(point) ?: 0.0
            totalDistance += dis
            measurePoints.add(point)
            tempLinePoint = null
            drawMeasureGraphics()
        }
    }

    // 4. 清空
    fun clearMeasure() {
        measurePoints.clear()
        tempLinePoint = null
        totalDistance = 0.0
        measureOverlay.graphics.clear()
        onDistanceUpdated?.invoke(0.0, 0.0)
    }

    // ==========================
    // 绘制
    // ==========================
    private fun drawMeasureGraphics() {
        measureOverlay.graphics.clear()

        // 画已确定的点
        measurePoints.forEach {
            measureOverlay.graphics.add(Graphic(it, measurePointSymbol))
        }

        // 画已确定的线
        if (measurePoints.size >= 2) {
            val line = Polyline(measurePoints)
            measureOverlay.graphics.add(Graphic(line, measureLineSymbol))
        }

        // 画实时拖动虚线
        val temp = tempLinePoint
        if (measurePoints.isNotEmpty() && temp != null) {
            val line = Polyline(listOf(measurePoints.last(), temp))
            measureOverlay.graphics.add(Graphic(line, tempLineSymbol))
        }
    }

    fun getMeasureOverlay(): GraphicsOverlay {
        return measureOverlay
    }

    private fun Point.distanceOrNull(other: Point): Double? {
        return try {
            val dx = x - other.x
            val dy = y - other.y
            kotlin.math.sqrt(dx * dx + dy * dy)
        } catch (e: Exception) {
            null
        }
    }
}