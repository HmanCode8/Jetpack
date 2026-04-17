package com.example.jetcompose.untils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
@Suppress("DEPRECATION", "DiscouragedApi")
fun Context.getStringByName(name: String): String {
    val resId = resources.getIdentifier(name, "string", packageName)
    return if (resId != 0) getString(resId) else name
}

//动态获取字符串
@Composable
fun stringResourceByName(name: String): String {
    LocaleUtils.appLocale.value
    val context = LocalContext.current
    return context.getStringByName(name)
}

@Suppress("DEPRECATION", "DiscouragedApi")
@Composable
fun getDrawable(name: String): Int{
    val context = LocalContext.current
    val iconName = context.resources.getIdentifier(
        name,
        "drawable",
        context.packageName
    )
    return iconName
}

