package com.example.jetcompose.untils

import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.*
import com.arcgismaps.mapping.layers.ArcGISTiledLayer
import com.arcgismaps.mapping.layers.WebTiledLayer
import com.arcgismaps.mapping.view.Camera
import com.arcgismaps.portal.Portal

object TianDiTuLayer {
    // ====================== 常量定义 ======================
    // 天地图密钥
    private const val TDT_KEY = "17ee5b9a9338f882a3f1cbcf55409a0f"
    private val subDomains = listOf("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7")

    // ========== 香港地政总署 官方固定参数（严格照搬官网文档） ==========
    private const val HK_API_VERSION = "v1.0.0"
    // 统一使用 WGS84 全球经纬度坐标系（官网支持0~19全层级）
    private const val HK_SR = "WGS84"

    // ====================== 1. 天地图 矢量底图======================
    fun createTdtVecMap(): ArcGISMap {
        val vecLayer = WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=vec_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        ).apply {
            attribution = "© 国家自然资源部 天地图"
        }
        val cvaLayer = WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cva_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        )
        return ArcGISMap(Basemap(listOf(vecLayer, cvaLayer)))
    }

    // ====================== 2. 天地图 卫星影像） ======================
    fun createTdtImgMap(): ArcGISMap {
        val imgLayer = WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        ).apply {
            attribution = "© 国家自然资源部 天地图"
        }
        val ciaLayer = WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cia_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        )
        // 香港英文注记
        val hkLabelLayer = createHkLabelLayer("en")
        return ArcGISMap(Basemap(listOf(imgLayer, ciaLayer, hkLabelLayer)))
    }

    fun createHongKongImageryMap(): ArcGISMap {
        // 香港官方粤港澳大湾区哨兵2号高清影像（严格官网URL）
        val hkImageryLayer = WebTiledLayer.create(
            "https://mapapi.geodata.gov.hk/gs/api/$HK_API_VERSION/xyz/imagery/$HK_SR/{level}/{col}/{row}.png"
        ).apply {
            attribution = "地圖由地政總署提供 | Contains modified Copernicus Sentinel data [2022]"
        }
        val hkLabelLayer = createHkLabelLayer("en")

        return ArcGISMap(Basemap(listOf(hkImageryLayer, hkLabelLayer)))
    }

    // ====================== 香港地名标注图层（en英文/tc繁体/sc简体） ======================
    fun createHkLabelLayer(lang: String = "en"): WebTiledLayer {
        return WebTiledLayer.create(
            "https://mapapi.geodata.gov.hk/gs/api/$HK_API_VERSION/xyz/label/hk/$lang/$HK_SR/{level}/{col}/{row}.png"
        ).apply {
            attribution = "© 香港地政总署 地名标注"
        }
    }

    // ====================== 原有3D场景全部保留 + 修复Camera报错 ======================
    fun createScene(): ArcGISScene {
        val portal = Portal(
            url = "https://www.arcgis.com",
            connection = Portal.Connection.Anonymous
        )
        val portalItem = PortalItem(portal = portal, itemId = "579f97b2f3b94d4a8e48a5f140a6639b")
        return ArcGISScene(portalItem)
    }

    fun createHkArcgisMap(): ArcGISMap {
        val url = "http://10.11.228.247:9101/arcgis/rest/services/HK_MAP/BaseMap/MapServer"
        val layer =  ArcGISTiledLayer(url)
        println("layer=="+layer)
        return ArcGISMap(Basemap(layer))
    }

}