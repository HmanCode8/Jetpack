package com.example.jetcompose.untils

import com.esri.arcgisruntime.layers.WebTiledLayer

object TianDiTuLayer {

    private const val TDT_KEY = "17ee5b9a9338f882a3f1cbcf55409a0f"
//    private const val TDT_KEY = "1"

    // 天地图服务子域
    private val subDomains = listOf("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7")

    /**
     * 矢量底图（街道图）
     */
    fun createVecLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=vec_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

    /**
     * 矢量注记（汉字标注）
     */
    fun createCvaLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cva_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

    /**
     * 影像底图（卫星图）
     */
    fun createImgLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

    /**
     * 影像注记
     */
    fun createCiaLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cia_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

    /**
     * 地形底图
     */
    fun createTerLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=ter_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

    /**
     * 地形注记
     */
    fun createCtaLayer(): WebTiledLayer {
        val urlTemplate =
            "https://{subDomain}.tianditu.gov.cn/DataServer?T=cta_w&x={col}&y={row}&l={level}&tk=$TDT_KEY"
        return WebTiledLayer(urlTemplate, subDomains)
    }

}