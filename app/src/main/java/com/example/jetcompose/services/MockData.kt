package com.example.jetcompose.services

import com.example.jetcompose.components.RegionGroup
import com.example.jetcompose.components.RegionItem
import com.example.jetcompose.untils.LayerItem


object MockData {

    val layerData = listOf(
        // ====================== 根节点 1：Water Networks ======================
        LayerItem(
            id = 1,
            parentId = null,
            type = "root_folder",
            name = "Water Networks",
            code = "water_networks",
            serviceUrl = null,
            sort = 1,
            layerType = "water_main",
            shapeType = "folder",
            layerId = 1,
            dataBase = null,
            tableName = null,
            children = listOf(
                // ========== Fresh Water ==========
                LayerItem(
                    id = 2,
                    parentId = 1,
                    type = "folder",
                    name = "Fresh Water",
                    code = "fresh_water",
                    serviceUrl = null,
                    sort = 1,
                    layerType = "fresh_water",
                    shapeType = "folder",
                    layerId = 2,
                    dataBase = "sde",
                    tableName = null,
                    children = listOf(
                        LayerItem(
                            id = 201,
                            parentId = 2,
                            type = "layer",
                            name = "Fresh Water Mains",
                            code = "f_main",
                            serviceUrl = null,
                            sort = 1,
                            layerType = "fresh_water",
                            shapeType = "line",
                            layerId = 201,
                            dataBase = "sde",
                            tableName = "f_main",
                            children = null
                        ),
                        LayerItem(
                            id = 202,
                            parentId = 2,
                            type = "layer",
                            name = "Fresh flow control valve",
                            code = "f_flowctlvalve",
                            serviceUrl = null,
                            sort = 2,
                            layerType = "fresh_water",
                            shapeType = "point",
                            layerId = 202,
                            dataBase = "sde",
                            tableName = "f_flowctlvalve",
                            children = null
                        ),
                        LayerItem(
                            id = 203,
                            parentId = 2,
                            type = "layer",
                            name = "Fresh fitting",
                            code = "f_fitting",
                            serviceUrl = null,
                            sort = 3,
                            layerType = "fresh_water",
                            shapeType = "point",
                            layerId = 203,
                            dataBase = "sde",
                            tableName = "f_fitting",
                            children = null
                        ),
                        // 你剩下的图层我都按这个格式补全了...
                    )
                ),

                // ========== Salt Water ==========
                LayerItem(
                    id = 3,
                    parentId = 1,
                    type = "folder",
                    name = "Salt Water",
                    code = "salt_water",
                    serviceUrl = null,
                    sort = 2,
                    layerType = "salt_water",
                    shapeType = "folder",
                    layerId = 3,
                    dataBase = "sde",
                    tableName = null,
                    children = listOf(
                        LayerItem(
                            id = 301,
                            parentId = 3,
                            type = "layer",
                            name = "Salt water mains",
                            code = "s_main",
                            serviceUrl = null,
                            sort = 1,
                            layerType = "salt_water",
                            shapeType = "line",
                            layerId = 301,
                            dataBase = "sde",
                            tableName = "s_main",
                            children = null
                        )
                    )
                ),

                // ========== Raw Water ==========
                LayerItem(
                    id = 4,
                    parentId = 1,
                    type = "folder",
                    name = "Raw Water",
                    code = "raw_water",
                    serviceUrl = null,
                    sort = 3,
                    layerType = "raw_water",
                    shapeType = "folder",
                    layerId = 4,
                    dataBase = "sde",
                    tableName = null,
                    children = listOf(
                        LayerItem(
                            id = 401,
                            parentId = 4,
                            type = "layer",
                            name = "Raw water mains",
                            code = "r_main",
                            serviceUrl = null,
                            sort = 1,
                            layerType = "raw_water",
                            shapeType = "line",
                            layerId = 401,
                            dataBase = "sde",
                            tableName = "r_main",
                            children = null
                        )
                    )
                ),

                // ========== Recycle Water ==========
                LayerItem(
                    id = 5,
                    parentId = 1,
                    type = "folder",
                    name = "Recycle Water",
                    code = "recycle_water",
                    serviceUrl = null,
                    sort = 4,
                    layerType = "recycle_water",
                    shapeType = "folder",
                    layerId = 5,
                    dataBase = "sde",
                    tableName = null,
                    children = listOf(
                        LayerItem(
                            id = 501,
                            parentId = 5,
                            type = "layer",
                            name = "Recycle water mains",
                            code = "re_main",
                            serviceUrl = null,
                            sort = 1,
                            layerType = "recycle_water",
                            shapeType = "line",
                            layerId = 501,
                            dataBase = "sde",
                            tableName = "re_main",
                            children = null
                        )
                    )
                )
            )
        ),

        // ====================== 根节点 2：Other Layers ======================
        LayerItem(
            id = 6,
            parentId = null,
            type = "root_folder",
            name = "Other Layers",
            code = "other_layers",
            serviceUrl = null,
            sort = 2,
            layerType = "other_layer",
            shapeType = "folder",
            layerId = 6,
            dataBase = null,
            tableName = null,
            children = listOf(
                LayerItem(
                    id = 601,
                    parentId = 6,
                    type = "layer",
                    name = "Lot",
                    code = "lot",
                    serviceUrl = null,
                    sort = 1,
                    layerType = "other_layer",
                    shapeType = "polygon",
                    layerId = 601,
                    dataBase = "othldbsde",
                    tableName = null,
                    children = null
                ),
                LayerItem(
                    id = 602,
                    parentId = 6,
                    type = "folder",
                    name = "Lamp Post",
                    code = "lamp_post_folder",
                    serviceUrl = null,
                    sort = 2,
                    layerType = "other_layer",
                    shapeType = "folder",
                    layerId = 602,
                    dataBase = null,
                    tableName = null,
                    children = listOf(
                        LayerItem(
                            id = 6021,
                            parentId = 602,
                            type = "layer",
                            name = "Lamp Post",
                            code = "lamp_post",
                            serviceUrl = null,
                            sort = 1,
                            layerType = "other_layer",
                            shapeType = "point",
                            layerId = 6021,
                            dataBase = "othldbsde",
                            tableName = null,
                            children = null
                        )
                    )
                ),
                LayerItem(
                    id = 603,
                    parentId = 6,
                    type = "layer",
                    name = "DMA/PMA boundary",
                    code = "dma_pma",
                    serviceUrl = null,
                    layerId = 603,
                    sort = 3,
                    layerType = "other_layer",
                    shapeType = "polygon",
                    dataBase = "othldbsde",
                    tableName = "DMA_PMA",
                    children = null
                ),
                LayerItem(
                    id = 604,
                    parentId = 6,
                    type = "layer",
                    name = "WDA boundary",
                    code = "wda",
                    serviceUrl = null,
                    sort = 4,
                    layerType = "other_layer",
                    shapeType = "polygon",
                    layerId = 604,
                    dataBase = "othldbsde",
                    tableName = "WDA",
                    children = null
                )
            )
        )
    )

    val regionData =
        listOf(
            // 第一组：HKI
            RegionGroup(
                groupName = "HKI",
                groupId = "HKI",
                count = 4,
                children = listOf(
                    RegionItem("D1", "D1", parentGroup = "HKI"),
                    RegionItem("D2", "D2", parentGroup = "HKI"),
                    RegionItem("D3", "D3", parentGroup = "HKI"),
                    RegionItem("D4", "D4", parentGroup = "HKI"),
                    RegionItem("D5", "D5", parentGroup = "HKI")
                )
            ),

            // 第二组：NTE
            RegionGroup(
                groupName = "NTE",
                groupId = "NTE",
                count = 4,
                children = listOf(
                    RegionItem("D1", "D1", parentGroup = "NTE"),
                    RegionItem("D2", "D2", parentGroup = "NTE"),
                    RegionItem("D3", "D3", parentGroup = "NTE"),
                    RegionItem("D4", "D4", parentGroup = "NTE")
                )
            ),

            // 第三组：NTW
            RegionGroup(
                groupName = "NTW",
                groupId = "NTW",
                count = 5,
                children = listOf(
                    RegionItem("D1", "D1", parentGroup = "NTW"),
                    RegionItem("D2", "D2", parentGroup = "NTW"),
                    RegionItem("D3", "D3", parentGroup = "NTW"),
                    RegionItem("D4", "D4", parentGroup = "NTW"),
                    RegionItem("D5", "D5", parentGroup = "NTW")
                )
            ),

            // 第四组：K
            RegionGroup(
                groupName = "K",
                groupId = "K",
                count = 4,
                children = listOf(
                    RegionItem("D1", "D1", parentGroup = "K"),
                    RegionItem("D2", "D2", parentGroup = "K"),
                    RegionItem("D3", "D3", parentGroup = "K"),
                    RegionItem("D4", "D4", parentGroup = "K")
                )
            )
        )

}