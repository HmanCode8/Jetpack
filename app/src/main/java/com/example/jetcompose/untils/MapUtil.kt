package com.example.jetcompose.untils

import com.arcgismaps.mapping.layers.ArcGISTiledLayer

object MapUtil {
    private fun initMap(key: String){
        when (key) {
//            "argis" -> createHKBaseMapLayer()
//            "tidiVec" -> createVecLayer()
//            "tidiImg" -> createImgLayer()
//            "tidiTer" -> createTerLayer()
//            "tile"->createCustomTileLayer()
        }
    }

    // ====================== 你的内网 ArcGIS 服务 ======================
    /**
     * 加载你自己的 ArcGIS 切片地图服务
     * http://10.11.228.247:9101/arcgis/rest/services/HK_MAP/BaseMap/MapServer
     */

    fun createHKBaseMapLayer(): ArcGISTiledLayer {
        val url = "http://10.11.228.247:9101/arcgis/rest/services/HK_MAP/BaseMap/MapServer"
        return ArcGISTiledLayer(url)
    }

}