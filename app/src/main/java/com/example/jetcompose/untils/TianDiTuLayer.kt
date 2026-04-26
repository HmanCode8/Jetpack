package com.example.jetcompose.untils

import com.arcgismaps.mapping.ArcGISScene
import com.arcgismaps.mapping.layers.ArcGISTiledLayer
import com.arcgismaps.mapping.layers.WebTiledLayer
import com.arcgismaps.portal.Portal

object TianDiTuLayer {
    // ====================== 常量定义 ======================
    private const val TDT_KEY = "17ee5b9a9338f882a3f1cbcf55409a0f"
    private val subDomains = listOf("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7")

    private const val HK_API_VERSION = "v1.0.0"
    private const val HK_SR = "WGS84"

    // ====================== 1. 天地图 矢量底图图层 ======================
    fun getTdtVecLayer(): WebTiledLayer {
        return WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=vec_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        ).apply {
            attribution = "© 国家自然资源部 天地图"
        }
    }

    // ====================== 2. 天地图 卫星影像图层 ======================
    fun getTdtImgLayer(): WebTiledLayer {
        return WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        ).apply {
            attribution = "© 国家自然资源部 天地图"
        }
    }

    // ====================== 3. 天地图 中文注记图层（固定） ======================
    fun getTdtCvaLabelLayer(): WebTiledLayer {
        return WebTiledLayer.create(
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cva_w&x={col}&y={row}&l={level}&tk=$TDT_KEY", subDomains
        ).apply {
            attribution = "© 天地图地名标注"
        }
    }

    // ====================== 4. 香港官方 矢量底图图层 ======================
    fun getHongKongVecLayer(): WebTiledLayer {
        return WebTiledLayer.create(
            "https://mapapi.geodata.gov.hk/gs/api/v1.0.0/xyz/basemap/wgs84/{level}/{col}/{row}.png"
        ).apply {
            attribution = "© 香港地政总署"
        }
    }

    // ====================== 5. 香港官方 影像底图图层 ======================
    fun getHongKongImageryLayer(): WebTiledLayer {
        return WebTiledLayer.create(
            "https://mapapi.geodata.gov.hk/gs/api/$HK_API_VERSION/xyz/imagery/$HK_SR/{level}/{col}/{row}.png"
        ).apply {
            attribution = "地圖由地政總署提供 | Contains modified Copernicus Sentinel data [2022]"
        }
    }

    // ====================== 6. 香港多语言注记图层（核心！） ======================
    fun getHongKongLabelLayer(lang: String = "en"): WebTiledLayer {
        return WebTiledLayer.create(
            "https://mapapi.geodata.gov.hk/gs/api/$HK_API_VERSION/xyz/label/hk/$lang/$HK_SR/{level}/{col}/{row}.png"
        ).apply {
            attribution = "© 香港地政总署 地名标注"
        }
    }

    // ====================== 7. 香港 ArcGIS 服务图层 ======================
    fun getHkArcgisLayer(): ArcGISTiledLayer {
        val url = "http://10.11.228.247:9101/arcgis/rest/services/HK_MAP/BaseMap/MapServer"
        return ArcGISTiledLayer(url)
    }

    // ====================== 8. 3D 场景（保留不动） ======================
    fun createScene(): ArcGISScene {
        val portal = Portal(
            url = "https://www.arcgis.com",
            connection = Portal.Connection.Anonymous
        )
        val portalItem = com.arcgismaps.mapping.PortalItem(portal = portal, itemId = "579f97b2f3b94d4a8e48a5f140a6639b")
        return ArcGISScene(portalItem)
    }
}