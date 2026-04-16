package com.example.jetcompose.untils

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object LocaleUtils {
    const val LANG_ZH_TW = "zh-TW"
    const val LANG_ZH_CN = "zh-CN"
    const val LANG_EN = "en"

    var currentLang: String = LANG_EN

    // 🔥 全局可观察语言（关键！）
    var appLocale = mutableStateOf(Locale.TRADITIONAL_CHINESE)

    fun setLanguage(context: Context, lang: String) {
        currentLang = lang

        val locale = when (lang) {
            LANG_ZH_TW -> Locale("zh", "TW")
            LANG_ZH_CN -> Locale("zh", "CN")
            LANG_EN -> Locale("en")
            else -> Locale("zh", "TW")
        }

        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // 🔥 全局通知语言变了（所有组件自动刷新）
        appLocale.value = locale
    }
}