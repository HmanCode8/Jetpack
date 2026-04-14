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
    val icon: Int,
    val children: List<MChildren<*>> = emptyList() // 👈 正确写法
)
