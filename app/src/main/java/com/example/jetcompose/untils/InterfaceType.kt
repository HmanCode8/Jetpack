package com.example.jetcompose.untils

data class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
)

// 1. 子菜单：泛型 id（支持 Int / String）
data class MChildren<T>(
    val label: String,
    val id: T, // T 可以是 Int / String
    val isMenu: Boolean =true
)

// 2. 主菜单：children 是 MChildren<*> 列表（支持任意类型）
data class MenuItem(
    val label: String,
    val id: Int,
    val icon: String,
    val children: List<MChildren<*>> = emptyList() // 👈 正确写法
)

// 接口根返回体 完全保留你原有代码
data class LayerResponse(
    val code: Int,
    val msg: String,
    val data: List<LayerItem>,
    val error: Boolean,
    val success: Boolean
)

// 每一个图层原始数据项（无限层级嵌套children，所有原有字段全部保留）
data class LayerItem(
    val id: Int?,
    val parentId: Int?,
    val type: String?,
    val name: String?,        // 图层英文名称（UI展示名称）
    val code: String?,        // 图层唯一编码
    val serviceUrl: String?,
    val sort: Int?,           // 排序序号
    val layerType: String?,   // 图层大类类型（供水/其他图层）
    val shapeType: String?,   // 要素类型：点/线/面(point/line/polygon)
    val layerId: Int?,
    val dataBase: String?,    // 数据库库名 sde / othldbsde
    val tableName: String?,   // 数据库表名（你表格里的全部表名）
    val isChecked: Boolean = false,
    val children: List<LayerItem>?  // 无限层级子图层，递归嵌套
)

